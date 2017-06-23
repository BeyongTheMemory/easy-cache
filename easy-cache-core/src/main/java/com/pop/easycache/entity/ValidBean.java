package com.pop.easycache.entity;

/**
 * Created by xugang on 17/6/23.
 */
public class ValidBean {
    private boolean result;
    private String msg;

    public ValidBean(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
