package com.pop.intercepter;

import com.google.common.base.Preconditions;
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
public class NeedCacheIntercepter {
    @Resource
    Cache cache;
    @Resource
    Serialize serialize;
    @Around(value = "@annotation(com.pop.annotion.NeedCache)")
    public Object cache(ProceedingJoinPoint pjp){
        Object target = pjp.getTarget();
        String className = target.getClass().getName();
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        NeedCache needCacheAnnotion = method.getAnnotation(NeedCache.class);
        String returnTypeName = signature.getReturnType().getName();
        Preconditions.checkArgument(!returnTypeName.equals("void"),"can't cache void method!");
        cache.setNeedRemote(needCacheAnnotion.needRemote());
        //get key
        String key;
        if(StringUtils.isEmpty(needCacheAnnotion.key())){
            key = needCacheAnnotion.key();
        }else {
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder.append(className).append("-").append(methodName).append("-").append(serialize.toString(pjp.getArgs()));
            key = keyBuilder.toString();
        }

        //get object
        Object cacheResult = cache.getStringByKey(key,signature.getReturnType());
        if(cacheResult != null){
            return cacheResult;
        }
        //no cache
        try {
            Object methodResut = pjp.proceed();
            if(methodResut != null) {
                cache.set(key, methodResut);
            }
            return methodResut;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;




    }
}
