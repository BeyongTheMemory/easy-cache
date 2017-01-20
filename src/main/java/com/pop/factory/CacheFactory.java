package com.pop.factory;

import com.google.common.cache.CacheBuilder;
import com.pop.cache.Cache;
import com.pop.local.LocalCache;
import com.pop.local.guava.GuavaCacheOperateImpl;
import com.pop.remote.RemoteCache;
import com.pop.remote.redis.RedisOperateImp;
import com.pop.remote.redis.RedisPool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;


/**
 * Created by xugang on 17/1/18.
 */
public class CacheFactory implements ApplicationContextAware {
    @Autowired(required = false)
    private  LocalCache localCache;
    @Autowired(required = false)
    private RemoteCache remoteCache;
    private boolean needRemote = true;
    private Cache cache;
    private long localMaxSize = -1;//defalut unlimited size
    private long localTTL = -1;//expire after write ,defalut no ttl
    private TimeUnit tTLUnit =  TimeUnit.SECONDS;


    private Cache createCache() {
        if(localCache == null){
            //create defalut cache
            localCache = new GuavaCacheOperateImpl();
            CacheBuilder guavaCacheBulider = CacheBuilder.newBuilder();
            if(localMaxSize > 0){
                guavaCacheBulider.maximumSize(localMaxSize);
            }
            if (localTTL > 0){
                guavaCacheBulider.expireAfterWrite(localTTL, tTLUnit);
            }
            ((GuavaCacheOperateImpl)localCache).setCache(guavaCacheBulider.build());
        }
        cache = new Cache();
        cache.setLocalCache(localCache);
        if(needRemote && remoteCache == null){
            JedisPool jedisPool = applicationContext.getBean(JedisPool.class);
            RedisPool redisPool = new RedisPool(jedisPool);
            RemoteCache remoteCache = new RedisOperateImp(redisPool);
            cache.setRemoteCache(remoteCache);
        }
        return cache;
    }

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setNeedRemote(boolean needRemote) {
        this.needRemote = needRemote;
    }
}
