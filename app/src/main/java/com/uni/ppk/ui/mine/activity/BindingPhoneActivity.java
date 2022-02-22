package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机号前的展示界面
 */
public class BindingPhoneActivity extends BaseActivity {
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_exchange)
    Button btnExchange;

    private String mPhone = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_binding_phone;
    }

    @Override
    protected void initData() {
        mPhone = getIntent().getStringExtra("phone");

        initTitle("" + getString(R.string.binding_phone));

        tvPhone.setText("" + mPhone);
    }

    @OnClick(R.id.btn_exchange)
    public void onViewClicked() {
        //更换手机号
        Intent intentPhone = new Intent(BindingPhoneActivity.this, UpdatePhoneActivity.class);
        intentPhone.putExtra("phone", "" + mPhone);
        startActivity(intentPhone);
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }
}
