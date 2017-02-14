package com.fosu.newsclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
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
 * Created by Administrator on 2016/10/16.
 */

public class JokeFragment extends Fragment {

    @BindView(R.id.pager_type)
    ViewPager pagerType;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tab_indicator)
    TabPageIndicator tabIndicator;
    private String[] titles = new String[]{"图片", "段子", "声音", "视频"};
    private List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(JokeTypeFragment.getInstance("10"));
        fragments.add(JokeTypeFragment.getInstance("29"));
        fragments.add(JokeTypeFragment.getInstance("31"));
        fragments.add(JokeTypeFragment.getInstance("41"));
        pagerType.setAdapter(new MainPagerAdapter(getChildFragmentManager(), fragments, titles));
        tabIndicator.setViewPager(pagerType);
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String search = etSearch.getText().toString();
                    Intent intent = new Intent("search");
                    intent.putExtra("search", search);
                    getActivity().sendBroadcast(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
