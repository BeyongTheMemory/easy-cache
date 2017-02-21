package com.pop.regist;

/**
 * Created by xugang on 17/2/20.
 */
public interface Registry {
    String create(String key,String value);
    void del(String key);
}
