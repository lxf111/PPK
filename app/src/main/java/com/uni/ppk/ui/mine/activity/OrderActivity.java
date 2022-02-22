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
import com.uni.ppk.ui.mine.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 16:57
 * 服务订单
 */
public class OrderActivity extends BaseActivity {
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
        return R.layout.activity_order;
    }

    @Override
    protected void initData() {
        initTitle("服务订单");
        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                mTabNames.add("待接单");
            } else if (i == 1) {
                mTabNames.add("进行中");
            } else if (i == 2) {
                mTabNames.add("已完成");
            }
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            OrderFragment itemVideoFragment = new OrderFragment();
            itemVideoFragment.setArguments(bundle);
            mFragmentList.add(itemVideoFragment);
        }
        vpContent.setAdapter(new HomeTabViewPagerAdapter(getSupportFragmentManager(), mTabNames, mFragmentList));
        xTablayout.setupWithViewPager(vpContent);
    }

}
