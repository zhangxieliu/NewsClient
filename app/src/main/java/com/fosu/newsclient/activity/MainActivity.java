package com.fosu.newsclient.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fosu.newsclient.R;
import com.fosu.newsclient.fragment.HomeFragment;
import com.fosu.newsclient.fragment.JokeFragment;
import com.fosu.newsclient.fragment.WeiXinFragment;
import com.fosu.newsclient.fragment.WeiboFragment;
import com.fosu.newsclient.utils.CommonTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<Fragment> fragments;

    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;

    private LinearLayout[] linearLayouts;
    private int childCount;
    private FragmentManager manager;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonTools.initSystemBar(this, R.color.blue);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        initFragment();
        initBottomLayout();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new JokeFragment());
        fragments.add(new WeiboFragment());
        fragments.add(new WeiXinFragment());
        manager.beginTransaction()
                .add(R.id.layout_container, fragments.get(0))
                .add(R.id.layout_container, fragments.get(1))
                .add(R.id.layout_container, fragments.get(2))
                .add(R.id.layout_container, fragments.get(3))
                .hide(fragments.get(1))
                .hide(fragments.get(2))
                .hide(fragments.get(3))
                .commit();
    }

    private void initBottomLayout() {
        childCount = layoutBottom.getChildCount();
        linearLayouts = new LinearLayout[childCount];
        for (int i = 0; i < childCount; i++) {
            linearLayouts[i] = (LinearLayout) layoutBottom.getChildAt(i);
            linearLayouts[i].setTag(i);
            linearLayouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    setSelectedState(position);
                }
            });
        }
        linearLayouts[0].setSelected(true);
    }

    private void setSelectedState(int position) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        for (int i = 0; i < childCount; i++) {
            linearLayouts[i].setSelected(i == position);
            fragmentTransaction.hide(fragments.get(i));
        }
        fragmentTransaction.show(fragments.get(position)).commit();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}