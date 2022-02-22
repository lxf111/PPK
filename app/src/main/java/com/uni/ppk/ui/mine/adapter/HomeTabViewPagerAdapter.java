package com.uni.ppk.ui.mine.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uni.ppk.base.LazyBaseFragments;

import java.util.List;

/**
 * Create by wanghk on 2019-05-27.
 * Describe:首页viewpager适配器
 */
public class HomeTabViewPagerAdapter extends FragmentPagerAdapter {

    //tab数据
    private List<String> mDatas;

    private List<LazyBaseFragments> mFragments;

    public HomeTabViewPagerAdapter(FragmentManager fm, List<String> mDatas, List<LazyBaseFragments> fragments) {
        super(fm);
        this.mDatas = mDatas;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size() == mFragments.size() ? mFragments.size() : 0;
    }

    /**
     * 重写此方法，返回TabLayout的内容
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position);
    }
}
