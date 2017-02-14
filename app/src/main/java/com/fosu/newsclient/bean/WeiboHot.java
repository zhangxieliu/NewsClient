package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2016/10/19.
 */

public class WeiboHot {
    @JSONField(name = "typeName")
    private String typeName;
    @JSONField(name = "num")
    private int num;
    @JSONField(name = "desc")
    private String desc;
    @JSONField(name = "newinfo")
    private String newinfo;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "img")
    private String img;
    @JSONField(name = "influence")
    private String influence;
    @JSONField(name = "date")
    private int date;
    @JSONField(name = "space")
    private String space;
    @JSONField(name = "typeId")
    private String typeId;
    @JSONField(name = "url")
    private String url;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNewinfo() {
        return newinfo;
    }

    public void setNewinfo(String newinfo) {
        this.newinfo = newinfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInfluence() {
        return influence;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WeiboHot{" +
                "typeName='" + typeName + '\'' +
                ", num=" + num +
                ", desc='" + desc + '\'' +
                ", newinfo='" + newinfo + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", influence='" + influence + '\'' +
                ", date=" + date +
                ", space='" + space + '\'' +
                ", typeId='" + typeId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
