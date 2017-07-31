package com.tyut.feiyu.myapplication;

/**
 * Created by feiyu on 2017/4/27.
 */

public class Msginfo {
    private String left_text;
    private String right_text;

    public Msginfo(String left_text, String right_text) {
        this.left_text = left_text;
        this.right_text = right_text;
    }

    public String getLeft_text() {
        return left_text;
    }

    public String getRight_text() {
        return right_text;
    }
}
