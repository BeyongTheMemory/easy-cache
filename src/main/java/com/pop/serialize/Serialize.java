package com.pop.serialize;

/**
 * Created by xugang on 17/1/19.
 */
public interface Serialize {
    String toString (Object obj);
    <T> T toObject(String obj,Class<T> clazz);
}
