package com.pop.easycache.registry;

/**
 * Created by xugang on 17/6/23.
 */
public class AbstractRegistryConfig {
    /**
     * 注册中心地址
     */
    private String server;
    /**
     * 注册节点根目录
     */
    private String path;

    public AbstractRegistryConfig(String server, String path) {
        this.server = server;
        this.path = path;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

