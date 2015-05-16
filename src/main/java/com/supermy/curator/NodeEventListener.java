package com.supermy.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.nio.charset.Charset;

/**
 * Created by moyong on 15/5/16.
 */
// 监听器
public final class NodeEventListener implements CuratorListener {
    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        System.out.println(event.toString() + ".......................");
        final WatchedEvent watchedEvent = event.getWatchedEvent();
        if (watchedEvent != null) {
            System.out.println(watchedEvent.getState() + "=======================" + watchedEvent.getType());
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                switch (watchedEvent.getType()) {
                    case NodeChildrenChanged:
                        // TODO

                        byte[] data1 = client.getData().forPath(event.getPath());
                        System.out.println("监控中，子节点数据变更："+event.getPath()+":"+new String(data1, Charset.forName("utf-8")));

                        break;
                    case NodeDataChanged:
                        // TODO
                        byte[] data2 = client.getData().forPath(event.getPath());
                        System.out.println("监控中，数据变更："+event.getPath()+":"+new String(data2, Charset.forName("utf-8")));
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
