package com.pop.remote;

/**
 * Created by xugang on 17/1/18.
 */
public interface RemoteCache {
    void set(String key, String value);

    void set(String key, String value, int second);

    void del(String key);

    String getStringByKey(String key);
}
