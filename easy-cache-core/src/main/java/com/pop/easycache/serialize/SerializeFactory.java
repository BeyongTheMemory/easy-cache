package com.pop.easycache.serialize;

/**
 * Created by xugang on 17/6/23.
 */
public abstract class SerializeFactory {
    public  Serialize getSerialize(){
        return bulid();
    }

    protected abstract Serialize bulid();
}
