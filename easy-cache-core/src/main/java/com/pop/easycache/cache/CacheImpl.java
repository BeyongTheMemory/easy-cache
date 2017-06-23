package com.pop.easycache.cache;

import com.pop.easycache.cache.local.LocalCache;
import com.pop.easycache.cache.remote.RemoteCache;
import com.pop.easycache.registry.Registry;
import com.pop.easycache.serialize.Serialize;
import org.springframework.util.StringUtils;

/**
 * Created by xugang on 17/6/21.
 */
public class CacheImpl implements Cache{
    private LocalCache localCache;
    private RemoteCache remoteCache;
    private Registry registry;
    private Serialize serialize;

    public CacheImpl(LocalCache localCache, RemoteCache remoteCache, Registry registry, Serialize serialize) {
        this.localCache = localCache;
        this.remoteCache = remoteCache;
        this.registry = registry;
        this.serialize = serialize;
    }

    public void set(String key, Object value) {
        if (localCache != null){
            localCache.set(key, value);
        }
        if(registry != null){
            registry.create(key,serialize.objectToString(value));
        }
        if (remoteCache != null) {
            remoteCache.set(key, serialize.objectToString(value));
        }
    }

    public void del(String key) {
        if (remoteCache != null) {
            remoteCache.del(key);
        }
        if(localCache != null){
            localCache.del(key);
        }
        if (registry != null){
            registry.del(key);
        }
    }

    public <T> T getStringByKey(String key, Class<T> clazz) {
        Object localResult = null;
        if(localCache != null) {
             localResult = localCache.getStringByKey(key);
        }
        if (localResult != null) {
            return (T) localResult;
        }
        if (remoteCache != null) {
            String remoteResult = remoteCache.getStringByKey(key);
            if (!StringUtils.isEmpty(remoteResult)) {
                return serialize.toObject(remoteResult, clazz);
            }
        }
        return null;
    }
}
