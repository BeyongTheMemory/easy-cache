package com.pop.easycache.cache.local;

import com.google.common.base.Preconditions;
import com.pop.easycache.entity.ValidBean;

/**
 * Created by xugang on 17/6/22.
 */
public abstract class LocalCacheFactory {

    public LocalCache getLocalCaChe(){
        ValidBean validBean = valid();
        Preconditions.checkArgument(validBean.isResult(),validBean.getMsg());
        return build();
    }

   protected abstract ValidBean valid();

   protected abstract LocalCache build();

}
