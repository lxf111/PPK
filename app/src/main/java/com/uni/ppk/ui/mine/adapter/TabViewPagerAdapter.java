package com.uni.ppk.ui.mine.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 *
 * tabLayout与ViewPager联用使用的adapter
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabName;
    private List<Fragment> mFragments;

    public TabViewPagerAdapter(FragmentManager fm, String[] tabName, List<Fragment> fragments) {
        super(fm);
        this.mTabName = tabName;
        this.mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabName[position];
    }

}
