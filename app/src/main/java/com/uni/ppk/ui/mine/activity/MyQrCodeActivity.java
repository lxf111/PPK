package com.uni.ppk.ui.mine.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/6
 * Time: 10:16
 * 我的二维码
 */
public class MyQrCodeActivity extends BaseActivity {
    @BindView(R.id.iv_code)
    ImageView ivCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_qr_code;
    }

    @Override
    protected void initData() {
        initTitle("我的二维码");
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
