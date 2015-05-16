package com.supermy.zookeeper; /**
 * Created by moyong on 14/12/27.
 */
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * <p>模板引擎Mustache处理<p/>
 * <p>提供list变量</p>
 */
public class ZooKeeperTest {



    public static void main(String[] args) throws Exception {
        //客户端就连接到ZooKeeper了，无需再显式关闭。
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        //fluent style
        String namespace = "cluster-worker";
        CuratorFramework client = builder.connectString("127.0.0.1:2181")
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace(namespace)
                .defaultData(null)
                .build();
        client.start();
        EnsurePath ensure = client.newNamespaceAwareEnsurePath(namespace);
        //code for test
        Thread.sleep(5000);
        //client.close()//
    }

}
