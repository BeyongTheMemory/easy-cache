package com.pop.easycache.cache;

/**
 * Created by xugang on 17/6/21.
 */
public interface Cache {
     void set(String key, Object value);

     void del(String key);

     <T> T getStringByKey(String key, Class<T> clazz);


}
