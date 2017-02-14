package com.fosu.newsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fosu.newsclient.R;
import com.fosu.newsclient.adapter.MainPagerAdapter;
import com.fosu.newsclient.view.ClearEditText;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WeiboFragment extends Fragment {
    private static final String TAG = "WeiboFragment";
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.pager_type)
    ViewPager pagerType;
    @BindView(R.id.tab_indicator)
    TabPageIndicator tabIndicator;

    private int[] typeIds = new int[]{1, 2, 3, 4, 6, 7, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19,
            21, 22, 23, 777, 25, 26, 27, 28, 30, 31, 888, 999, 36, 37, 38, 39, 40, 41, 42, 101};
    private String[] titles = {"互联网", "科学", "历史", "军事", "数码", "人文", "宠物", "星座", "搞笑", "情感",
            "媒体", "养生", "音乐", "电视剧", "电影", "综艺", "摄影", "健身", "体育", "美食", "旅游", "财经", "医疗",
            "读书", "房地产", "汽车", "时尚", "美妆", "教育", "动漫", "游戏", "法律", "母婴", "娱乐", "收藏", "科技观察"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        Log.i(TAG, "onCreateView: ");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initFragment();
    }


    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(WeiboTypeFragment.getInstance(typeIds[i]));
        }
        pagerType.setAdapter(new MainPagerAdapter(getChildFragmentManager(), fragments, titles));
        tabIndicator.setViewPager(pagerType);
    }
}
