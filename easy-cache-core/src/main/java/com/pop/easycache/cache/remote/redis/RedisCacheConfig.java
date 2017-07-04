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

    /**
     * 远程缓存失败指定次数后降级
     */
    private int errorNum;

    /**
     * 服务降级后的重试间隔时间,单位秒
     */
    private int redisRetryTime;

    public RedisCacheConfig(long ttl, TimeUnit ttlUnit, JedisPool jedisPool, String redisUrl, int redisPort,int errorNum,int redisRetryTime) {
        super(ttl, ttlUnit);
        this.jedisPool = jedisPool;
        this.redisUrl = redisUrl;
        this.redisPort = redisPort;
        this.errorNum = errorNum;
        this.redisRetryTime = redisRetryTime;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }


    public String getRedisUrl() {
        return redisUrl;
    }


    public int getRedisPort() {
        return redisPort;
    }


    public int getErrorNum() {
        return errorNum;
    }

    public int getRedisRetryTime() {
        return redisRetryTime;
    }
}
