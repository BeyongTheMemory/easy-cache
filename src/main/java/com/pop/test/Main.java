package com.pop.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xugang on 17/1/19.
 */
public class Main {
    public static void main(String args[]) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-redis.xml","spring/spring-cache.xml","spring/spring-registry.xml"});
        SimpleNeedCache simple = (SimpleNeedCache)context.getBean("simple");
        System.out.println(simple.getSimpleObject("a",1).toString());
        simple.deleteCache();
        Thread.sleep(1000000000);
        System.out.println(simple.getSimpleObject("a",1).toString());


    }
}
