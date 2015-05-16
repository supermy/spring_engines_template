package com.supermy.zookeeper;

/**
 * Created by moyong on 15/5/12.
 */
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.nio.charset.Charset;

/**
 * 创建与获取节点数据
 *
 */
public class CreateClientExample {
    private static final String PATH = "/example/basic";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();
        CuratorFramework client = null;
        try {
            client = createSimple(server.getConnectString());
            client.start();

            client.create().creatingParentsIfNeeded().forPath(PATH, "测试test".getBytes());

            client.create()//创建一个路径
                    .creatingParentsIfNeeded()//如果指定的节点的父节点不存在，递归创建父节点
                    .withMode(CreateMode.PERSISTENT)//存储类型（临时的还是持久的）
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//访问权限
                    .forPath("/zk/test", "123".getBytes());//创建的路径

            client.//对路径节点赋值
                    setData().
                    forPath("/zk/test", "hello world,世界，你好！".getBytes(Charset.forName("utf-8")));

            byte[] buffer = client.
                    getData().usingWatcher(new ZKWatch("/zk/test", client)).forPath("/zk/test");

            client.//对路径节点赋值
                    setData().
                    forPath("/zk/test", "世界，你好！".getBytes(Charset.forName("utf-8")));

            client.//对路径节点赋值
                    setData().
                    forPath("/zk/test", "华丽变身......".getBytes(Charset.forName("utf-8")));

            System.out.println(new String(buffer));


            CloseableUtils.closeQuietly(client);

            client = createWithOptions(server.getConnectString(), new ExponentialBackoffRetry(1000, 3), 1000, 1000);
            client.start();

            System.out.println(new String(client.getData().forPath(PATH)));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(server);
        }
    }
    public static CuratorFramework createSimple(String connectionString) {
        // these are reasonable arguments for the ExponentialBackoffRetry.
        // The first retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // The simplest way to get a CuratorFramework instance. This will use default values.
        // The only required arguments are the connection string and the retry policy
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }
    public static CuratorFramework createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        // using the CuratorFrameworkFactory.builder() gives fine grained control
        // over creation options. See the CuratorFrameworkFactory.Builder javadoc details
        return CuratorFrameworkFactory.builder().connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                        // etc. etc.
                .build();
    }
}
