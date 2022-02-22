package com.uni.ppk.ui.mine.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值界面
 */
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.iv_select_alipay)
    ImageView ivSelectAlipay;
    @BindView(R.id.rl_alipay)
    RelativeLayout rlAlipay;
    @BindView(R.id.iv_select_wxpay)
    ImageView ivSelectWxpay;
    @BindView(R.id.rl_wxpay)
    RelativeLayout rlWxpay;
    @BindView(R.id.iv_select_pp)
    ImageView ivSelectPp;
    @BindView(R.id.rl_pp)
    RelativeLayout rlPp;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {
        initTitle("" + getString(R.string.wallet_recharge));
        //微信支付的回调监听
//        PayListenerUtils.getInstance(this).addListener(new PayResultListener() {
//            @Override
//            public void onPaySuccess() {
//                //支付成功
//            }
//
//            @Override
//            public void onPayError() {
//                //支付失败
//            }
//
//            @Override
//            public void onPayCancel() {
//                //取消支付
//            }
//        });
    }

    @OnClick({R.id.rl_alipay, R.id.rl_wxpay, R.id.rl_pp, R.id.btn_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择支付宝
            case R.id.rl_alipay:
                ivSelectAlipay.setImageResource(R.mipmap.icon_select_theme);
                ivSelectWxpay.setImageResource(R.mipmap.icon_select_no);
                ivSelectPp.setImageResource(R.mipmap.icon_select_no);
                break;
            //选择微信
            case R.id.rl_wxpay:
                ivSelectWxpay.setImageResource(R.mipmap.icon_select_theme);
                ivSelectAlipay.setImageResource(R.mipmap.icon_select_no);
                ivSelectPp.setImageResource(R.mipmap.icon_select_no);
                break;
            //选择PP
            case R.id.rl_pp:
                ivSelectPp.setImageResource(R.mipmap.icon_select_theme);
                ivSelectWxpay.setImageResource(R.mipmap.icon_select_no);
                ivSelectAlipay.setImageResource(R.mipmap.icon_select_no);
                break;
            //确定充值
            case R.id.btn_recharge:
                String money = edtMoney.getText().toString().trim();

                if (TextUtils.isEmpty(money)) {
                    toast(getString(R.string.recharge_input_money));
                    return;
                }

                //调接口去充值

                break;
        }
    }

//    IWXAPI api;
//
//    /**
//     * 微信支付
//     */
//    private void wxpay(PayBean bean) {
//        api = WXAPIFactory.createWXAPI(RechargeActivity.this, null);
//        api.registerApp("wxf92112054c7eb268");//微信的appkey
//        PayReq request = new PayReq();
//        request.appId = bean.getAppId();
//        request.partnerId = bean.getPartnerId();
//        request.prepayId = bean.getPrepayId();
//        request.packageValue = bean.getPackageValue();
//        request.nonceStr = bean.getNonceStr();
//        request.timeStamp = bean.getTimeStamp();
//        request.sign = bean.getSign();
//        api.sendReq(request);
//    }
//
//    /**
//     * 支付宝支付
//     */
//    private void alipay(final String orderInfo) {
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(RechargeActivity.this);
//                Map<String, String> result = alipay.payV2(orderInfo, true);
//
//                Message msg = new Message();
//                msg.what = 1;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    /**
//     * 支付宝支付的回调
//     */
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                Map<String, String> result = (Map<String, String>) msg.obj;
//
//                String resultStatus = result.get("resultStatus");
//
//                if (resultStatus.equals("4000")) {
//                    //支付失败
//
//                } else if (resultStatus.equals("9000")) {
//                    //支付成功
//
//                } else {
//
//                }
//            }
//        }
//    };

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }


}
