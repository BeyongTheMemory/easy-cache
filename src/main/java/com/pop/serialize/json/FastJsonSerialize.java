package com.pop.serialize.json;

import com.alibaba.fastjson.JSON;
import com.pop.serialize.Serialize;

/**
 * Created by xugang on 17/1/19.
 */
public class FastJsonSerialize implements Serialize {
    public String toString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public <T> T toObject(String obj, Class<T> clazz) {
        return JSON.parseObject(obj,clazz);
    }
}
