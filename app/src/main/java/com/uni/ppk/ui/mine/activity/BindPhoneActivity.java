package com.uni.ppk.ui.mine.activity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.AppManager;
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
 * 绑定新手机号
 */
public class BindPhoneActivity extends BaseActivity {

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
    @BindView(R.id.tv_primary)
    TextView tvPrimary;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void initData() {
        initTitle("绑定手机");
        tvPrimary.setText("你目前的手机号是+" + MyApplication.mPreferenceProvider.getMobile() + "，想要把他更新为？");
        String title = tvPrimary.getText().toString();
        SpannableString spannableString = new SpannableString(title);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.theme));
        spannableString.setSpan(foregroundColorSpan, title.indexOf("+"), title.indexOf("，")
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrimary.setText(spannableString);
        tvPrimary.setHighlightColor(mContext.getResources().getColor(R.color.transparent)); //设置点击后的颜色为透明
        tvPrimary.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.tv_code, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.tv_code:
                sendMessage();
                break;
            //确定更换手机号
            case R.id.tv_submit:
                confirm();
                break;
        }
    }

    //确定更换手机号
    private void confirm() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
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
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "" + phone);
        params.put("code", "" + code);
        params.put("step", "" + 2);
        params.put("type", "5");
        HttpUtils.updatePhone(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                AppManager.getInstance().finishActivity(CheckPhoneActivity.class);
                MyApplication.mPreferenceProvider.setMobile("" + phone);
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
        params.put("type", "5");
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
