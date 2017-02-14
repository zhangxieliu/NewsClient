package com.fosu.newsclient.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.fosu.newsclient.activity.WebActivity;

/**
 * Created by Administrator on 2016/10/16.
 */

public class BaseFragment extends Fragment {

    public void startWebActivity(String url) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
