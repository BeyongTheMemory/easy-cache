package com.pop.config.local;

import java.util.concurrent.TimeUnit;

/**
 * Created by xugang on 17/2/16.
 */
public abstract class AbstractLocalConfig {
    private long localMaxSize = -1;//defalut unlimited size
    private long localTTL = -1;//expire after write ,defalut no ttl
    private TimeUnit tTLUnit =  TimeUnit.SECONDS;

    public long getLocalMaxSize() {
        return localMaxSize;
    }

    public void setLocalMaxSize(long localMaxSize) {
        this.localMaxSize = localMaxSize;
    }

    public long getLocalTTL() {
        return localTTL;
    }

    public void setLocalTTL(long localTTL) {
        this.localTTL = localTTL;
    }

    public TimeUnit gettTLUnit() {
        return tTLUnit;
    }

    public void settTLUnit(TimeUnit tTLUnit) {
        this.tTLUnit = tTLUnit;
    }
}
