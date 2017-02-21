package com.pop.local.guava;

import com.google.common.cache.Cache;
import com.google.common.eventbus.Subscribe;
import com.pop.enums.CacheModifyType;
import com.pop.event.CacheModifyMessage;
import com.pop.event.EventBusHolder;
import com.pop.local.LocalCache;
import org.jboss.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pop.enums.CacheModifyType.DELETE;


/**
 * Created by xugang on 17/1/18.
 */
public class GuavaCacheOperateImpl implements LocalCache {
    private static final Logger logger = LoggerFactory.getLogger(GuavaCacheOperateImpl.class);
    private static String modifyLog = "Cache be %s,key is %s";

    private Cache cache;

    public GuavaCacheOperateImpl() {
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

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Subscribe
    public void cacheModify(CacheModifyMessage message){
        switch (message.getType()){
            case DELETE:
                del(message.getKey());
                logger.info(String.format(modifyLog,"delete",message.getKey()));
                break;
        }
    }
}
