package com.fosu.newsclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fosu.newsclient.R;
import com.fosu.newsclient.activity.WebActivity;
import com.fosu.newsclient.adapter.WeiXinAdapter;
import com.fosu.newsclient.bean.Response;
import com.fosu.newsclient.bean.WeiXinEntity;
import com.fosu.newsclient.service.JuHeService;
import com.fosu.newsclient.utils.Commons;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/19.
 */

public class WeiXinFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "WeiXinFragment";

    @BindView(R.id.listView)
    PullToRefreshListView listView;
    private int page = 1;
    private WeiXinEntity entity;
    private List<WeiXinEntity.Content> contents = new ArrayList<>();
    private WeiXinAdapter adapter;
    private Subscriber<WeiXinEntity> subscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initPullToRefreshListView();
        listView.setOnItemClickListener(this);
        loadData(0);
    }

    private void initPullToRefreshListView() {
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放立即刷新");
        // 下拉加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放立即加载");
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                loadData(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (page <= entity.getTotalPage()) {
                    page++;
                    loadData(1);
                }
            }
        });
    }

    private void loadData(int what) {
        subscriber = initSubscriber(what);
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "5445b9761b1ffd0414d08cac6849bb7c");
        params.put("pno", String.valueOf(page));
        new Retrofit.Builder()
                .baseUrl(Commons.JU_HE_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build()
                .create(JuHeService.class)
                .loadWeiXin(params)
                .map( new Func1<Response<WeiXinEntity>, WeiXinEntity>() {
                    @Override
                    public WeiXinEntity call(Response<WeiXinEntity> weiXinEntityResponse) {
                        if (weiXinEntityResponse.getErrorCode() != 0) {
                            throw new RuntimeException("error_code:" + weiXinEntityResponse.getErrorCode() + "reason:" + weiXinEntityResponse.getReason());
                        }
                        return weiXinEntityResponse.getResult();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private Subscriber<WeiXinEntity> initSubscriber(final int what) {
        return new Subscriber<WeiXinEntity>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
                listView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(WeiXinEntity weiXinEntity) {
                entity = weiXinEntity;
                Log.i(TAG, "onNext: " + weiXinEntity.getContents().toString());
                if (what == 0) {
                    contents.clear();
                    contents.addAll(weiXinEntity.getContents());
                    adapter = new WeiXinAdapter(getActivity(), contents);
                    listView.setAdapter(adapter);
                } else if (what == 1) {
                    contents.addAll(weiXinEntity.getContents());
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", contents.get(position - 1).getUrl());
        startActivity(intent);
    }
}
