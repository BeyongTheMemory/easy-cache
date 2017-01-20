package com.pop.local.guava;

import com.google.common.cache.Cache;
import com.pop.local.LocalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by xugang on 17/1/18.
 */
public class GuavaCacheOperateImpl implements LocalCache {
    private static final Logger logger = LoggerFactory.getLogger(GuavaCacheOperateImpl.class);

    private Cache cache;


    public void set(Object key, Object value) {
        cache.put(key, value);
    }


    public void del(Object key) {
        cache.invalidate(key);
    }

    public Object getStringByKey(Object key) {
        return cache.getIfPresent(key);
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
