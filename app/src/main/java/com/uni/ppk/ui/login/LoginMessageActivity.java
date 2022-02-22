package com.uni.ppk.ui.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by wanghk on 2019-05-28.
 * Describe:短信验证登录
 */
public class LoginMessageActivity extends BaseActivity {

    @BindView(R.id.edt_login_account)
    EditText edtLoginAccount;
    @BindView(R.id.edt_verification_code)
    EditText edtVerificationCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register_account)
    TextView tvRegisterAccount;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_get_verification_code)
    TextView tvGetVerificationCode;
    @BindView(R.id.iv_wechat_login)
    ImageView ivWechatLogin;

    //验证码倒计时
    private CountDownTimer mCountDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_message;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_login, R.id.tv_register_account, R.id.tv_forget_password, R.id.iv_wechat_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //登录
                login();
                break;
            case R.id.tv_register_account:
                //跳转注册页面
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget_password:
                //跳转找回密码页面
                startActivity(new Intent(this, RetrievePasswordActivity.class));
                break;
            case R.id.iv_wechat_login:
                //微信登录
                break;
        }
    }

    //获取验证码
    private void getVerificationCode() {

        String phone = edtLoginAccount.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            toast(getString(R.string.iphone_number_not_null));
            return;
        }
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long time = millisUntilFinished / 1000;
                    tvGetVerificationCode.setClickable(false);
                    tvGetVerificationCode.setText(time + getResources().getString(R.string.resend_verification_code));
                }

                @Override
                public void onFinish() {
                    tvGetVerificationCode.setClickable(true);
                    tvGetVerificationCode.setText(getResources().getString(R.string.get_verification_code));
                }
            };
        }
        mCountDownTimer.start();

        // TODO: 2019/5/28 0028 请求获取验证码的接口
        //type 1=注册(未注册手机号),2=忘记密码(已经注册的手机号),3=短信登录,4修改手机号

    }

    //登录
    private void login() {
        String phone = edtLoginAccount.getText().toString().trim();
        String verificationCode = edtVerificationCode.getText().toString().trim();

        if (StringUtils.isEmpty(phone)) {
            toast(getString(R.string.iphone_number_not_null));
            return;
        }
        if (StringUtils.isEmpty(verificationCode)) {
            toast(getString(R.string.verification_code_not_null));
            return;
        }
        // TODO: 2019/5/28 0028 请求登录的接口

    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.color_FFFFFF);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, true);
    }
}
