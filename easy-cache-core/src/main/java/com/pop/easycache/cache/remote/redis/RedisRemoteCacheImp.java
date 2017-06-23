package com.pop.easycache.cache.remote.redis;

import com.pop.easycache.cache.remote.RemoteCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


/**
 */
public class RedisRemoteCacheImp implements RemoteCache {

    private static final Logger logger = LoggerFactory.getLogger(RedisRemoteCacheImp.class);

    private RedisPool redispool;

    public RedisRemoteCacheImp(RedisPool redispool) {
        this.redispool = redispool;
    }

    public void set(String key, String value) {
        Jedis con = null;
        try {
            con = redispool.getConnection();
            con.set(key, value);
        } finally {
            redispool.closeConnection(con);
        }
    }

    public void set(String key, String value, int second) {
        Jedis con = null;
        try {
            con = redispool.getConnection();
            con.set(key, value);
            con.expire(key, second);
        } finally {
            redispool.closeConnection(con);
        }


    }


    public void del(String key) {
        Jedis con = null;
        try {
            con = redispool.getConnection();
            if (con != null) {
                con.del(key);
            }
        } finally {
            redispool.closeConnection(con);
        }

    }

    public String getStringByKey(String key) {
        Jedis con = null;
        String res = null;
        try {
            con = redispool.getConnection();
            res = con.get(key);
        } finally {
            redispool.closeConnection(con);
        }
        return res;
    }

    public void setRedispool(RedisPool redispool) {
        this.redispool = redispool;
    }
}
