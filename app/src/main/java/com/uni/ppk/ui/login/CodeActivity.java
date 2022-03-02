package com.uni.ppk.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MainActivity;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.login.bean.LoginBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.utils.TimerUtil;
import com.uni.ppk.widget.VerifyEditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @ClassName CodeActivity
 * @Description TODO
 * @Author LXF
 * @Date 2022/2/22 22:02
 * @Version 1.0
 */
public class CodeActivity extends BaseActivity {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.vet1)
    VerifyEditText vet1;
    @BindView(R.id.tv_no_receiver)
    TextView tvNoReceiver;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private String mPhone = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code;
    }

    @Override
    protected void initData() {
        mPhone = getIntent().getStringExtra("phone");
        TimerUtil timerUtil = new TimerUtil(tvCode);
        timerUtil.timers();
        vet1.addInputCompleteListener(new VerifyEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", "" + mPhone);
                params.put("event", "login");
                params.put("captcha", "" + content);
                HttpUtils.codeLogin(mContext, params, new MyCallBack() {
                    @Override
                    public void onSuccess(String response, String msg) {
                        LoginBean bean = JSONUtils.parserObject(response, "user", LoginBean.class);
                        if (bean != null) {
                            LoginCheckUtils.saveLoginInfo(bean);
                            MyApplication.openActivity(mContext, MainActivity.class);
                            finish();
                        } else {
                            ToastUtils.show(mContext, msg);
                        }
                    }

                    @Override
                    public void onError(String msg, int code) {
                        ToastUtils.show(mContext, msg);
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        ToastUtils.show(mContext, getString(R.string.service_error));
                    }
                });
            }
        });
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    @OnClick({R.id.iv_back, R.id.tv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_code:
                Map<String, Object> params = new HashMap<>();
                params.put("mobile", "" + mPhone);
                params.put("event", "login");
                HttpUtils.sendMessage(mContext, params, new MyCallBack() {
                    @Override
                    public void onSuccess(String response, String msg) {
                        TimerUtil timerUtil = new TimerUtil(tvCode);
                        timerUtil.timers();
                        ToastUtils.show(mContext, msg);
                    }

                    @Override
                    public void onError(String msg, int code) {
                        ToastUtils.show(mContext, msg);
                    }

                    @Override
                    public void onFail(Call call, IOException e) {
                        ToastUtils.show(mContext, getString(R.string.service_error));
                    }
                });
                break;
        }
    }
}
