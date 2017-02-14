package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */

public class HttpResult {
    @JSONField(name = "error_code")
    private int errorCode;
    @JSONField(name = "reason")
    private String reason;
    @JSONField(name = "result")
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        private String stat;
        private List<NewsBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<NewsBean> getData() {
            return data;
        }

        public void setData(List<NewsBean> data) {
            this.data = data;
        }
    }
}
