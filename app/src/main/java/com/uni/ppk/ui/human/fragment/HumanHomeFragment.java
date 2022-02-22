package com.uni.ppk.ui.human.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.uni.ppk.R;
import com.uni.ppk.base.LazyBaseFragments;

/**
 * @ClassName HumanHomeFragment
 * @Description TODO
 * @Author LXF
 * @Date 2022/2/22 23:25
 * @Version 1.0
 */
public class HumanHomeFragment extends LazyBaseFragments {
    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView=inflater.inflate(R.layout.frag_human_home,null);
        return mRootView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
