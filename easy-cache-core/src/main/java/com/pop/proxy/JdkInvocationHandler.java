package com.pop.proxy;

import com.pop.annotion.NeedCache;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xugang on 17/3/2.
 */
public class JdkInvocationHandler implements InvocationHandler{
    private Object target;
    public JdkInvocationHandler(Object target){
        this.target = target;
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations =method.getDeclaredAnnotations();
        NeedCache needCache = method.getAnnotation(NeedCache.class);
        if(needCache != null){
            System.out.println("begin");
            return method.invoke(target,args);
        }
        return null;
    }
}
