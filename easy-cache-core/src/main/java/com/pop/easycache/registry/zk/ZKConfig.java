package com.pop.easycache.registry.zk;

import com.pop.easycache.registry.AbstractRegistryConfig;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by xugang on 17/6/23.
 */
public class ZKConfig extends AbstractRegistryConfig{
    private ZooKeeper zk;
    private int sessionTimeOut;

    public ZKConfig(String server, String path, ZooKeeper zk, int sessionTimeOut) {
        super(server, path);
        this.zk = zk;
        this.sessionTimeOut = sessionTimeOut;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
