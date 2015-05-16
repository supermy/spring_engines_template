package com.supermy.curator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.Charsets;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by moyong on 15/5/16.
 */
public class CuratorUtil {
    private CuratorFramework client;


    public CuratorUtil(String zkAddress) {
        client = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(1000, 3));
        client.getCuratorListenable().addListener(new NodeEventListener());
        client.start();
    }

    public CuratorUtil(CuratorFramework client ) {
        this.client =client;
    }

    public  CuratorFramework createSimple(String connectionString) {
        // these are reasonable arguments for the ExponentialBackoffRetry.
        // The first retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // The simplest way to get a CuratorFramework instance. This will use default values.
        // The only required arguments are the connection string and the retry policy
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    public  CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        // using the CuratorFrameworkFactory.builder() gives fine grained control
        // over creation options. See the CuratorFrameworkFactory.Builder javadoc details
        return CuratorFrameworkFactory.builder().connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                        // etc. etc.
                .build();
    }

    /**
     * 遍历JsonTree
     *
     * @param json
     */
    public  void decodeJSONObject(JSONObject json, String path) throws Exception {

        Iterator<String> keys = json.keySet().iterator();
        JSONObject jo = null;
        Object o;
        String key;


        while (keys.hasNext()) {
            key = keys.next();
            o = json.get(key);

//            System.out.println(o.getClass());

            if (o instanceof JSONObject) {
                jo = (JSONObject) o;
                if (jo.keySet().size() > 0) {
                    decodeJSONObject(jo, path + key);
                } else {
                    System.out.println("key:" + key);
                }
            } else {
                if (o instanceof JSONArray) {
                    JSONArray jsa = (JSONArray) o;
                    for (int i = 0; i < jsa.size(); i++) {
                        JSONObject jo2 = jsa.getJSONObject(i);
                        decodeJSONObject( jo2, path + key);
                    }
                } else if (path.endsWith("/")) {
                    client.create().creatingParentsIfNeeded().forPath(path + key, o.toString().getBytes());
                    System.out.println(" path:" + path + key + " value:" + o);
                } else {
                    client.create().creatingParentsIfNeeded().forPath(path + "/" + key, o.toString().getBytes());
                    System.out.println(" path:" + path + "/" + key + " value:" + o);
                }

            }

        }
    }

    /**
     * 创建node
     *
     * @param nodeName
     * @param value
     * @return
     */
    public boolean createNode(String nodeName, String value) {
        boolean suc = false;
        try {
            Stat stat = getClient().checkExists().forPath(nodeName);
            if (stat == null) {
                String opResult = null;
                if (Strings.isNullOrEmpty(value)) {
                    opResult = getClient().create().creatingParentsIfNeeded().forPath(nodeName);

//                    client.create()//创建一个路径
//                            .creatingParentsIfNeeded()//如果指定的节点的父节点不存在，递归创建父节点
//                            .withMode(CreateMode.PERSISTENT)//存储类型（临时的还是持久的）
//                            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//访问权限
//                            .forPath("/zk/test", "123".getBytes());//创建的路径

                }
                else {
                    opResult =
                            getClient().create().creatingParentsIfNeeded()
                                    .forPath(nodeName, value.getBytes(Charsets.UTF_8));
                }
                suc = Objects.equal(nodeName, opResult);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }


    /**
     * 更新节点
     *
     * @param nodeName
     * @param value
     * @return
     */
    public boolean updateNode(String nodeName, String value) {
        boolean suc = false;
        try {
            Stat stat = getClient().checkExists().forPath(nodeName);
            if (stat != null) {
                Stat opResult = getClient().setData().forPath(nodeName, value.getBytes(Charsets.UTF_8));
                suc = opResult != null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }


    /**
     * 删除节点
     *
     * @param nodeName
     */
    public void deleteNode(String nodeName) {
        try {
            getClient().delete().deletingChildrenIfNeeded().forPath(nodeName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 找到指定节点下所有子节点的名称与值
     *
     * @param node
     * @return
     */
    public Map<String, String> listChildrenDetail(String node) {
        Map<String, String> map = Maps.newHashMap();
        try {
            GetChildrenBuilder childrenBuilder = getClient().getChildren();
            List<String> children = childrenBuilder.forPath(node);
            GetDataBuilder dataBuilder = getClient().getData();
            if (children != null) {
                for (String child : children) {
                    String propPath = ZKPaths.makePath(node, child);
                    map.put(child, new String(dataBuilder.forPath(propPath), Charsets.UTF_8));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 列出子节点的名称
     *
     * @param node
     * @return
     */
    public List<String> listChildren(String node) {
        List<String> children = Lists.newArrayList();
        try {
            GetChildrenBuilder childrenBuilder = getClient().getChildren();
            children = childrenBuilder.forPath(node);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf) throws Exception {
        if (isSelf) {
            getClient().getData().watched().forPath(node);
        }
        else {
            getClient().getChildren().watched().forPath(node);
        }
    }

    /**
     *
     * 监控节点下面的所有树叶节点
     *
     * @param node
     * @throws Exception
     */
    public void addWatchTree(String node) throws Exception {

        final TreeCache treeCache = new TreeCache(client, node);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("================== catch tree change ==================");
                if(event.getData() == null){
                    System.out.println("===init," + event.getType());
                    return;
                }

                if(event.getData().getData() == null){
                    System.out.println("===delete," + event.getType() + "," + event.getData().getPath());
                }else{
                    System.out.println("===update or add," + event.getType() + "," + event.getData().getPath() + "," + new String(event.getData().getData()));
                }
            }
        });
        treeCache.start();

    }

    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf, Watcher watcher) throws Exception {
        if (isSelf) {
            getClient().getData().usingWatcher(watcher).forPath(node);
        }
        else {
            getClient().getChildren().usingWatcher(watcher).forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf, CuratorWatcher watcher) throws Exception {
        if (isSelf) {
            getClient().getData().usingWatcher(watcher).forPath(node);
        }
        else {
            getClient().getChildren().usingWatcher(watcher).forPath(node);
        }
    }


    /**
     * 销毁资源
     */
    public void destory() {
        if (client != null) {
            //client.close();
            CloseableUtils.closeQuietly(client);
        }
    }


    /**
     * 获取client
     *
     *
     * @return
     */
    public CuratorFramework getClient() {
        return client;
    }

}
