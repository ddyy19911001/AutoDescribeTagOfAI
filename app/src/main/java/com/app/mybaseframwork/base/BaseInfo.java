package com.app.mybaseframwork.base;

import java.io.Serializable;

public class BaseInfo implements Serializable {
    int code;
    String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}
