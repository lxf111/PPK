package com.uni.ppk.ui.mine.activity;

import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CashierInputFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 14:04
 * 提现
 */
public class WithdrawActivity extends BaseActivity {
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
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.iv_alipay)
    ImageView ivAlipay;
    @BindView(R.id.edt_alipay)
    EditText edtAlipay;
    @BindView(R.id.iv_wx)
    ImageView ivWx;
    @BindView(R.id.edt_wx)
    EditText edtWx;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private int mSelectType = 2;//提现类型 1：微信 2：支付宝】

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initData() {
        initTitle("提现");
        InputFilter[] filters = {new CashierInputFilter()};
        edtMoney.setFilters(filters);
        getBalance();
    }

    //获取余额
    private void getBalance() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.withdrawBalance(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                tvBalance.setText("¥" + JSONUtils.getNoteJson(response, "user_money"));
            }

            @Override
            public void onError(String msg, int code) {
            }

            @Override
            public void onFail(Call call, IOException e) {
            }
        });
    }

    @OnClick({R.id.tv_all, R.id.iv_alipay, R.id.iv_wx, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                edtMoney.setText("" + ArithUtils.saveSecond(tvBalance.getText().toString().trim().replace("¥", "")));
                break;
            case R.id.iv_alipay:
                ivAlipay.setImageResource(R.mipmap.ic_agree_selected);
                ivWx.setImageResource(R.mipmap.ic_agree_no_select);
                break;
            case R.id.iv_wx:
                ivAlipay.setImageResource(R.mipmap.ic_agree_no_select);
                ivWx.setImageResource(R.mipmap.ic_agree_selected);
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void submit() {
        String money = edtMoney.getText().toString().trim();
        String account = "";
        if (StringUtils.isEmpty(money)) {
            ToastUtils.show(mContext, "请输入金额");
            return;
        }
        if (mSelectType == 2) {
            account = edtAlipay.getText().toString().trim();
            if (StringUtils.isEmpty(account)) {
                ToastUtils.show(mContext, "请输入提现账户");
                return;
            }
            if (!InputCheckUtil.checkPhoneNum(account) && !InputCheckUtil.isEmail(account)) {
                ToastUtils.show(mContext, "请输入正确的支付宝账号");
                return;
            }
        } else {
            account = edtWx.getText().toString().trim();
            if (StringUtils.isEmpty(account)) {
                ToastUtils.show(mContext, "请输入提现账户");
                return;
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("money", "" + money);
        params.put("type", "" + mSelectType);
        params.put("account_id", "" + account);
        params.put("true_name", "" + MyApplication.mPreferenceProvider.getUserName());
        HttpUtils.withdraw(mContext, params, new MyCallBack() {
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

}
