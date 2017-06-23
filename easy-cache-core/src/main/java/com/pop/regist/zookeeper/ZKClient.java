package com.pop.regist.zookeeper;

import com.pop.enums.CacheModifyType;
import com.pop.event.CacheModifyMessage;
import com.pop.event.EventBusHolder;
import com.pop.regist.Registry;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Created by xugang on 17/2/20.
 */
public class ZKClient implements Registry {
    private static final Logger logger = LoggerFactory.getLogger(ZKClient.class);
    private volatile ZooKeeper zk;
    private String server;
    private String cacheMainPath = "/localCache";
    private int SESSION_TIME_OUT = 200000;
    private Watcher watcher;

    @PostConstruct
    public void connect() {
        try {
            this.getZKInstance();
        } catch (Exception e) {
            logger.error("zk connect error", e);
        }
    }


    @PreDestroy
    public synchronized void close() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                logger.error("zk close error", e);
            }
            zk = null;
        }
    }

    public String create(String key, String value) {
        String path = cacheMainPath + "/" + key;
        try {
            zk.exists(path, true);
            return zk.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException.NodeExistsException e) {
            logger.info("zk create node error,node exist", e);
            return null;
        } catch (Exception e) {
            logger.error("zk create node error", e);
            return null;
        }


    }

    public void del(String key) {
        try {
            zk.delete(cacheMainPath + "/" + key, -1);
        } catch (Exception e) {
            logger.error("zk del node error", e);
        }
    }

    private void reconnect() {
        this.close();
        this.connect();
    }


    private ZooKeeper getZKInstance() throws IOException, KeeperException, InterruptedException {
        if (zk == null) {
            synchronized (ZooKeeper.class) {
                if (zk == null) {
                    if (StringUtils.isEmpty(server)) {
                        throw new RuntimeException("can not connect zk,server is empty");
                    }
                    zk = new ZooKeeper(server, SESSION_TIME_OUT, this.getWatch());
                    if (zk.exists(cacheMainPath, this.getWatch()) == null) {
                        zk.create(cacheMainPath, cacheMainPath.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }

                }
            }
        }
        return zk;
    }

    private Watcher getWatch() {
        if (watcher == null) {
            synchronized (Watcher.class) {
                if (watcher == null) {
                    watcher = new CacheWatcher();
                }
            }
        }
        return watcher;
    }


    private class CacheWatcher implements Watcher {

        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getState() == Event.KeeperState.Expired) {
                logger.info("zk expired reconnect");
                reconnect();
                return;
            } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                String key = getKey(watchedEvent.getPath());
                EventBusHolder.eventBus.post(new CacheModifyMessage(key, null, CacheModifyType.DELETE));
            } else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
                try {
                    zk.getData(watchedEvent.getPath(), true, null);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            //持续监听
//            try {
//                zk.getChildren(cacheMainPath,true);
//            } catch (KeeperException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


        }
    }


    private String getKey(String path) {
        return path.substring(cacheMainPath.length() + 1);
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setCacheMainPath(String cacheMainPath) {
        this.cacheMainPath = cacheMainPath;
    }

    public void setSESSION_TIME_OUT(int SESSION_TIME_OUT) {
        this.SESSION_TIME_OUT = SESSION_TIME_OUT;
    }

    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }
}
