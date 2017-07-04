package com.pop.easycache.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by xugang on 17/7/2.
 */
public class RedisCacheInterceptor implements MethodInterceptor {
    private volatile int errorCount = -1;
    private int errorNum;
    public static boolean isNormal = true;


    public RedisCacheInterceptor(int errorNum) {
        this.errorNum = errorNum;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        try {
            if(isNormal) {
                Object result = proxy.invokeSuper(obj, args);
                errorCount = 0;
                return result;
            }else {//多次调用失败后降级
                return null;
            }
        }catch (Exception e){
            errorCount++;
            if(errorCount > errorNum){
                isNormal = false;
            }
            return null;
        }
    }


}
