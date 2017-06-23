package com.pop.intercepter;

import com.google.common.base.Preconditions;
import com.pop.annotion.NeedCache;
import com.pop.cache.Cache;
import com.pop.serialize.Serialize;
import com.pop.util.KeySpELUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by xugang on 17/1/19.
 */
public class NeedCacheIntercepter implements MethodInterceptor {
    private Cache cache;
    private Serialize serialize;

    public NeedCacheIntercepter(Cache cache, Serialize serialize) {
        this.cache = cache;
        this.serialize = serialize;
    }

    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        NeedCache needCache = method.getAnnotation(NeedCache.class);
        if (needCache != null) {
            String className = target.getClass().getName();
            String methodName = method.getName();
            String returnTypeName = method.getReturnType().getName();
            Preconditions.checkArgument(!returnTypeName.equals("void"), "can't cache void method!");
            cache.setNeedRemote(needCache.needRemote());
            //get key
            String key;
            if (!StringUtils.isEmpty(needCache.key()) || !StringUtils.isEmpty(needCache.name())) {
                if (!StringUtils.isEmpty(needCache.key()) && needCache.key().startsWith("#")) {
                    key = needCache.name() + KeySpELUtil.getKey(method, args, needCache.key());
                } else {
                    key = needCache.name() + needCache.key();
                }
            } else {
                StringBuilder keyBuilder = new StringBuilder();
                keyBuilder.append(className).append("-").append(methodName).append("-").append(serialize.toString(args));
                key = keyBuilder.toString();
            }

            //get object
            Object cacheResult = cache.getStringByKey(key, method.getReturnType());
            if (cacheResult != null) {
                return cacheResult;
            }
            //no cache
            Object methodResut = methodProxy.invokeSuper(target, args);
            if (methodResut != null) {
                cache.set(key, methodResut);
            }
            return methodResut;
        }else {
            return methodProxy.invokeSuper(target, args);
        }
    }
}
