package com.pop.easycache.cache;

import java.util.concurrent.TimeUnit;

/**
 * Created by xugang on 17/6/21.
 */
public abstract class AbstarctCacheConfig {
    private long ttl;
    private TimeUnit ttlUnit;

    public AbstarctCacheConfig(long ttl, TimeUnit ttlUnit) {
        this.ttl = ttl;
        this.ttlUnit = ttlUnit;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public TimeUnit getTtlUnit() {
        return ttlUnit;
    }

    public void setTtlUnit(TimeUnit ttlUnit) {
        this.ttlUnit = ttlUnit;
    }
}
