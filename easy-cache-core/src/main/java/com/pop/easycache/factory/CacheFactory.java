package com.pop.easycache.factory;

import com.pop.easycache.cache.Cache;
import com.pop.easycache.serialize.Serialize;

/**
 * Created by xugang on 17/6/21.
 */
public interface CacheFactory {
    Cache getCache();

    Serialize getSerialize();
}
