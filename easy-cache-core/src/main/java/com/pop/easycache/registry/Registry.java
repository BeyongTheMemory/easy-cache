package com.pop.easycache.registry;

/**
 * Created by xugang on 17/6/21.
 */
public interface Registry {
    String create(String key,String value);

    void del(String key);
}
