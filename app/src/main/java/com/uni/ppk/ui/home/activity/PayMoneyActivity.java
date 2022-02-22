package com.uni.ppk.ui.home.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.home.bean.WxPayBean;
import com.uni.ppk.ui.mine.activity.OrderDetailActivity;
import com.uni.ppk.ui.mine.activity.ServiceDetailActivity;
import com.uni.ppk.utils.PayListenerUtils;
import com.uni.ppk.utils.PayResultListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 10:03
 * 支付选择
 */
public class PayMoneyActivity extends BaseActivity {
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
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_wxpay)
    TextView tvWxpay;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String mOrderNum = "";
    private String mOrderMoney = "";
    private int mJumpType = 0;//0下单 5开通会员 4购买视频 1选择律师 6发布咨询订单

    private int mPayTye = 0;//0支付宝 1微信
    private String mSonId = "";//选择律师使用

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_money;
    }

    @Override
    protected void initData() {
        mOrderNum = getIntent().getStringExtra("orderNum");
        mOrderMoney = getIntent().getStringExtra("orderMoney");
        mSonId = getIntent().getStringExtra("sonId");
        mJumpType = getIntent().getIntExtra("jumpType", 0);
        initTitle("支付");
        if (mJumpType == 5) {
            tvTitle.setText("开通会员");
        } else if (mJumpType == 4) {
            tvTitle.setText("购买课程");
        } else if (mJumpType == 6) {
            tvTitle.setText("发布咨询");
        }
        tvMoney.setText("¥" + mOrderMoney);
        //微信支付的回调监听
        PayListenerUtils.getInstance(this).addListener(payResultListener);
    }

    @OnClick({R.id.tv_alipay, R.id.tv_wxpay, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //支付宝
            case R.id.tv_alipay:
                setWxSelect(R.mipmap.ic_pay_select_no);
                setAlipaySelect(R.mipmap.ic_pay_select);
                mPayTye = 0;
                break;
            //微信
            case R.id.tv_wxpay:
                setWxSelect(R.mipmap.ic_pay_select);
                setAlipaySelect(R.mipmap.ic_pay_select_no);
                mPayTye = 1;
                break;
            //确认支付
            case R.id.tv_pay:
                if (mPayTye == 0) {
                    //支付宝
                    getAlipayData();
                } else if (mPayTye == 1) {
                    //微信
                    getWxData();
                }
                break;
        }
    }

    //获取支付宝参数
    private void getAlipayData() {
        Map<String, Object> params = new HashMap<>();
        params.put("order_sn", "" + mOrderNum);
        HttpUtils.alipay(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "订单异常，请重试...");
                    return;
                }
                alipay(response);
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

    //获取微信参数
    private void getWxData() {
        Map<String, Object> params = new HashMap<>();
        params.put("order_sn", "" + mOrderNum);
        HttpUtils.wxPay(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "订单异常，请重试...");
                    return;
                }
                WxPayBean payBean = JSONUtils.jsonString2Bean(response, WxPayBean.class);
                if (payBean != null) {
                    wxPay(payBean);
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

    //设置微信图标
    private void setWxSelect(int mipmap) {
        Drawable drawable = getResources().getDrawable(mipmap);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        Drawable drawable1 = getResources().getDrawable(R.mipmap.ic_pay_wx);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        tvWxpay.setCompoundDrawables(drawable1, null, drawable, null);
    }

    //设置支付宝图标
    private void setAlipaySelect(int mipmap) {
        Drawable drawable = getResources().getDrawable(mipmap);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        Drawable drawable1 = getResources().getDrawable(R.mipmap.ic_pay_alipay);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        tvAlipay.setCompoundDrawables(drawable1, null, drawable, null);
    }

    /**
     * 支付宝支付
     */
    private void alipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                final Map<String, String> result = alipay.payV2(orderInfo, true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String resultStatus = result.get("resultStatus");
                        if (resultStatus.equals("9000")) {
                            toast("支付成功！");
                            Bundle bundle = new Bundle();
                            if (mJumpType == 5) {
                                AppManager.getInstance().finishActivity(VipListActivity.class);
                            } else if (mJumpType == 0 || mJumpType == 1) {
                                AppManager.getInstance().finishActivity(OrderDetailActivity.class);
                                bundle.putString("sonId", mSonId);
                                bundle.putString("price", mOrderMoney);
                                bundle.putString("orderNum", "" + mOrderNum);
                            } else if (mJumpType == 6) {
                                AppManager.getInstance().finishActivity(ServiceDetailActivity.class);
                            }
                            bundle.putInt("type", mJumpType);
                            MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
                            finish();
                        } else {
                            toast("支付取消！");
                        }
                    }
                });
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    IWXAPI api;

    /**
     * 微信支付
     */
    private void wxPay(WxPayBean bean) {
        LogUtils.e("TAG", "WxPayBean=" + JSONUtils.toJsonString(bean));
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp("" + Constants.WX_APP_KEY);//微信的appkey
        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = bean.getNoncestr();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        api.sendReq(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PayListenerUtils.getInstance(this).removeListener(payResultListener);
    }

    private PayResultListener payResultListener = new PayResultListener() {
        @Override
        public void onPaySuccess() {
            //支付成功
            toast("支付成功");
            Bundle bundle = new Bundle();
            if (mJumpType == 5) {
                AppManager.getInstance().finishActivity(VipListActivity.class);
            } else if (mJumpType == 0 || mJumpType == 1) {
                AppManager.getInstance().finishActivity(OrderDetailActivity.class);
                bundle.putString("sonId", mSonId);
                bundle.putString("price", mOrderMoney);
            } else if (mJumpType == 6) {
                AppManager.getInstance().finishActivity(ServiceDetailActivity.class);
            }
            bundle.putInt("type", mJumpType);
            MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
            finish();
        }

        @Override
        public void onPayError() {
            //支付失败
            toast("支付失败");
        }

        @Override
        public void onPayCancel() {
            //取消支付
            toast("取消支付");
        }
    };
}
