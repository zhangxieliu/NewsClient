package com.fosu.newsclient.service;

import com.fosu.newsclient.bean.HttpResponse;
import com.fosu.newsclient.bean.JokeEntity;
import com.fosu.newsclient.bean.NewsChannel;
import com.fosu.newsclient.bean.NewsPageBean;
import com.fosu.newsclient.bean.PageBean;
import com.fosu.newsclient.bean.ShowapiResBody;
import com.fosu.newsclient.bean.WeiboHot;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/17.
 */

public interface ShowApiService {
    @FormUrlEncoded
    @POST("109-34")
    public Observable<HttpResponse<NewsChannel>> loadNewsChannel(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("109-35")
    public Observable<HttpResponse<ShowapiResBody<NewsPageBean>>> loadNews(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("255-1")
    public Observable<HttpResponse<ShowapiResBody<PageBean<JokeEntity>>>> loadJoke(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("254-1")
    public Observable<HttpResponse<ShowapiResBody<PageBean<WeiboHot>>>> loadWeibo(@FieldMap Map<String, String> params);

}
