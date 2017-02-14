package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2016/10/16.
 */

public class HttpResponse<T> {

    @JSONField(name = "showapi_res_code")
    private int showapiResCode;
    @JSONField(name = "showapi_res_error")
    private String showapiResError;

    @JSONField(name = "showapi_res_body")
    private T showapiResBody;

    public int getShowapiResCode() {
        return showapiResCode;
    }

    public void setShowapiResCode(int showapiResCode) {
        this.showapiResCode = showapiResCode;
    }

    public String getShowapiResError() {
        return showapiResError;
    }

    public void setShowapiResError(String showapiResError) {
        this.showapiResError = showapiResError;
    }

    public T getShowapiResBody() {
        return showapiResBody;
    }

    public void setShowapiResBody(T showapiResBody) {
        this.showapiResBody = showapiResBody;
    }
}
