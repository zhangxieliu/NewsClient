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
import com.fosu.newsclient.adapter.WeiboAdapter;
import com.fosu.newsclient.bean.HttpResponse;
import com.fosu.newsclient.bean.PageBean;
import com.fosu.newsclient.bean.ShowapiResBody;
import com.fosu.newsclient.bean.WeiboHot;
import com.fosu.newsclient.service.ShowApiService;
import com.fosu.newsclient.utils.Commons;
import com.fosu.newsclient.utils.Tools;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WeiboTypeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "WeiboTypeFragment";
    @BindView(R.id.listView)
    PullToRefreshListView listView;

    private int tid;
    private int page = 1;
    private Subscriber<List<WeiboHot>> subscriber;
    private WeiboAdapter adapter;
    private PageBean<WeiboHot> pageBean;
    private List<WeiboHot> contents = new ArrayList<>();

    public static WeiboTypeFragment getInstance(int tid) {
        WeiboTypeFragment healthyTypeFragment = new WeiboTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tid", tid);
        healthyTypeFragment.setArguments(bundle);
        return healthyTypeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        tid = bundle.getInt("tid");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initPullToRefreshListView();
        loadData(0);
    }

    private void initPullToRefreshListView() {
        listView.setOnItemClickListener(this);
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
                if (page <= pageBean.getAllPages()) {
                    page++;
                    loadData(1);
                }
            }
        });
    }

    private void loadData(int what) {
        subscriber = getSubscriber(what);
        Map<String, String> params = new HashMap<>();
        params.put("showapi_appid", Commons.SHOW_API_APPID);
        params.put("showapi_sign", Commons.SHOW_API_SECRET);
        params.put("typeId", String.valueOf(tid));
        params.put("space", "day");
        params.put("page", String.valueOf(page));
        Tools.getRetrofit()
                .create(ShowApiService.class)
                .loadWeibo(params)
                .map(new Func1<HttpResponse<ShowapiResBody<PageBean<WeiboHot>>>, List<WeiboHot>>() {
                    @Override
                    public List<WeiboHot> call(HttpResponse<ShowapiResBody<PageBean<WeiboHot>>> showapiResBodyHttpResponse) {
                        Log.i(TAG, "call: " + showapiResBodyHttpResponse.getShowapiResBody().getPagebean().getContentlist());
                        if (showapiResBodyHttpResponse.getShowapiResCode() != 0) {
                            throw new RuntimeException("showapi_res_code:" + showapiResBodyHttpResponse.getShowapiResCode() + ",showapi_res_error" + showapiResBodyHttpResponse.getShowapiResError());
                        }
                        pageBean = showapiResBodyHttpResponse.getShowapiResBody().getPagebean();
                        return showapiResBodyHttpResponse.getShowapiResBody().getPagebean().getContentlist();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private Subscriber<List<WeiboHot>> getSubscriber(final int what) {
        return new Subscriber<List<WeiboHot>>() {
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
            public void onNext(List<WeiboHot> weiboHots) {
                for (WeiboHot w : weiboHots) {
                    Log.i(TAG, "onNext: " + w.toString());
                }
                if (what == 0) {
                    contents.clear();
                    contents.addAll(weiboHots);
                    adapter = new WeiboAdapter(getActivity(), contents);
                    listView.setAdapter(adapter);
                } else if (what == 1) {
                    contents.addAll(weiboHots);
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", contents.get(position - 1).getUrl());
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }
}
