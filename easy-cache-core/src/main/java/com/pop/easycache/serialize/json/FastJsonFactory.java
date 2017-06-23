package com.pop.easycache.serialize.json;

import com.pop.easycache.serialize.Serialize;
import com.pop.easycache.serialize.SerializeFactory;

/**
 * Created by xugang on 17/6/23.
 */
public class FastJsonFactory extends SerializeFactory{
    private FastJsonConfig config;

    public FastJsonFactory(FastJsonConfig config) {
        this.config = config;
    }

    protected Serialize bulid() {
        return  new FastJsonSerialize(config);
    }
}
