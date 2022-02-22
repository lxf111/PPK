package com.uni.ppk.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
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
public class RetrievePasswordActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
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

    private boolean isWatchPwd = false;//密码不可见

    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrieve_password;
    }

    @Override
    protected void initData() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
    }

    @OnClick({R.id.tv_code, R.id.iv_back, R.id.iv_watch, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_code:
                //获取验证码
                sendMessage();
                break;
            case R.id.tv_login:
                //提交修改密码
                confirm();
                break;
            //查看密码
            case R.id.iv_watch:
                if (!isWatchPwd) {
                    isWatchPwd = true;
                    ivWatch.setImageResource(R.mipmap.ic_login_watch_pwd);
                    //从密码不可见模式变为密码可见模式
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isWatchPwd = false;
                    ivWatch.setImageResource(R.mipmap.ic_login_no_watch_pwd);
                    //从密码可见模式变为密码不可见模式
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
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
            ToastUtils.show(mContext, "密码必须是数字加字母");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "" + phone);
        params.put("code", "" + code);
        params.put("type", "2");
        params.put("password", "" + pwd);
        HttpUtils.forget(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                finish();
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
        params.put("type", "2");
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
