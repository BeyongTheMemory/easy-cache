package com.pop.easycache.interceptor;


import com.pop.easycache.cache.Cache;
import com.pop.easycache.interceptor.handler.CachaableHandler;
import com.pop.easycache.interceptor.handler.CacheEvictHandler;
import com.pop.easycache.interceptor.handler.CacheHandler;
import com.pop.easycache.serialize.Serialize;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/6/21.
 */
public class CacheInterceptor implements MethodInterceptor {
    private Cache cache;

    private Serialize serialize;

    private CacheHandler cacheHandler;

    public CacheInterceptor(Cache cache, Serialize serialize) {
        this.cache = cache;
        this.serialize = serialize;
        registHandle();
    }

    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return cacheHandler.handle(cache, serialize,  target,  method,  args,  methodProxy);
    }

    private void registHandle(){
        cacheHandler = new CachaableHandler();
        cacheHandler.setSuccessor(new CacheEvictHandler());
    }
}
