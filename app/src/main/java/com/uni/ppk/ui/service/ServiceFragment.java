package com.uni.ppk.ui.service;

import android.view.LayoutInflater;
import android.view.View;

import com.uni.ppk.R;
import com.uni.ppk.base.LazyBaseFragments;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:16
 * 技能帮扶（发布服务选择页）
 */
public class ServiceFragment extends LazyBaseFragments {
    private static final String TAG = "ServiceFragment";

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_service, null);
        return mRootView;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

}
