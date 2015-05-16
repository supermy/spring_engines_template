package com.supermy.zookeeper;


/**
 * Created by moyong on 15/5/12.
 */
import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

/**
 * 增删改查
 */
public class CrudExample {
    private static final String PATH = "/a/b/c";

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer();

        CuratorFramework client = null;
        client = CreateClientExample.createSimple(server.getConnectString());
        client.start();

        //client.create().creatingParentsIfNeeded().forPath(PATH, "测试test".getBytes());

        create(client,"/a/b/c","play-boy".getBytes());

        System.out.println(getData(client,PATH));


        CloseableUtils.closeQuietly(client);
        CloseableUtils.closeQuietly(server);

//        TestingServer server = new TestingServer();
//        CuratorFramework client = null;
//        try {
//            client = CreateClientExample.createSimple(server.getConnectString());
//            client.start();
//
//            //client.create().creatingParentsIfNeeded().forPath(PATH, "测试test".getBytes());
//            create(client,"/a/b/c","play-boy".getBytes());
//
//            CloseableUtils.closeQuietly(client);
//
//            client = CreateClientExample.createWithOptions(server.getConnectString(), new ExponentialBackoffRetry(1000, 3), 1000, 1000);
//            client.start();
//
//            System.out.println(new String(client.getData().forPath(PATH)));
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            CloseableUtils.closeQuietly(client);
//            CloseableUtils.closeQuietly(server);
//        }


    }
    public static void create(CuratorFramework client, String path, byte[] payload) throws Exception {
        // this will create the given ZNode with the given data
        //client.create().forPath(path, payload);
        client.create().creatingParentsIfNeeded().forPath(path, payload);

    }
    public static void createEphemeral(CuratorFramework client, String path, byte[] payload) throws Exception {
        // this will create the given EPHEMERAL ZNode with the given data
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }
    public static String createEphemeralSequential(CuratorFramework client, String path, byte[] payload) throws Exception {
        // this will create the given EPHEMERAL-SEQUENTIAL ZNode with the given
        // data using Curator protection.
        return client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }
    public static void setData(CuratorFramework client, String path, byte[] payload) throws Exception {
        // set data for the given node
        client.setData().forPath(path, payload);
    }
    public static String getData(CuratorFramework client, String path) throws Exception {
        // set data for the given node
        return new String(client.getData().forPath(path));

    }
    public static void setDataAsync(CuratorFramework client, String path, byte[] payload) throws Exception {
        // this is one method of getting event/async notifications
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                // examine event for details
            }
        };
        client.getCuratorListenable().addListener(listener);
        // set data for the given node asynchronously. The completion
        // notification
        // is done via the CuratorListener.
        client.setData().inBackground().forPath(path, payload);
    }
    public static void setDataAsyncWithCallback(CuratorFramework client, BackgroundCallback callback, String path, byte[] payload) throws Exception {
        // this is another method of getting notification of an async completion
        client.setData().inBackground(callback).forPath(path, payload);
    }
    public static void delete(CuratorFramework client, String path) throws Exception {
        // delete the given node
        client.delete().forPath(path);
    }
    public static void guaranteedDelete(CuratorFramework client, String path) throws Exception {
        // delete the given node and guarantee that it completes
        client.delete().guaranteed().forPath(path);
    }
    public static List<String> watchedGetChildren(CuratorFramework client, String path) throws Exception {
        /**
         * Get children and set a watcher on the node. The watcher notification
         * will come through the CuratorListener (see setDataAsync() above).
         */
        return client.getChildren().watched().forPath(path);
    }
    public static List<String> watchedGetChildren(CuratorFramework client, String path, Watcher watcher) throws Exception {
        /**
         * Get children and set the given watcher on the node.
         */
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }
}
