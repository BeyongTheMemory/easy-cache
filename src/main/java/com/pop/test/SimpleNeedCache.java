package com.pop.test;

import com.pop.annotion.CacheEvict;
import com.pop.annotion.NeedCache;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xugang on 17/1/20.
 */
@Component
public class SimpleNeedCache {
    @NeedCache(key = "ss",name = "xx")
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
    @CacheEvict(key = "ss",name = "xx")
    public void deleteCache(){
        System.out.println("delete");
    }

}
