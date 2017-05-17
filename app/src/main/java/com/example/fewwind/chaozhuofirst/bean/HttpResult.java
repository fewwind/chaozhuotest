package com.example.fewwind.chaozhuofirst.bean;

/**
 * Created by fewwind on 17-4-19.
 */

public class HttpResult<T> {
    public int code;
    public String msg;
    public T data;


    @Override public String toString() {
        return "HttpResult{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", data=" + data +
            '}';
    }
}
