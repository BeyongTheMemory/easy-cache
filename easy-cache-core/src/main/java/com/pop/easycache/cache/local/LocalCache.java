package com.pop.easycache.cache.local;

/**
 * Created by xugang on 17/6/21.
 */
public interface LocalCache {
    void set(Object key, Object value);

    void del(Object key);

    Object getStringByKey(Object key);
}
