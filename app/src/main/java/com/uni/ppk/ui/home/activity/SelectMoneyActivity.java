package com.uni.ppk.ui.home.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.SelectMoneyAdapter;
import com.uni.ppk.ui.home.bean.OrderPriceBean;
import com.uni.ppk.ui.home.bean.SelectMoneyBean;
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
 * Time: 11:24
 * 咨询律师选择价格
 */
public class SelectMoneyActivity extends BaseActivity {
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
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.rlv_money)
    RecyclerView rlvMoney;

    private SelectMoneyAdapter mAdapter;

    private String mOrderInfo = "";
    private String mSonId = "";

    private OrderPriceBean mPriceBean;//价格

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_money;
    }

    @Override
    protected void initData() {
        initTitle("费用预估");

        mOrderInfo = getIntent().getStringExtra("orderInfo");
        mSonId = getIntent().getStringExtra("sonId");
        mPriceBean = (OrderPriceBean) getIntent().getSerializableExtra("bean");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_order_custom);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(drawable, null, null, null);

        rlvMoney.setLayoutManager(new GridLayoutManager(mContext, 3));
        mAdapter = new SelectMoneyAdapter(mContext);
        rlvMoney.setAdapter(mAdapter);

        OrderAgreeUtils.showVipTxt(tvOpenVip);
        OrderAgreeUtils.showVipTxt(tvVip);


        List<SelectMoneyBean> moneyBeans = new ArrayList<>();
        if (mPriceBean != null) {
            if (mPriceBean.getPrices() != null && mPriceBean.getPrices().size() > 0) {
                for (int i = 0; i < mPriceBean.getPrices().size(); i++) {
                    if ("service".equals(mPriceBean.getPrices().get(i).getType())) {
                        if (mPriceBean.getPrices().get(i).getRule() != null && mPriceBean.getPrices().get(i).getRule().size() > 0) {
                            for (int j = 0; j < mPriceBean.getPrices().get(i).getRule().size(); j++) {
                                SelectMoneyBean bean = new SelectMoneyBean();
                                bean.setPrice("" + mPriceBean.getPrices().get(i).getRule().get(j).getPrice());
                                bean.setCount("" + mPriceBean.getPrices().get(i).getRule().get(j).getCount());
                                moneyBeans.add(bean);
                            }
                        }
                    }
                }
            }
        }
        mAdapter.refreshList(moneyBeans);
    }

    @OnClick({R.id.tv_open_vip, R.id.tv_pay, R.id.tv_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //vip
            case R.id.tv_open_vip:
            case R.id.tv_vip:
                MyApplication.openActivity(mContext, VipListActivity.class);
                break;
            //支付
            case R.id.tv_pay:
                submit();
                break;
        }
    }

    private void submit() {
        String money = "";
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getItem(i).isSelect()) {
                money = "" + mAdapter.getItem(i).getPrice();
            }
        }
        if (StringUtils.isEmpty(money)) {
            ToastUtils.show(mContext, "请选择价格");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "1");
        params.put("order_money", "" + money);
        params.put("remark", "");
        params.put("order_info", mOrderInfo);
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                String status = JSONUtils.getNoteJson(response, "pay_status");
                if ("1".equals(status)) {
                    Bundle bundle = new Bundle();
                    AppManager.getInstance().finishActivity(OrderEnquireActivity.class);
                    bundle.putInt("type", 0);
                    MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
                    finish();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("jumpType", 1);
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("sonId", "" + mSonId);
                bundle.putString("orderMoney", "" + orderMoney);
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
