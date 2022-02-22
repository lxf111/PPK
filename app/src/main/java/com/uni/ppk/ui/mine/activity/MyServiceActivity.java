package com.uni.ppk.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.ui.mine.adapter.HomeTabViewPagerAdapter;
import com.uni.ppk.ui.mine.fragment.MyServiceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:36
 * 我的咨询
 */
public class MyServiceActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.xTablayout)
    XTabLayout xTablayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<String> mTabNames = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_service;
    }

    @Override
    protected void initData() {
        initTitle("我的咨询");
        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                mTabNames.add("未回复");
            } else if (i == 1) {
                mTabNames.add("已回复");
            }
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            MyServiceFragment itemVideoFragment = new MyServiceFragment();
            itemVideoFragment.setArguments(bundle);
            mFragmentList.add(itemVideoFragment);
        }
        vpContent.setAdapter(new HomeTabViewPagerAdapter(getSupportFragmentManager(), mTabNames, mFragmentList));
        xTablayout.setupWithViewPager(vpContent);
    }

}
