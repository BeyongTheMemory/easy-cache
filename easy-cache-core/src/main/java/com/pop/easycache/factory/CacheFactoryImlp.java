package com.pop.easycache.factory;

import com.google.common.base.Preconditions;
import com.pop.easycache.CacheBuilder;
import com.pop.easycache.cache.Cache;
import com.pop.easycache.cache.CacheImpl;
import com.pop.easycache.cache.local.LocalCache;
import com.pop.easycache.cache.local.LocalCacheFactory;
import com.pop.easycache.cache.local.guava.GuavaCacheConfig;
import com.pop.easycache.cache.local.guava.GuavaCacheFactory;
import com.pop.easycache.cache.remote.RemoteCache;
import com.pop.easycache.cache.remote.RemoteCacheFactory;
import com.pop.easycache.cache.remote.redis.RedisCacheConfig;
import com.pop.easycache.cache.remote.redis.RedisCacheFactory;
import com.pop.easycache.registry.Registry;
import com.pop.easycache.registry.RegistryFactory;
import com.pop.easycache.registry.zk.ZKConfig;
import com.pop.easycache.registry.zk.ZKFactory;
import com.pop.easycache.serialize.Serialize;
import com.pop.easycache.serialize.SerializeFactory;
import com.pop.easycache.serialize.json.FastJsonConfig;
import com.pop.easycache.serialize.json.FastJsonFactory;
import org.springframework.util.StringUtils;


/**
 * Created by xugang on 17/6/21.
 */
public class CacheFactoryImlp implements CacheFactory {
    private CacheBuilder builder;
    private Serialize serialize;

    public CacheFactoryImlp(CacheBuilder builder) {
        this.builder = builder;
    }

    public Cache getCache() {
        serialize = initSerialize();
        return new CacheImpl(initLocalCache(), initRemoteCache(), initRegistry(), serialize);
    }

    private RemoteCache initRemoteCache() {
        if (builder.isUseRemoteCache()) {
            RemoteCacheFactory remoteCacheFactory = null;
            Preconditions.checkArgument(!StringUtils.isEmpty(builder.getRemoteCacheType()), "error,the remoteCacheType is null");
            if (builder.getRemoteCacheType().contentEquals("redis")) {
                remoteCacheFactory = new RedisCacheFactory(new RedisCacheConfig(builder.getTtl(), builder.getTtlUnit(), builder.getJedisPool(), builder.getRedisUrl(), builder.getRedisPort()));
            }
            Preconditions.checkArgument(remoteCacheFactory != null, "can't support remoteCache type");
            return remoteCacheFactory.getRemoteCaChe();
        }
        return null;
    }


    private LocalCache initLocalCache() {
        if (builder.isUseLocalCache()) {
            LocalCacheFactory localCacheFactory = null;
            Preconditions.checkArgument(!StringUtils.isEmpty(builder.getLocalCacheType()), "error,the localCacheType is null");
            if (builder.getLocalCacheType().contentEquals("guava")) {
                localCacheFactory = new GuavaCacheFactory(new GuavaCacheConfig(builder.getTtl(), builder.getTtlUnit(), builder.getLocalMaxSize()));
            }
            Preconditions.checkArgument(localCacheFactory != null, "can't support localCache type");
            return localCacheFactory.getLocalCaChe();
        }
        return null;
    }

    private Serialize initSerialize() {
        SerializeFactory serializeFactory = null;
        Preconditions.checkArgument(!StringUtils.isEmpty(builder.getSerializeType()), "error,the serializeType is null");
        if (builder.getSerializeType().contentEquals("fastJson")) {
            serializeFactory = new FastJsonFactory(new FastJsonConfig(builder.getSerializerFeatures(), builder.getFeature()));
        }
        Preconditions.checkArgument(serializeFactory != null, "can't support serialize type");
        return serializeFactory.getSerialize();
    }

    private Registry initRegistry() {
        if (builder.isUseRegistry()) {
            RegistryFactory registryFactory = null;
            Preconditions.checkArgument(!StringUtils.isEmpty(builder.getRegistryType()), "error,the registryType is null");
            if (builder.getRegistryType().contentEquals("zooKeeper")) {
                registryFactory = new ZKFactory(new ZKConfig(builder.getRegistryServer(), builder.getRegistryPath(), builder.getZooKeeper(), builder.getSessionTimeOut()));
            }
            Preconditions.checkArgument(registryFactory != null, "can't support registry type");
            return registryFactory.getRegistry();
        }
        return null;
    }

    public Serialize getSerialize() {
        return serialize;
    }
}
