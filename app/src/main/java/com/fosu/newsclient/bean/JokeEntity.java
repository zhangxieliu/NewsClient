package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

@Table(name = "joke_table")
public class JokeEntity {
    @Column(name = "text")
    @JSONField(name = "text")
    private String text;
    @Column(name = "hate")
    @JSONField(name = "hate")
    private String hate;
    @JSONField(name = "videotime")
    private String videotime;
    @JSONField(name = "voicetime")
    private String voicetime;
    @Column(name = "weixin_url")
    @JSONField(name = "weixin_url")
    private String weixinUrl;
    @JSONField(name = "profile_image")
    private String profileImage;
    @JSONField(name = "width")
    private String width;
    @JSONField(name = "voiceuri")
    private String voiceuri;
    @Column(name = "type")
    @JSONField(name = "type")
    private String type;
    @Column(name = "id", isId = true, autoGen = false)
    @JSONField(name = "id")
    private String id;
    @Column(name = "image3")
    @JSONField(name = "image3")
    private String image3;
    @Column(name = "love")
    @JSONField(name = "love")
    private String love;
    @JSONField(name = "height")
    private String height;
    @JSONField(name = "video_uri")
    private String videoUri;
    @JSONField(name = "voicelength")
    private String voicelength;
    @JSONField(name = "name")
    private String name;
    @Column(name = "create_time")
    @JSONField(name = "create_time")
    private String createTime;

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHate() {
        return hate;
    }

    public void setHate(String hate) {
        this.hate = hate;
    }

    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    public String getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(String voicetime) {
        this.voicetime = voicetime;
    }

    public String getWeixinUrl() {
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getVoiceuri() {
        return voiceuri;
    }

    public void setVoiceuri(String voiceuri) {
        this.voiceuri = voiceuri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getVoicelength() {
        return voicelength;
    }

    public void setVoicelength(String voicelength) {
        this.voicelength = voicelength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Contentlist{" +
                "text='" + text + '\'' +
                ", hate='" + hate + '\'' +
                ", videotime='" + videotime + '\'' +
                ", voicetime='" + voicetime + '\'' +
                ", weixinUrl='" + weixinUrl + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", width='" + width + '\'' +
                ", voiceuri='" + voiceuri + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", love='" + love + '\'' +
                ", height='" + height + '\'' +
                ", videoUri='" + videoUri + '\'' +
                ", voicelength='" + voicelength + '\'' +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
