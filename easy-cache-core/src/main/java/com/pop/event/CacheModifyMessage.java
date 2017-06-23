package com.pop.event;

import com.pop.enums.CacheModifyType;

/**
 * Created by xugang on 17/2/20.
 */
public class CacheModifyMessage {
    private String key;
    private String value;
    private CacheModifyType type;

    public CacheModifyMessage(String key, String value, CacheModifyType type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CacheModifyType getType() {
        return type;
    }

    public void setType(CacheModifyType type) {
        this.type = type;
    }
}
