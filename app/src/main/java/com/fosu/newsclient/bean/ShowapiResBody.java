package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2016/10/19.
 */

public class ShowapiResBody<T> {
    @JSONField(name = "ret_code")
    private int retCode;

    @JSONField(name = "pagebean")
    private T pagebean;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public T getPagebean() {
        return pagebean;
    }

    public void setPagebean(T pagebean) {
        this.pagebean = pagebean;
    }

}
