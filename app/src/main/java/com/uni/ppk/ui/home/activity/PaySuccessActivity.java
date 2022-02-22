package com.uni.ppk.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.bean.OrderDetailBean;
import com.uni.ppk.ui.mine.bean.PersonDataBean;
import com.uni.ppk.utils.LoginCheckUtils;

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
 * Time: 10:25
 * 支付成功
 */
public class PaySuccessActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    private boolean isJumpHome;//true跳转首页 false选择律师

    private int mType = 0;//3仅提交成功  1选择律师
    private String mPrice = "";
    private String mSonId = "";
    private String mOrderNum = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initData() {
        mType = getIntent().getIntExtra("type", 0);
        mPrice = getIntent().getStringExtra("price");
        mSonId = getIntent().getStringExtra("sonId");
        mOrderNum = getIntent().getStringExtra("orderNum");
        initTitle("付款结果");
        isJumpHome = getIntent().getBooleanExtra("jump", true);
        if (isJumpHome) {
            tvSubmit.setText("返回首页");
        } else {
            tvSubmit.setText("选择律师");
        }
        if (mType == 3) {
            tvSubmit.setVisibility(View.GONE);
            initTitle("");
            tvStatus.setText("提交成功");
        } else if (mType == 4) {
            //购买视频
            tvSubmit.setText("返回");
        } else if (mType == 1) {
            tvSubmit.setText("选择律师");
        } else if (mType == 5) {
            refreshVipLevel();
        }
    }

    //刷新会员等级
    private void refreshVipLevel() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        HttpUtils.mineData(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                PersonDataBean mDataBean = JSONUtils.jsonString2Bean(response, PersonDataBean.class);
                if (mDataBean != null) {
                    LoginCheckUtils.updateUserInfo(mDataBean);
                    LoginCheckUtils.updateLevel(mDataBean.getUser_vip());
                }
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        if ("选择律师".equals(tvSubmit.getText().toString().trim())) {
            getOrderDetail();
        } else {
            onBackPressed();
        }
    }

    private void getOrderDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("order_sn", "" + mOrderNum);
        HttpUtils.orderDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                OrderDetailBean mDetailBean = JSONUtils.jsonString2Bean(response, OrderDetailBean.class);
                if (mDetailBean != null) {
                    Bundle bundle = new Bundle();
                    if (mDetailBean.getTop_id() == 3) {
                        bundle.putString("price", "" + mDetailBean.getOrder_money());
                    }
                    bundle.putString("classifyId", "" + mDetailBean.getSon_id());
                    bundle.putString("orderNum", "" + mOrderNum);
                    if (!"市辖区".equals(mDetailBean.getCity())) {
                        bundle.putString("city", "" + mDetailBean.getCity());
                    } else {
                        bundle.putString("city", "" + mDetailBean.getProvince());
                    }
                    MyApplication.openActivity(mContext, SearchActivity.class, bundle);
                } else {
                    ToastUtils.show(mContext, "订单异常，请稍后再试...");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mType == 4) {
            RxBus.getInstance().post("purchaseVideo");
        }
    }
}
