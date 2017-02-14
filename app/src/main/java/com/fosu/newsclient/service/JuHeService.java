package com.fosu.newsclient.service;

import com.fosu.newsclient.bean.HttpResult;
import com.fosu.newsclient.bean.News;
import com.fosu.newsclient.bean.NewsBean;
import com.fosu.newsclient.bean.Response;
import com.fosu.newsclient.bean.WeiXinEntity;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/15.
 */

public interface JuHeService {
    @FormUrlEncoded
    @POST("toutiao/index")
    public Observable<HttpResult> getSearchNews(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("weixin/query")
    public Observable<Response<WeiXinEntity>> loadWeiXin(@FieldMap Map<String, String> params);
}
