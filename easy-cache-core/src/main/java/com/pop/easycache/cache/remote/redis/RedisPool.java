package com.pop.easycache.cache.remote.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;


/**
 */
public class RedisPool {

    private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);

    private JedisPool pool;

    public RedisPool(JedisPool pool) {
        this.pool = pool;
    }

    public Jedis getConnection() {
        Jedis con = null;
        try {
            con = pool.getResource();
        } catch (JedisConnectionException e) {
            logger.error("redis get connection exception", e);
            throw e;

        }
        return con;
    }

    public void closeConnection(Jedis con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (JedisException e) {
            logger.error("redis close connection exception", e);
            throw e;
        }
    }

}
