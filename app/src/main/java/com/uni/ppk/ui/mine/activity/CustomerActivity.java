package com.uni.ppk.ui.mine.activity;

import android.widget.ImageView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/6
 * Time: 10:20
 * 人工服务
 */
public class CustomerActivity extends BaseActivity {
    @BindView(R.id.iv_code)
    ImageView ivCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer;
    }

    @Override
    protected void initData() {
        initTitle("人工服务");
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }

}
