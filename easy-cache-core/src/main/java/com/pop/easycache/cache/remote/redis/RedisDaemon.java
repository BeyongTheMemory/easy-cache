package com.pop.easycache.cache.remote.redis;

import com.pop.easycache.interceptor.RedisCacheInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xugang on 17/7/4.
 */
public class RedisDaemon implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(RedisDaemon.class);

    private RedisPool redispool;

    private int redisRetryTime;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public RedisDaemon(RedisPool redispool, int redisRetryTime) {
        this.redispool = redispool;
        this.redisRetryTime = redisRetryTime;
        this.service.scheduleAtFixedRate(this,redisRetryTime,redisRetryTime, TimeUnit.SECONDS);
    }


    public void run() {
        if(!RedisCacheInterceptor.isNormal) {
            Jedis con = null;
            try {
                con = redispool.getConnection();
                con.set("easy_cache_redis_state","normal");
                RedisCacheInterceptor.isNormal = true;
            } catch (Throwable throwable){
                logger.error("redis reconnect error",throwable);
            } finally {
                redispool.closeConnection(con);
            }
        }
    }
}
