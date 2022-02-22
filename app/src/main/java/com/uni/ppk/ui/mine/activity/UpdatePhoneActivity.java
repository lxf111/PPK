package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.utils.TimerUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更改绑定手机号
 */
public class UpdatePhoneActivity extends BaseActivity {
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_verification_code)
    EditText edtVerificationCode;

    private String mPhone = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void initData() {
        mPhone = getIntent().getStringExtra("phone");
        initTitle("" + getString(R.string.binding_exchange_phone));
        edtPhone.setText("" + mPhone);
        edtPhone.setFocusable(false);
    }

    @OnClick({R.id.tv_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.tv_code:
                TimerUtil timerUtil = new TimerUtil(tvCode);
                timerUtil.timers();
                sendMessage();
                break;
        }
    }

    /**
     * 确定更换手机号
     */
    private void exchangePhone() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            toast("请输入验证码");
            return;
        }
        Intent intent = new Intent(UpdatePhoneActivity.this, BindPhoneActivity.class);
        startActivity(intent);
//        StyledDialogUtils.getInstance().loading(this);
//        BaseOkHttpClient.newBuilder().url(NetUrlUtils.CHECK_PHONE_CODE)
//                .addParam("captcha", "" + code)
//                .addParam("mobile", "" + phone)
//                .addParam("event", "" + 4)
//                .post()
//                .json()
//                .build().enqueue(this, new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Intent intent = new Intent(UpdatePhoneActivity.this, BindPhoneActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast(msg);
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast("服务器异常");
//            }
//        });
    }

    /**
     * 发送验证码
     */
    private void sendMessage() {
        String phone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }
//        StyledDialogUtils.getInstance().loading(this);
//        BaseOkHttpClient.newBuilder().url(NetUrlUtils.SEND_MESSAGE)
//                .addParam("phone", "" + phone)
//                .addParam("type", "" + 4)
//                .post()
//                .json()
//                .build().enqueue(this, new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast(msg);
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast(msg);
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }
}
