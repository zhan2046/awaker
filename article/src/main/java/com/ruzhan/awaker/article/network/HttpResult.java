package com.ruzhan.awaker.article.network;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright ©2017 by ruzhan
 */

public class HttpResult<T> {

    @SerializedName("info")
    private String info;
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private T data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
