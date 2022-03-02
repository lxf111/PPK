package com.uni.ppk.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Create by wanghk on 2019-05-28.
 * Describe:
 */
public class BindPhoneActivity extends BaseActivity {


    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_86)
    TextView tv86;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.rlyt_code)
    RelativeLayout rlytCode;
    @BindView(R.id.iv_watch)
    ImageView ivWatch;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.rlyt_pwd)
    RelativeLayout rlytPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private String mOpenId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initData() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
        mOpenId = getIntent().getStringExtra("openId");
    }

    @OnClick({R.id.tv_code, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                //获取验证码
                sendMessage();
                break;
            case R.id.tv_login:
                //提交修改密码
                confirm();
                break;
        }
    }

    //提交修改密码
    private void confirm() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            ToastUtils.show(mContext, "请输入验证码");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtils.show(mContext, "请输入密码");
            return;
        }
        if (pwd.length() < 6) {
            ToastUtils.show(mContext, "密码至少六位");
            return;
        }
        if (!InputCheckUtil.isLetterDigit(pwd)) {
            ToastUtils.show(mContext, "请输入密码(6~12位字母+数字)");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("platform", "" + 1);
        params.put("unionID", "" + mOpenId);
        params.put("mobile", "" + phone);
        params.put("captcha", "" + code);
        params.put("password", "" + pwd);
        params.put("event", "bindThird");
        HttpUtils.bindPhone(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LoginBean bean = JSONUtils.parserObject(response, "user", LoginBean.class);
                if (bean != null) {
                    LoginCheckUtils.saveLoginInfo(bean);
                    AppManager.getInstance().finishActivity(LoginActivity.class);
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

    //获取验证码
    private void sendMessage() {
        String phone = edtPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "" + phone);
        params.put("event", "bindThird");
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
    }

    protected int getStatusBarColor() {
        return R.color.transparent;
    }

}
