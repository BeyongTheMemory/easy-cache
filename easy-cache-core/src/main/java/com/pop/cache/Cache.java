package com.pop.cache;

import com.pop.local.LocalCache;
import com.pop.regist.Registry;
import com.pop.remote.RemoteCache;
import com.pop.serialize.Serialize;
import org.springframework.util.StringUtils;

/**
 * Created by xugang on 17/1/19.
 */
public class Cache {
    private RemoteCache remoteCache;
    private LocalCache localCache;
    private Registry registry;

    private Serialize serialize;
    private boolean needRemote = true;

    public void set(String key, Object value) {
        localCache.set(key, value);
        if(registry != null){
            registry.create(key,serialize.toString(value));
        }

        if (needRemote && remoteCache != null) {
                remoteCache.set(key, serialize.toString(value));
        }
    }

    public void del(String key) {
        if (needRemote && remoteCache != null) {
            remoteCache.del(key);
        }
        localCache.del(key);
        registry.del(key);
    }

    public <T> T getStringByKey(String key, Class<T> clazz) {
        Object localResult = localCache.getStringByKey(key);
        if (localResult != null) {
            return (T) localResult;
        }
        if (needRemote && remoteCache != null) {
            String remoteResult = remoteCache.getStringByKey(key);
            if (!StringUtils.isEmpty(remoteResult)) {
                return serialize.toObject(remoteResult, clazz);
            }
        }
        return null;
    }

    public void setNeedRemote(boolean needRemote) {
        this.needRemote = needRemote;
    }

    public void setRemoteCache(RemoteCache remoteCache) {
        this.remoteCache = remoteCache;
    }

    public void setLocalCache(LocalCache localCache) {
        this.localCache = localCache;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setSerialize(Serialize serialize) {
        this.serialize = serialize;
    }
}