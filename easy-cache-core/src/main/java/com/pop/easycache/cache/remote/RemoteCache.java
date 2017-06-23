package com.pop.easycache.cache.remote;

/**
 * Created by xugang on 17/6/21.
 */
public interface RemoteCache {
    void set(String key, String value);

    void set(String key, String value, int second);

    void del(String key);

    String getStringByKey(String key);

}
