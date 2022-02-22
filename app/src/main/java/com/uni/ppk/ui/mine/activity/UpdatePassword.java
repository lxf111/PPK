package com.uni.ppk.ui.mine.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.utils.TimerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 修改密码
 */
public class UpdatePassword extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_confirm_pwd)
    EditText edtConfirmPwd;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initData() {
        initTitle("修改密码");
        edtPhone.setText("" + MyApplication.mPreferenceProvider.getMobile());
    }

    @OnClick({R.id.tv_code, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.tv_code:
                sendMessage();
                break;
            //确定修改
            case R.id.tv_submit:
                confirm();
                break;
        }
    }

    //提交修改密码
    private void confirm() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();
        String confirmPwd = edtConfirmPwd.getText().toString().trim();
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
        if (StringUtils.isEmpty(confirmPwd)) {
            ToastUtils.show(mContext, "请再次输入密码");
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            ToastUtils.show(mContext, "两次密码输入不一致");
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

}
