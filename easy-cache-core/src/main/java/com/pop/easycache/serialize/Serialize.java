package com.pop.easycache.serialize;

/**
 * Created by xugang on 17/6/21.
 */
public interface Serialize {
    String objectToString(Object o);

     <T> T toObject(String obj, Class<T> clazz);
}
