package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class NewsEntity {
    @JSONField(name = "pubDate")
    private String pubDate;
    @JSONField(name = "havePic")
    private boolean havePic;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "channelName")
    private String channelName;
    @JSONField(name = "desc")
    private String desc;
    @JSONField(name = "source")
    private String source;
    @JSONField(name = "channelId")
    private String channelId;
    @JSONField(name = "link")
    private String link;
    @JSONField(name = "imageurls")
    private List<?> imageurls;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<?> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<?> imageurls) {
        this.imageurls = imageurls;
    }

    @Override
    public String toString() {
        return "Contentlist{" +
                "pubDate='" + pubDate + '\'' +
                ", havePic=" + havePic +
                ", title='" + title + '\'' +
                ", channelName='" + channelName + '\'' +
                ", desc='" + desc + '\'' +
                ", source='" + source + '\'' +
                ", channelId='" + channelId + '\'' +
                ", link='" + link + '\'' +
                ", imageurls=" + imageurls +
                '}';
    }
}
