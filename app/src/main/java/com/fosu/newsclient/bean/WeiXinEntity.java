package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class WeiXinEntity {
    @JSONField(name = "totalPage")
    private int totalPage;
    @JSONField(name = "ps")
    private int ps;
    @JSONField(name = "pno")
    private int pno;

    @JSONField(name = "list")
    private List<Content> contents;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public static class Content {
        @JSONField(name = "id")
        private String id;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "source")
        private String source;
        @JSONField(name = "firstImg")
        private String firstImg;
        @JSONField(name = "mark")
        private String mark;
        @JSONField(name = "url")
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", source='" + source + '\'' +
                    ", firstImg='" + firstImg + '\'' +
                    ", mark='" + mark + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
