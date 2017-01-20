package com.pop.local;

import java.util.concurrent.ExecutionException;

/**
 * Created by xugang on 17/1/18.
 */
public interface LocalCache {
    void set(Object key, Object value);

    void del(Object key);

    Object getStringByKey(Object key);
}
