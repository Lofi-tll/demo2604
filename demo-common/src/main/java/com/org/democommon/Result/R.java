package com.org.democommon.Result;


import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success() {
        R<T> result = new R<T>();
        result.code = 200;
        return result;
    }

    public static <T> R<T> success(T data) {
        R<T> result = new R<T>();
        result.code = 200;
        result.data = data;
        return result;
    }

    public static <T> R<T> error(T data) {
        R<T> result = new R<T>();
        result.code = 500;
        result.data = data;
        return result;
    }

    public static <T> R<T> error(Integer code, String message) {
        R<T> result = new R<T>();
        result.code = code;
        result.msg = message;
        return result;
    }

}
