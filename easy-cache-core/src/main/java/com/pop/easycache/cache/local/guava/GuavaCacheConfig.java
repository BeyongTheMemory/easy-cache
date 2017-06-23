package com.pop.easycache.cache.local.guava;

import com.pop.easycache.cache.AbstarctCacheConfig;

import java.util.concurrent.TimeUnit;

/**
 * Created by xugang on 17/6/21.
 */
public class GuavaCacheConfig extends AbstarctCacheConfig {
    private long maxSize;

    public GuavaCacheConfig(long ttl, TimeUnit ttlUnit, long maxSize) {
        super(ttl, ttlUnit);
        this.maxSize = maxSize;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
