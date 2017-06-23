package com.pop.easycache.registry.zk;

import com.pop.easycache.entity.ValidBean;
import com.pop.easycache.registry.Registry;
import com.pop.easycache.registry.RegistryFactory;
import org.springframework.util.StringUtils;

/**
 * Created by xugang on 17/6/23.
 */
public class ZKFactory extends RegistryFactory{
    private ZKConfig config;

    public ZKFactory(ZKConfig config) {
        this.config = config;
    }

    protected ValidBean valid() {
        if(StringUtils.isEmpty(config.getServer())){
            return new ValidBean(false,"zk server address is null");
        }
        return new ValidBean(true,null);
    }

    protected Registry build() {
        return new ZKRegistry(config);
    }
}
