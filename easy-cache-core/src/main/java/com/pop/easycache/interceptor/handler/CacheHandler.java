package com.pop.easycache.interceptor.handler;

import com.pop.easycache.cache.Cache;
import com.pop.easycache.serialize.Serialize;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/6/21.
 */
public abstract class CacheHandler {
    private CacheHandler successor;

    public abstract Object handle(Cache cache, Serialize serialize,Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable;

    public void setSuccessor(CacheHandler successor) {
        this.successor = successor;
    }

    public CacheHandler getSuccessor() {
        return successor;
    }
}
