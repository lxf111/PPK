package com.uni.ppk.ui.home.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.CommonSelectPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.ui.home.bean.LawsuitJumpBean;
import com.uni.ppk.ui.home.bean.OrderEarnestBean;
import com.uni.ppk.utils.OrderAgreeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 9:17
 * 费用估算
 */
public class CostEstimatingActivity extends BaseActivity {
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
    @BindView(R.id.tv_should_price)
    TextView tvShouldPrice;
    @BindView(R.id.tv_platform_price)
    TextView tvPlatformPrice;
    @BindView(R.id.tv_vip_price)
    TextView tvVipPrice;
    @BindView(R.id.tv_subsidy_price)
    TextView tvSubsidyPrice;
    @BindView(R.id.tv_deposit_price)
    TextView tvDepositPrice;
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;

    private CommonSelectPopup mSelectPopup;//选择弹窗
    private List<CommonSelectBean> mSelectBeans = new ArrayList<>();

    private String mResponse = "";
    private LawsuitJumpBean mJumpBean;
    private OrderEarnestBean mEarnestBean = null;

    private String mLocalPrice = "";//最高价
    private String mSubsidyPrice = "";//补贴价

    private String mEarnestPrice = "";//档位
    private String mSignPrice = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cost_estimating;
    }

    @Override
    protected void initData() {
        mResponse = getIntent().getStringExtra("response");
        mLocalPrice = getIntent().getStringExtra("localPrice");
        mSignPrice = getIntent().getStringExtra("signPrice");
        mSubsidyPrice = getIntent().getStringExtra("subsidyPrice");
        mJumpBean = (LawsuitJumpBean) getIntent().getSerializableExtra("bean");
        initTitle("费用估算");
        tvPay.setText("支付定金");
        mSelectPopup = new CommonSelectPopup(mContext);
        tvSubsidyPrice.setText(mSubsidyPrice + "%");
        if (!StringUtils.isEmpty(mResponse)) {
            mEarnestBean = JSONUtils.jsonString2Bean(mResponse, OrderEarnestBean.class);
            if (mEarnestBean != null && mEarnestBean.getEarnest_list() != null && mEarnestBean.getEarnest_list().size() > 0) {
                for (int i = 0; i < mEarnestBean.getEarnest_list().size(); i++) {
                    CommonSelectBean bean = new CommonSelectBean();
                    bean.setName(mEarnestBean.getEarnest_list().get(i).getEarnest());
                    mSelectBeans.add(bean);
                }
            }
            tvPlatformPrice.setText("¥" + mEarnestBean.getSum_price().getPrice());
//            + "(定金¥"
//                    + mEarnestBean.getEarnest().getPrice() + "元)"
            tvVipPrice.setText("¥" + mEarnestBean.getSum_price().getMember_price());
            tvDepositPrice.setText("¥" + OrderAgreeUtils.showPrice(mContext, mEarnestBean.getEarnest().getMember_price(), mEarnestBean.getEarnest().getPrice()));
        }
        OrderAgreeUtils.showVipTxt(tvOpenVip);
        if (!StringUtils.isEmpty(mSignPrice)) {
            getLocalPrice();
        } else {
            tvShouldPrice.setText("¥" + mLocalPrice);
        }
    }

    private void getLocalPrice() {
        Map<String, Object> params = new HashMap<>();
        params.put("province", mJumpBean.getOrderInfo().get("province"));
        params.put("money", mSignPrice);
        HttpUtils.localPrice(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                tvShouldPrice.setText("¥" + response);
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

    @OnClick({R.id.tv_deposit_price, R.id.tv_open_vip, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //预付定金
            case R.id.tv_deposit_price:
                mSelectPopup.setmSelectBeans(mSelectBeans);
                mSelectPopup.setTitle("预付定金");
                mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                    @Override
                    public void onItemClick(View view, int position, CommonSelectBean model) {
//                        tvPlatformPrice.setText("¥" + mEarnestBean.getEarnest_list().get(position).getPrice());
//                        + "(定金¥"
//                                + mEarnestBean.getEarnest_list().get(position).getPrice() + "元)"
//                        tvVipPrice.setText("¥" + mEarnestBean.getEarnest_list().get(position).getMember_price());
                        tvDepositPrice.setText("¥" + mEarnestBean.getEarnest_list().get(position).getPrice());
                        mSelectPopup.dismiss();
                        mEarnestPrice = model.getName();
                    }

                    @Override
                    public void onItemLongClick(View view, int position, CommonSelectBean model) {

                    }
                });
                mSelectPopup.showAtLocation(tvOpenVip, Gravity.BOTTOM, 0, 0);
                break;
            //开通vip
            case R.id.tv_open_vip:
                OrderAgreeUtils.jumpVip(mContext);
                break;
            //支付
            case R.id.tv_pay:
                submit();
                break;
        }
    }

    @SuppressLint("NewApi")
    private void submit() {
        if (!StringUtils.isEmpty(mEarnestPrice)) {
            for (int i = 0; i < mJumpBean.getBodyBeans().size(); i++) {
                if ("earnest".equals(mJumpBean.getBodyBeans().get(i).getKey())) {
                    mJumpBean.getBodyBeans().get(i).setValue("" + mEarnestPrice);
                }
            }
        }
        mJumpBean.getOrderInfo().replace("body", JSONUtils.toJsonString(mJumpBean.getBodyBeans()));
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "1");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(mJumpBean.getOrderInfo()));
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                String status = JSONUtils.getNoteJson(response, "pay_status");
                if ("1".equals(status)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 0);
                    MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
                    finish();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("jumpType", 1);
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
                AppManager.getInstance().finishActivity(OrderPromptActivity.class);
                AppManager.getInstance().finishActivity(OrderLawsuitActivity.class);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
                finish();
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
}
