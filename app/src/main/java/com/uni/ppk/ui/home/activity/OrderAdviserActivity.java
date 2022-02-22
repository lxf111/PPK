package com.uni.ppk.ui.home.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.pop.AddressSelectPopup;
import com.uni.ppk.ui.home.adapter.AdviseAdapter;
import com.uni.ppk.ui.home.bean.AdviserBean;
import com.uni.ppk.ui.home.bean.OrderBodyBean;
import com.uni.ppk.ui.home.bean.OrderPriceBean;
import com.uni.ppk.utils.OrderAgreeUtils;
import com.uni.ppk.widget.CustomRecyclerView;

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
 * Date: 2020/9/10
 * Time: 18:01
 * 法律顾问
 */
public class OrderAdviserActivity extends BaseActivity {
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
    @BindView(R.id.tv_title_address)
    TextView tvTitleAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rlv_list)
    CustomRecyclerView rlvList;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.iv_agree)
    ImageView ivAgree;
    @BindView(R.id.tv_agree_rule)
    TextView tvAgreeRule;
    @BindView(R.id.tv_normal_price)
    TextView tvNormalPrice;
    @BindView(R.id.tv_vip_price)
    TextView tvVipPrice;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    private String mTitle = "";

    private AdviseAdapter mAdapter;

    private boolean isAgree = false;

    private AddressSelectPopup mAddressPopup;//地址选择

    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";
    private String mId = "";//一级分类id
    private String mClassifyId = "";//二级分类id

    //协议 标问号的标题协议内容
    private String mAgreeAddress = "";
    private OrderPriceBean mPriceBean = null;//获取套餐价格列表

    private String mProvinceId = "";
    private String mCityId = "";
    private String mAreaId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_adviser;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra("title");
        mId = getIntent().getStringExtra("id");
        mClassifyId = getIntent().getStringExtra("classifyId");
        initTitle("" + mTitle);
        OrderAgreeUtils.showVipTxt(tvOpenVip);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_order_custom);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(drawable, null, null, null);

        rlvList.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new AdviseAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        rlvList.setFocusable(false);

        getOrderPrice();
    }

    @OnClick({R.id.tv_open_vip, R.id.right_title, R.id.tv_pay, R.id.tv_title_address, R.id.tv_address, R.id.iv_agree, R.id.tv_agree_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //客服
            case R.id.right_title:
                OrderAgreeUtils.jumpChat(mContext);
                break;
            case R.id.tv_open_vip:
                OrderAgreeUtils.jumpVip(mContext);
                break;
            case R.id.tv_pay:
                if (!isAgree) {
                    ToastUtils.show(mContext, "请同意相关条款");
                    return;
                }
                submit();
                break;
            case R.id.tv_title_address:
                OrderAgreeUtils.jumpAgree(mContext, tvTitleAddress.getText().toString().trim(), mAgreeAddress, true);
                break;
            case R.id.tv_address:
                if (mPriceBean == null || mPriceBean.getPrices() == null) {
                    getOrderPrice();
                    ToastUtils.show(mContext, getString(R.string.service_error));
                    return;
                }
                mAddressPopup = new AddressSelectPopup(mContext);
                mAddressPopup.setmAddressCallback(new AddressSelectPopup.OnAddressCallback() {
                    @Override
                    public void callback(String province, String city, String area) {
                        tvAddress.setText("" + province + city + area);
                        mProvince = province;
                        mCity = city;
                        mArea = area;
                        getAreaMeal(province, city, area);
                    }
                });
                mAddressPopup.setmAddressId(new AddressSelectPopup.OnAddressCallback() {
                    @Override
                    public void callback(String province, String city, String area) {
//                        getAreaMeal(province, city, area);
                    }
                });
                mAddressPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_agree:
                if (isAgree) {
                    isAgree = false;
                    ivAgree.setImageResource(R.mipmap.ic_order_agree);
                } else {
                    isAgree = true;
                    ivAgree.setImageResource(R.mipmap.ic_agree_select);
                }
                break;
            case R.id.tv_agree_rule:
                OrderAgreeUtils.getRule(mContext);
                break;
        }
    }

    //获取套餐
    private void getAreaMeal(String province, String city, String area) {
        Map<String, Object> params = new HashMap<>();
        params.put("types_id", "" + mId);
        params.put("province", "" + province);
        params.put("city", "" + city);
        HttpUtils.getAreaMeal(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<AdviserBean> beanList = JSONUtils.jsonString2Beans(response, AdviserBean.class);
                if (beanList != null && beanList.size() > 0) {
                    mAdapter.refreshList(beanList);
                } else {
                    toast("暂无配置套餐");
                }
            }

            @Override
            public void onError(String msg, int code) {
                toast("暂无配置套餐");
            }

            @Override
            public void onFail(Call call, IOException e) {
                toast(getString(R.string.service_error));
            }
        });
    }

    private void getOrderPrice() {
        Map<String, Object> params = new HashMap<>();
        params.put("son_id", "" + mClassifyId);
        params.put("typeid", "" + mId);
        HttpUtils.orderPrice(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                OrderPriceBean bean = JSONUtils.jsonString2Bean(response, OrderPriceBean.class);
                if (bean != null) {
                    mPriceBean = bean;
                    if (bean.getPrices() != null && bean.getPrices().size() > 0) {
                        for (int i = 0; i < bean.getPrices().size(); i++) {
                            //获取会员价跟平台价
                            if (Constants.ORDER_DEFAULT_PRICE.equals(bean.getPrices().get(i).getType())) {
                                tvNormalPrice.setText("¥" + bean.getPrices().get(i).getPrice());
                                tvVipPrice.setText("¥" + bean.getPrices().get(i).getMember_price());
                            }
                        }
                    }
                    String ensure = "";//服务保障
                    if (bean.getGuarantee() != null && bean.getGuarantee().size() > 0) {
                        for (int i = 0; i < bean.getGuarantee().size(); i++) {
                            if (StringUtils.isEmpty(ensure)) {
                                ensure = bean.getGuarantee().get(i).getName();
                            } else {
                                ensure = ensure + "\n" + bean.getGuarantee().get(i).getName();
                            }
                        }
                    }
                    tvEnsure.setText("" + ensure);
                    if (bean.getTips() != null) {
                        mAgreeAddress = "" + bean.getTips().getProvince();
                    }
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

    private void submit() {
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (StringUtils.isEmpty(mProvince)) {
            ToastUtils.show(mContext, "请选择地点");
            return;
        }
        List<OrderBodyBean> bodyBeans = new ArrayList<>();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getItem(i).isSelect()) {
                bodyBeans.add(new OrderBodyBean(Constants.ORDER_TAOCANID, "套餐id",
                        mAdapter.getItem(i).getArealist_id()));
                bodyBeans.add(new OrderBodyBean(Constants.ORDER_TAOCAN, "套餐内容",
                        mAdapter.getItem(i).getTitle()));
            }
        }
        if (bodyBeans.size() == 0) {
            ToastUtils.show(mContext, "请选择套餐");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
//        if (StringUtils.isEmpty(email)) {
//            ToastUtils.show(mContext, "请输入邮箱");
//            return;
//        }
//        if (!InputCheckUtil.isEmail(email)) {
//            ToastUtils.show(mContext, "请输入正确的邮箱");
//            return;
//        }
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("province", "" + mProvince);
        orderInfo.put("title", "" + mTitle);
        orderInfo.put("city", "" + mCity);
        orderInfo.put("district", "" + mArea);
        orderInfo.put("phone", "" + phone);
        orderInfo.put("email", "" + email);
        orderInfo.put("top_id", "" + mId);
        orderInfo.put("content", "");
        orderInfo.put("son_id", "" + mClassifyId);
        orderInfo.put("body", JSONUtils.toJsonString(bodyBeans));
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "1");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(orderInfo));
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
//                    finish();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
//                AppManager.getInstance().finishActivity(OrderPromptActivity.class);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
//                finish();
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
