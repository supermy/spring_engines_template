package com.supermy.zookeeper;

/**
 * Created by moyong on 15/5/12.
 */


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.supermy.curator.CuratorUtil;
import org.apache.commons.io.FileUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 创建与获取节点数据
 */
public class CreateClientExample4Json {
    private static final String PATH = "/example/basic";

    public static void main(String[] args) throws Exception {

        File json = new File("/Users/moyong/project/env-myopensource/1-spring/13-jamesmo/spring_engines_template/target/test-classes/cfg/config.json");
        String fileContent = FileUtils.readFileToString(json);
        JSONObject jsonfile = JSON.parseObject(fileContent, JSONObject.class);

        TestingServer server = new TestingServer();

        CuratorUtil curator = new CuratorUtil(server.getConnectString());

        try {

            curator.createNode("/zkroot/test1", "你好test11");
            curator.createNode("/zkroot/test2", "你好test22");
            curator.updateNode("/zkroot/test2", "你好test22");

            List<String> list = curator.listChildren("/zkroot");
            Map<String, String> map = curator.listChildrenDetail("/zkroot");
            // curator.deleteNode("/zkroot");
            // curator.destory();
            System.out.println("child-name=========================================");
            for (String str : list) {
                System.out.println(str);
            }

            System.out.println("child-detail=========================================");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=>" + entry.getValue());
            }

            // 增加监听
            curator.addWatch("/zkroot", false);

            //TimeUnit.SECONDS.sleep(6);

            curator.decodeJSONObject( jsonfile, "/abc/");
            Map<String, String> abc = curator.listChildrenDetail("/abc");
            for (Map.Entry<String, String> entry : abc.entrySet()) {
                System.out.println(entry.getKey() + "=>" + entry.getValue());
            }

            curator.destory();



        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            curator.destory();
            CloseableUtils.closeQuietly(server);
        }
    }


}
