package com.fosu.newsclient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class NewsChannel {
    @JSONField(name = "totalNum")
    private int totalNum;
    @JSONField(name = "ret_code")
    private int retCode;

    @JSONField(name = "channelList")
    private List<ChannelList> channelList;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public List<ChannelList> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<ChannelList> channelList) {
        this.channelList = channelList;
    }

    public static class ChannelList {
        @JSONField(name = "channelId")
        private String channelId;
        @JSONField(name = "name")
        private String name;

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ChannelList{" +
                    "channelId='" + channelId + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
