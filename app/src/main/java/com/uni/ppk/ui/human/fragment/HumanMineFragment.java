package com.uni.ppk.ui.human.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.uni.ppk.R;
import com.uni.ppk.base.LazyBaseFragments;

/**
 * @ClassName HumanMineFragment
 * @Description TODO
 * @Author LXF
 * @Date 2022/2/28 21:26
 * @Version 1.0
 */
public class HumanMineFragment extends LazyBaseFragments {
    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_human_mine, null);
        return mRootView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
