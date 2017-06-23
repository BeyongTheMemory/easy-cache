package com.pop.easycache.registry;

import com.google.common.base.Preconditions;
import com.pop.easycache.entity.ValidBean;

/**
 * Created by xugang on 17/6/23.
 */
public abstract class RegistryFactory {

    public Registry getRegistry(){
        ValidBean validBean = valid();
        Preconditions.checkArgument(validBean.isResult(),validBean.getMsg());
        return build();
    }

    protected abstract ValidBean valid();

    protected abstract Registry build();
}
