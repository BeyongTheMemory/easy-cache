package com.pop.easycache.cache.local.guava;

import com.google.common.cache.Cache;
import com.google.common.eventbus.Subscribe;
import com.pop.easycache.cache.local.LocalCache;

import com.pop.easycache.event.CacheModifyMessage;
import com.pop.easycache.event.EventBusHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by xugang on 17/1/18.
 */
public class GuavaLocalCacheImpl implements LocalCache {
    private static final Logger logger = LoggerFactory.getLogger(GuavaLocalCacheImpl.class);
    private static String modifyLog = "Cache be %s,key is %s";

    private Cache cache;

    public GuavaLocalCacheImpl(Cache cache) {
        this.cache = cache;
        EventBusHolder.eventBus.register(this);
    }

    public void set(Object key, Object value) {
        cache.put(key, value);
    }


    public void del(Object key) {
        cache.invalidate(key);
    }

    public Object getStringByKey(Object key) {
        return cache.getIfPresent(key);
    }


    @Subscribe
    public void cacheModify(CacheModifyMessage message){
        switch (message.getType()){
            case DELETE:
                del(message.getKey());
                logger.debug(String.format(modifyLog,"delete",message.getKey()));
                break;
        }
    }
}
