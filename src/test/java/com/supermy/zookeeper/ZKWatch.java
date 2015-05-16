package com.supermy.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.nio.charset.Charset;

/**
 * Created by moyong on 15/5/13.
 */
public class ZKWatch implements CuratorWatcher {
    private final String path;
    private final CuratorFramework client;

    public String getPath() {
        return path;
    }

    public ZKWatch(String path,CuratorFramework client) {
        this.path = path;
        this.client=client;
    }

    @Override
    public void process(WatchedEvent event) throws Exception {
        if(event.getType() == Watcher.Event.EventType.NodeDataChanged){
            byte[] data = client.getData().usingWatcher(new ZKWatch(path, client)).forPath(path);
            System.out.println("监控中，数据变更："+path+":"+new String(data, Charset.forName("utf-8")));
        }
        if(event.getType() == Watcher.Event.EventType.NodeCreated){
            byte[] data = client.getData().usingWatcher(new ZKWatch(path, client)).forPath(path);
            System.out.println("监控中，节点创建："+path+":"+new String(data, Charset.forName("utf-8")));
        }
        if(event.getType() == Watcher.Event.EventType.NodeDeleted){
            byte[] data = client.getData().usingWatcher(new ZKWatch(path, client)).forPath(path);
            System.out.println("监控中，节点删除："+path+":"+new String(data, Charset.forName("utf-8")));
        }

    }

}
