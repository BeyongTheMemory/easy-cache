package com.pop.factory;

import com.pop.annotion.NeedCache;
import com.pop.proxy.CglibProxy;
import com.pop.proxy.JdkInvocationHandler;
import com.pop.test.SimpleNeedCache;
import com.pop.test.SimpleNeedCacheInterface;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xugang on 17/2/27.
 */
public class CacheFactoryBuilder  implements BeanPostProcessor{
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-redis.xml","spring/spring-cache.xml","spring/spring-registry.xml"});



    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        CglibProxy cglibProxy = new CglibProxy();
        //
        Method[] methods = bean.getClass().getMethods();
        for (Method method:methods){
            if(method.getAnnotation(NeedCache.class) != null){
                return cglibProxy.getProxyObject(bean.getClass());
            }
        }
        return null;
    }
}
