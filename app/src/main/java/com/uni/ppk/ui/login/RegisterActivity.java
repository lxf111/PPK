package com.uni.ppk.ui.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.uni.ppk.NormalWebViewActivity;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.NormalWebViewConfig;
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
 * Describe:注册页面
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
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
    @BindView(R.id.edt_invite_code)
    EditText edtInviteCode;
    @BindView(R.id.tv_user_registration_agreement)
    TextView tvUserRegistrationAgreement;
    @BindView(R.id.llyt_agree)
    LinearLayout llytAgree;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_agree)
    TextView tvAgree;

    private boolean isWatchPwd = false;//密码不可见
    private boolean isAgree = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
    }

    @OnClick({R.id.iv_back, R.id.tv_code, R.id.iv_watch, R.id.tv_agree, R.id.llyt_agree, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_code:
                sendMessage();
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
            //注册协议
            case R.id.llyt_agree:
                getAgree();
                break;
            case R.id.tv_agree:
                if (isAgree) {
                    isAgree = false;
                    Drawable drawable = getResources().getDrawable(R.mipmap.icon_select_no);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvAgree.setCompoundDrawables(drawable, null, null, null);
                } else {
                    isAgree = true;
                    Drawable drawable = getResources().getDrawable(R.mipmap.icon_select_theme);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvAgree.setCompoundDrawables(drawable, null, null, null);
                }
                break;
            case R.id.tv_login:
                if (!isAgree) {
                    toast("请阅读并同意协议");
                    return;
                }
                confirm();
                break;
        }
    }

    private void getAgree() {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "1");
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                Bundle bundle = new Bundle();
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
                bundle.putString(NormalWebViewConfig.URL, "" + content);
                bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, true);
                MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, mContext.getResources().getString(R.string.service_error));
            }
        });
    }

    private void confirm() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();
        String inviteCode = edtInviteCode.getText().toString().trim();
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
        params.put("password", "" + pwd);
        params.put("invite_code", "" + inviteCode);
        params.put("type", "1");
        HttpUtils.register(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LoginBean bean = JSONUtils.parserObject(response, "userinfo", LoginBean.class);
                if (bean != null) {
                    LoginCheckUtils.saveLoginInfo(bean);
                    MyApplication.openActivity(mContext, MainActivity.class);
                    AppManager.getInstance().finishActivity(LoginActivity.class);
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
        params.put("type", "1");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
