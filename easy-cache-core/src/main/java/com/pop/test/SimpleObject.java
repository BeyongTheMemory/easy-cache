package com.pop.test;

import java.util.List;

/**
 * Created by xugang on 17/1/20.
 */
public class SimpleObject {
    private String string;
    private boolean bool;
    private int i;
    private List<String> stringList;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public String toString() {
        return "SimpleObject{" +
                "string='" + string + '\'' +
                ", bool=" + bool +
                ", i=" + i +
                ", stringList=" + stringList +
                '}';
    }
}
