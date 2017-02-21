package com.pop.intercepter;

import com.google.common.base.Preconditions;
import com.pop.annotion.CacheEvict;
import com.pop.annotion.NeedCache;
import com.pop.cache.Cache;
import com.pop.serialize.Serialize;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * Created by xugang on 17/1/19.
 */
@Component
@Aspect
public class CacheEvictIntercepter {
    @Resource
    Cache cache;
    @Around(value = "@annotation(com.pop.annotion.CacheEvict)")
    public Object cacheEvict(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        CacheEvict cacheEvict = signature.getMethod().getAnnotation(CacheEvict.class);
        String key = cacheEvict.name()+cacheEvict.key();
        cache.del(key);
        return pjp.proceed();
    }
}