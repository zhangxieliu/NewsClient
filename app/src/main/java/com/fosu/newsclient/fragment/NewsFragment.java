package com.fosu.newsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.fosu.newsclient.R;
import com.fosu.newsclient.adapter.NewsListAdapter;
import com.fosu.newsclient.bean.HttpResult;
import com.fosu.newsclient.bean.NewsBean;
import com.fosu.newsclient.db.dao.NewsDao;
import com.fosu.newsclient.service.JuHeService;
import com.fosu.newsclient.utils.Commons;
import com.fosu.newsclient.view.BannerSliderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wang.avi.AVLoadingIndicatorView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Created by Administrator on 2016/10/16.
 */

public class NewsFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "NewsFragment";

    @BindView(R.id.listView)
    PullToRefreshListView listView;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    private List<NewsBean> news = new ArrayList<>();
    private Subscriber<List<NewsBean>> subscriber;
    private String type;
    private SliderLayout sliderLayout;
    private NewsListAdapter adapter;

    public static NewsFragment getInstance(String type) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getString("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        initPullToRefreshListView();
        initSlider();
        return view;
    }

    private void initPullToRefreshListView() {
        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放立即刷新");
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                initNewsData();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView.setOnItemClickListener(this);
        initNewsData();
        adapter = new NewsListAdapter(getActivity(), news);
        listView.setAdapter(adapter);
    }

    private void initSlider() {
        sliderLayout = new SliderLayout(getActivity());
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 500);
        sliderLayout.setLayoutParams(layoutParams);
        sliderLayout.setDuration(4000);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        listView.getRefreshableView().addHeaderView(sliderLayout);
    }

    private void loadBanner() {
        sliderLayout.removeAllSliders();
        for (int i = 0; i < 5; i++) {
            NewsBean newsBean = news.get(i);
            BannerSliderView textSliderView = new BannerSliderView(getActivity());
            textSliderView
                    .description(newsBean.getTitle())
                    .image(newsBean.getThumbnail_pic_s())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
//                    .empty(R.mipmap.ic_launcher)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            String url = slider.getBundle().getString("url");
                            startWebActivity(url);
                        }
                    });
            textSliderView
                    .bundle(new Bundle())
                    .getBundle()
                    .putString("url", newsBean.getUrl());
            sliderLayout.addSlider(textSliderView);
        }
    }

    private void initNewsData() {
        loadingIndicator.show();
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("key", Commons.NEWS_APPKEY);
        subscriber = getSubscriber();
        new Retrofit.Builder()
                .baseUrl(Commons.JU_HE_API_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(JuHeService.class)
                .getSearchNews(params)
                .map(new Func1<HttpResult, List<NewsBean>>() {
                    @Override
                    public List<NewsBean> call(HttpResult httpResult) {
                        if (httpResult.getErrorCode() != 0) {
                            throw new RuntimeException(httpResult.getErrorCode() + httpResult.getReason());
                        }
                        List<NewsBean> data = httpResult.getResult().getData();
                        for (NewsBean newsBean : data) {
                            newsBean.setTypeId(type);
                        }
                        return data;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 当事件传递到onError或者onCompleted之后，也会自动的解绑。
     *
     * @return
     */
    private Subscriber<List<NewsBean>> getSubscriber() {
        return new Subscriber<List<NewsBean>>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
                listView.onRefreshComplete();
                loadingIndicator.hide();
                loadBanner();
                try {
                    NewsDao.getInstance().deleteByType(type);
                    NewsDao.getInstance().save(news);
                } catch (DbException e) {
                    Toast.makeText(getActivity(), "本地数据保存失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
                listView.onRefreshComplete();
                loadingIndicator.hide();
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                try {
                    news.addAll(NewsDao.getInstance().find(type));
                    adapter.notifyDataSetChanged();
                    loadBanner();
                } catch (DbException e1) {
                }
            }

            @Override
            public void onNext(List<NewsBean> newsBeans) {
                news.clear();
                news.addAll(newsBeans);
                Log.i(TAG, "onNext: " + newsBeans.toString());
                adapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sliderLayout != null) {
            sliderLayout.startAutoCycle();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
        if (sliderLayout != null) {
            sliderLayout.stopAutoCycle();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = news.get(position - 2).getUrl();
        startWebActivity(url);
    }
}
