package com.pop.easycache.proxy;

import com.pop.easycache.cache.Cache;
import com.pop.easycache.interceptor.CacheInterceptor;
import com.pop.easycache.serialize.Serialize;
import net.sf.cglib.proxy.Enhancer;

/**
 * Created by xugang on 17/6/21.
 */
public class CglibProxy implements Proxy{
    private CacheInterceptor interceptor;

    public CglibProxy(Cache cache,Serialize serialize){
        interceptor = new CacheInterceptor(cache, serialize);
    }

    public Object getProxyObject(Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(interceptor);
        return enhancer.create();
    }
}
