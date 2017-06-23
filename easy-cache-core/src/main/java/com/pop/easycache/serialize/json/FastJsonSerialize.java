package com.pop.easycache.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pop.easycache.serialize.Serialize;

/**
 * Created by xugang on 17/1/19.
 */
public class FastJsonSerialize implements Serialize {
    private SerializerFeature[] serializerFeatures;
    private Feature[] feature;

    public FastJsonSerialize(FastJsonConfig config) {
        this.feature = config.getFeature();
        this.serializerFeatures = config.getSerializerFeatures();
    }


    public <T> T toObject(String obj, Class<T> clazz) {
        return JSON.parseObject(obj, clazz,feature);
    }

    public String objectToString(Object o) {
        return JSON.toJSONString(o,serializerFeatures);
    }
}
