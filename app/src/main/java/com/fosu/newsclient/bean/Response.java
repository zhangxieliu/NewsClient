package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2016/10/19.
 */

public class Response<T> {
    @JSONField(name = "error_code")
    private int errorCode;
    @JSONField(name = "reason")
    private String reason;
    @JSONField(name = "result")
    private T result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
