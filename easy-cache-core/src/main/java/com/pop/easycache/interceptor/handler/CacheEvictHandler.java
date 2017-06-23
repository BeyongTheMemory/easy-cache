package com.pop.easycache.interceptor.handler;

import com.pop.easycache.cache.Cache;
import com.pop.easycache.serialize.Serialize;
import com.pop.easycache.util.KeySpELUtil;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/6/21.
 */
public class CacheEvictHandler extends CacheHandler{

    public Object handle(Cache cache, Serialize serialize, Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        if(cacheEvict != null) {
            String key;
            if (!StringUtils.isEmpty(cacheEvict.key()) && cacheEvict.key().startsWith("#")) {
                key = cacheEvict.value() + KeySpELUtil.getKey(method, args, cacheEvict.key());
            } else {
                key = cacheEvict.value() + cacheEvict.key();
            }
            cache.del(key);
        }else {
            if(getSuccessor() != null){
                return getSuccessor().handle(cache, serialize,  target,  method,  args,  methodProxy);
            }
        }
        return null;

    }
}
