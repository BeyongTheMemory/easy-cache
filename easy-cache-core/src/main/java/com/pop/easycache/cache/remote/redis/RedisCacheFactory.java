package com.pop.easycache.cache.remote.redis;

import com.pop.easycache.cache.remote.RemoteCache;
import com.pop.easycache.cache.remote.RemoteCacheFactory;
import com.pop.easycache.entity.ValidBean;
import com.pop.easycache.proxy.RedisProxy;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;

/**
 * Created by xugang on 17/6/22.
 */
public class RedisCacheFactory extends RemoteCacheFactory {
    private RedisCacheConfig config;


    public RedisCacheFactory(RedisCacheConfig config) {
        this.config = config;
    }

    protected ValidBean valid() {
        if (config.getJedisPool() != null ||
                (!StringUtils.isEmpty(config.getRedisUrl()) && !StringUtils.isEmpty(config.getRedisPort()))) {
            return new ValidBean(true,null);
        }
        return new ValidBean(false,"no source found");
    }

    protected RemoteCache build() {
        RedisPool redisPool;
        if (config.getJedisPool() != null) {
            redisPool = new RedisPool(config.getJedisPool());
        }else {
            JedisPool jedisPool = new JedisPool(config.getRedisUrl(),config.getRedisPort());
            redisPool = new RedisPool(jedisPool);
        }
        RedisProxy proxy = new RedisProxy(config.getErrorNum(),redisPool);
        RemoteCache remoteCache = (RemoteCache)proxy.getProxy(RedisRemoteCacheImpl.class);
        //注册守护线程
        RedisDaemon redisDaemon = new RedisDaemon(redisPool,config.getRedisRetryTime());
        return remoteCache;
    }
}
