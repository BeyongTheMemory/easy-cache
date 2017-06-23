package com.pop.easycache.interceptor.handler;

import com.pop.easycache.cache.Cache;
import com.pop.easycache.serialize.Serialize;
import com.pop.easycache.util.KeySpELUtil;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/6/21.
 */
public class CachaableHandler extends CacheHandler {
    public Object handle(Cache cache, Serialize serialize, Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        if (cacheable != null) {
            String className = target.getClass().getName();
            String methodName = method.getName();
            String returnTypeName = method.getReturnType().getName();
            if (returnTypeName.equals("void")) {
                return methodProxy.invokeSuper(target, args);
            }
            //get key
            String key;
            if (!StringUtils.isEmpty(cacheable.key()) || !StringUtils.isEmpty(cacheable.value())) {
                if (!StringUtils.isEmpty(cacheable.key()) && cacheable.key().startsWith("#")) {
                    key = cacheable.value() + KeySpELUtil.getKey(method, args, cacheable.key());
                } else {
                    key = cacheable.value() + cacheable.key();
                }
            } else {
                StringBuilder keyBuilder = new StringBuilder();
                keyBuilder.append(className).append("-").append(methodName).append("-").append(serialize.objectToString(args));
                key = keyBuilder.toString();
            }

            //get object
            Object cacheResult = cache.getStringByKey(key, method.getReturnType());
            if (cacheResult != null) {
                return cacheResult;
            }
            //cache miss
            Object methodResut = methodProxy.invokeSuper(target, args);
            if (methodResut != null) {
                cache.set(key, methodResut);
            }
            return methodResut;
        } else {
            if(getSuccessor() != null){
                return getSuccessor().handle(cache, serialize,  target,  method,  args,  methodProxy);
            }
        }
        return methodProxy.invokeSuper(target, args);
    }
}
