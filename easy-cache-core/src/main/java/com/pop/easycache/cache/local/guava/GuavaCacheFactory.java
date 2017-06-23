package com.pop.easycache.cache.local.guava;

import com.google.common.cache.CacheBuilder;
import com.pop.easycache.cache.local.LocalCache;
import com.pop.easycache.cache.local.LocalCacheFactory;
import com.pop.easycache.entity.ValidBean;

/**
 * Created by xugang on 17/6/22.
 */
public class GuavaCacheFactory extends LocalCacheFactory{
    private GuavaCacheConfig config;

    public GuavaCacheFactory(GuavaCacheConfig config) {
        this.config = config;
    }

    protected ValidBean valid() {
        return new ValidBean(true,null);
    }

    protected LocalCache build() {
        CacheBuilder guavaCacheBulider = CacheBuilder.newBuilder();
        if(config.getMaxSize() > 0){
            guavaCacheBulider.maximumSize(config.getMaxSize());
        }
        if (config.getTtl() > 0){
            guavaCacheBulider.expireAfterWrite(config.getTtl(), config.getTtlUnit());
        }
        return new GuavaLocalCacheImpl(guavaCacheBulider.<String, Object>build());
    }
}
