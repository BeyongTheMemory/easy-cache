package com.pop.easycache.serialize.json;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pop.easycache.serialize.SerializeConfig;

/**
 * Created by xugang on 17/6/23.
 */
public class FastJsonConfig extends SerializeConfig{
    private SerializerFeature[] serializerFeatures;
    private Feature[] feature;

    public FastJsonConfig(SerializerFeature[] serializerFeatures, Feature[] feature) {
        this.serializerFeatures = serializerFeatures;
        this.feature = feature;
    }

    public SerializerFeature[] getSerializerFeatures() {
        return serializerFeatures;
    }

    public void setSerializerFeatures(SerializerFeature[] serializerFeatures) {
        this.serializerFeatures = serializerFeatures;
    }

    public Feature[] getFeature() {
        return feature;
    }

    public void setFeature(Feature[] feature) {
        this.feature = feature;
    }
}
