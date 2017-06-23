package com.pop.easycache.cache.remote;

import com.google.common.base.Preconditions;
import com.pop.easycache.entity.ValidBean;

/**
 * Created by xugang on 17/6/22.
 */
public abstract class RemoteCacheFactory {

    public RemoteCache getRemoteCaChe(){
        ValidBean validBean = valid();
        Preconditions.checkArgument(validBean.isResult(),validBean.getMsg());
        return build();
    }

   protected abstract ValidBean valid();

   protected abstract RemoteCache build();

}
