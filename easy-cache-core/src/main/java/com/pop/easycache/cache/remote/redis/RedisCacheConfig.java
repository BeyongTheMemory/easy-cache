package com.pop.easycache.cache.remote.redis;

import com.pop.easycache.cache.AbstarctCacheConfig;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

/**
 * Created by xugang on 17/6/21.
 */
public class RedisCacheConfig extends AbstarctCacheConfig {
    /**
     * 用户自定义jedis引用
     */
    private JedisPool jedisPool;
    /**
     * redis Url
     */
    private String redisUrl;
    /**
     * redis端口
     */
    private int redisPort;

    public RedisCacheConfig(long ttl, TimeUnit ttlUnit, JedisPool jedisPool, String redisUrl, int redisPort) {
        super(ttl, ttlUnit);
        this.jedisPool = jedisPool;
        this.redisUrl = redisUrl;
        this.redisPort = redisPort;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }
}
