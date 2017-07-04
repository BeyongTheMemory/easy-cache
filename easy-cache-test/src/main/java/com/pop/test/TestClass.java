package com.pop.test;

import com.pop.easycache.annotion.NeedCache;
import org.springframework.stereotype.Component;

/**
 * Created by xugang on 17/2/27.
 */
@Component
public class TestClass implements TestInterface{
    @NeedCache(name = "testClass.getString",key ="#a" )
    public String getString(String a){
        System.out.println("getString run");
        return "xx";
    }
}
