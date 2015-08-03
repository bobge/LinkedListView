package com.duora.linkedlistview.javabean;

import java.util.List;

/**
 * Created by bobge on 15/7/31.
 */
public class Catogray {
    private String kind;
    private List<Goods> list;
    private int count;
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
