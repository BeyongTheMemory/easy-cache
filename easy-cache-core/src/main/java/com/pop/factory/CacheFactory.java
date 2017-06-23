package com.pop.factory;

import com.google.common.cache.CacheBuilder;
import com.pop.cache.Cache;
import com.pop.config.local.AbstractLocalConfig;
import com.pop.loader.XMLLoader;
import com.pop.local.LocalCache;
import com.pop.local.guava.GuavaCacheOperateImpl;
import com.pop.regist.Registry;
import com.pop.remote.RemoteCache;
import com.pop.remote.redis.RedisOperateImp;
import com.pop.remote.redis.RedisPool;
import com.pop.serialize.Serialize;
import com.pop.serialize.json.FastJsonSerialize;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * Created by xugang on 17/1/18.
 */
public class CacheFactory implements ApplicationContextAware {
    @Autowired(required = false)
    private  LocalCache localCache;
    @Autowired(required = false)
    private RemoteCache remoteCache;
    @Autowired(required = false)
    private Serialize serialize;
    @Autowired(required = false)
    private Registry registry;
    private boolean needRemote = true;
    private Cache cache;
    @Autowired
    private AbstractLocalConfig localCacheConfig;
    private ApplicationContext applicationContext;

    private Cache createCache() {
        if(localCache == null){
            //create defalut cache
            localCache = new GuavaCacheOperateImpl();
            CacheBuilder guavaCacheBulider = CacheBuilder.newBuilder();
            if(localCacheConfig.getLocalMaxSize() > 0){
                guavaCacheBulider.maximumSize(localCacheConfig.getLocalMaxSize());
            }
            if (localCacheConfig.getLocalTTL() > 0){
                guavaCacheBulider.expireAfterWrite(localCacheConfig.getLocalTTL(), localCacheConfig.gettTLUnit());
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
        if(serialize == null){
            serialize = new FastJsonSerialize();
        }
        cache.setSerialize(serialize);
        if(registry != null){
            cache.setRegistry(registry);
        }
        return cache;
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setNeedRemote(boolean needRemote) {
        this.needRemote = needRemote;
    }
}
