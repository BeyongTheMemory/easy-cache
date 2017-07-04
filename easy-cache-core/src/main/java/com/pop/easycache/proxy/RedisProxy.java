package com.pop.easycache.proxy;

import com.pop.easycache.cache.remote.redis.RedisPool;
import com.pop.easycache.interceptor.RedisCacheInterceptor;
import net.sf.cglib.proxy.Enhancer;

/**
 * Created by xugang on 17/7/2.
 * 用于远程缓存异常捕获及服务降级
 */
public class RedisProxy {
    private int errorNum;
    private RedisPool redispool;

    public RedisProxy(int errorNum, RedisPool redispool) {
        this.errorNum = errorNum;
        this.redispool = redispool;
    }

    public Object getProxy(Class clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new RedisCacheInterceptor(errorNum));
        return enhancer.create(new Class[]{RedisPool.class},new Object[]{redispool});
    }

}
