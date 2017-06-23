package com.pop.test;

import com.pop.annotion.NeedCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xugang on 17/1/20.
 */
@Component
public class SimpleNeedCache implements SimpleNeedCacheInterface{
    @NeedCache(key = "#a",name = "xx")
    public SimpleObject getSimpleObject(String a,int i){
        System.out.println("getSimpleObject");
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setString(a);
        simpleObject.setI(i);
        List<String> ss = new ArrayList<String>();
        ss.add(a);
        ss.add(a+"2");
        simpleObject.setStringList(ss);
        return simpleObject;
    }
    public void deleteCache(String xa){
        System.out.println("delete");
    }

}
