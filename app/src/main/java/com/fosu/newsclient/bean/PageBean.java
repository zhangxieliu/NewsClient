package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class PageBean<T> {
    @JSONField(name = "allPages")
    private int allPages;
    @JSONField(name = "currentPage")
    private int currentPage;
    @JSONField(name = "allNum")
    private int allNum;
    @JSONField(name = "maxResult")
    private int maxResult;

    @JSONField(name = "contentlist")
    private List<T> contentlist;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<T> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<T> contentlist) {
        this.contentlist = contentlist;
    }
}
