package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.home.activity.PayMoneyActivity;
import com.uni.ppk.ui.home.activity.SearchActivity;
import com.uni.ppk.ui.home.adapter.LabelAdapter;
import com.uni.ppk.ui.home.bean.LabelBean;
import com.uni.ppk.ui.message.activity.ChatActivity;
import com.uni.ppk.ui.message.bean.MessageUserBean;
import com.uni.ppk.ui.mine.adapter.OrderBodyAdapter;
import com.uni.ppk.ui.mine.adapter.OrderPhotoAdapter;
import com.uni.ppk.ui.mine.bean.OrderDetailBean;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.utils.EaseMobHelper;
import com.uni.ppk.utils.SaveUserUtils;
import com.uni.ppk.widget.CustomRatingBar;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

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
 * Date: 2019/6/14
 * Time: 11:03
 */
public class OrderDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_property)
    TextView tvProperty;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rlv_photo)
    CustomRecyclerView rlvPhoto;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_lawyer_name)
    TextView tvLawyerName;
    @BindView(R.id.rlyt_lawyer_name)
    RelativeLayout rlytLawyerName;
    @BindView(R.id.tv_evaluate_time)
    TextView tvEvaluateTime;
    @BindView(R.id.tv_evaluate_title)
    TextView tvEvaluateTitle;
    @BindView(R.id.ratingbar)
    CustomRatingBar ratingbar;
    @BindView(R.id.rlv_type)
    CustomRecyclerView rlvType;
    @BindView(R.id.tv_evaluate_content)
    TextView tvEvaluateContent;
    @BindView(R.id.rlyt_evaluate)
    RelativeLayout rlytEvaluate;
    @BindView(R.id.rlv_body)
    CustomRecyclerView rlvBody;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;

    private OrderPhotoAdapter mPhotoAdapter;

    private String mOrderId = "";
    private OrderDetailBean mDetailBean;

    private OrderBodyAdapter mBodyAdapter;

    private LabelAdapter mLabelAdapter;//评价标签

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initData() {
        mOrderId = getIntent().getStringExtra("id");
        initTitle("订单详情");
        rightTitle.setText("查看合约");
        ratingbar.setClickable(false);

        rlvPhoto.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mPhotoAdapter = new OrderPhotoAdapter(mContext);
        rlvPhoto.setAdapter(mPhotoAdapter);

        rlvBody.setLayoutManager(new LinearLayoutManager(mContext));
        mBodyAdapter = new OrderBodyAdapter(mContext);
        rlvBody.setAdapter(mBodyAdapter);

        getDetail();
    }

    /**
     * 获取详情
     */
    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("order_sn", "" + mOrderId);
        HttpUtils.orderDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mDetailBean = JSONUtils.jsonString2Bean(response, OrderDetailBean.class);
                if (mDetailBean != null) {
                    tvCreateTime.setText("下单时间：" + mDetailBean.getCreate_time());
                    tvContent.setText("" + mDetailBean.getContent());
                    tvType.setText("业务类型：" + mDetailBean.getTitle());
                    mPhotoAdapter.refreshList(mDetailBean.getAtlas());
                    if (!StringUtils.isEmpty(mDetailBean.getEmail())) {
                        tvEmail.setText("邮箱：" + mDetailBean.getEmail());
                        tvEmail.setVisibility(View.VISIBLE);
                    }
                    if (!StringUtils.isEmpty(mDetailBean.getPhone())) {
                        tvPhone.setText("手机号码：" + mDetailBean.getPhone());
                        tvPhone.setVisibility(View.VISIBLE);
                    }
                    if (!StringUtils.isEmpty(mDetailBean.getOrder_money())) {
                        tvOrderMoney.setText("订单金额：¥" + ArithUtils.saveSecond(mDetailBean.getOrder_money()));
                        tvOrderMoney.setVisibility(View.VISIBLE);
                    }
                    if (!StringUtils.isEmpty(mDetailBean.getProvince())) {
                        tvAddress.setText("案件委托地址：" + mDetailBean.getProvince()
                                + mDetailBean.getCity()
                                + mDetailBean.getDistrict());
                        tvAddress.setVisibility(View.VISIBLE);
                    }
                    tvName.setText("客户姓名：" + mDetailBean.getUser_name());
                    tvLawyerName.setText("" + mDetailBean.getLawyer_name());
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDetailBean.getLawyer_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                    if (mDetailBean.getBody() != null && mDetailBean.getBody().size() > 0) {
                        for (int i = 0; i < mDetailBean.getBody().size(); i++) {
                            if (Constants.ORDER_TAOCANID.equals(mDetailBean.getBody().get(i).getKey())) {
                                mDetailBean.getBody().remove(i);
                            }
                            if (Constants.ORDER_TYPE_ID.equals(mDetailBean.getBody().get(i).getKey())) {
                                mDetailBean.getBody().remove(i);
                            }
                            if (Constants.ORDER_HETONGLEIXING_ID.equals(mDetailBean.getBody().get(i).getKey())) {
                                mDetailBean.getBody().remove(i);
                            }
                        }
                    }
                    mBodyAdapter.refreshList(mDetailBean.getBody());

                    //评价
                    if (mDetailBean.getComment() != null && mDetailBean.getComment().getIs_comment() == 1) {
                        rlytEvaluate.setVisibility(View.VISIBLE);
                        tvEvaluateContent.setText("" + mDetailBean.getComment().getContent());
                        tvEvaluateTime.setText("" + mDetailBean.getComment().getCreate_time());
                        ratingbar.setStar(mDetailBean.getComment().getStar());
                        ratingbar.setClickable(false);
                        rlvType.setLayoutManager(new FlowLayoutManager());
                        mLabelAdapter = new LabelAdapter(mContext);
                        rlvType.setAdapter(mLabelAdapter);
//                        mLabelAdapter.refreshList(detailBean.getEvaluate().getLabel());
                        if (mDetailBean.getComment().getLabel() != null && mDetailBean.getComment().getLabel().size() > 0) {
                            List<LabelBean> labelBeans = new ArrayList<>();
                            for (int i = 0; i < mDetailBean.getComment().getLabel().size(); i++) {
                                LabelBean labelBean = new LabelBean();
                                labelBean.setName(mDetailBean.getComment().getLabel().get(i));
                                labelBeans.add(labelBean);
                            }
                            mLabelAdapter.refreshList(labelBeans);
                        }
                    } else {
                        rlytEvaluate.setVisibility(View.GONE);
                    }

                    //0 待结单 1已接单 （有律师）  2 已完成/评价
                    switch (mDetailBean.getShipping_status()) {
                        //待接单
                        case 0:
                            if ("1".equals(mDetailBean.getPay_status())) {
                                //已支付
                                if (StringUtils.isEmpty(mDetailBean.getLawyer_name())) {
                                    if (mDetailBean.getTop_id() == 2 ||
                                            mDetailBean.getTop_id() == 5 ||
                                            mDetailBean.getTop_id() == 46) {
                                        tvSubmit.setText("选择律师");
                                        tvSubmit.setVisibility(View.VISIBLE);
                                    } else if (mDetailBean.getTop_id() == 3) {
                                        if (Double.parseDouble(mDetailBean.getOrder_money()) > 0) {
                                            tvSubmit.setText("选择律师");
                                            tvSubmit.setVisibility(View.VISIBLE);
                                        } else {
                                            tvSubmit.setVisibility(View.GONE);
                                        }
                                    } else {
                                        tvSubmit.setVisibility(View.GONE);
                                    }
                                } else {
                                    tvSubmit.setVisibility(View.GONE);
                                }
                            } else {
//                                if (mDetailBean.getSon_id() == 66) {
//                                    tvSubmit.setText("选择律师");
//                                    tvSubmit.setVisibility(View.VISIBLE);
//                                } else {
                                tvSubmit.setText("立即支付");
//                                }
                            }
                            rlytLawyerName.setVisibility(View.GONE);
                            break;
                        //1已接单
                        case 1:
                            if (mDetailBean.getTop_id() != 8) {
                                if (!StringUtils.isEmpty(mDetailBean.getLawyer_name())) {
                                    rlytLawyerName.setVisibility(View.VISIBLE);
                                    tvSubmit.setVisibility(View.VISIBLE);
                                    tvSubmit.setText("确认订单完成");
                                } else {
                                    rlytLawyerName.setVisibility(View.GONE);
                                    tvSubmit.setVisibility(View.GONE);
                                    tvSubmit.setText("确认订单完成");
                                }
                            } else {
                                rlytLawyerName.setVisibility(View.GONE);
                                tvSubmit.setText("确认订单完成");
                            }
                            break;
                        //已完成
                        case 2:
                            if (!StringUtils.isEmpty(mDetailBean.getLawyer_name())) {
                                rlytLawyerName.setVisibility(View.VISIBLE);
                            }
//                            rlytEvaluate.setVisibility(View.VISIBLE);
                            tvSubmit.setVisibility(View.GONE);
                            break;
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
                finish();
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
                finish();
            }
        });
    }

    @OnClick({R.id.right_title, R.id.rlyt_lawyer_name, R.id.tv_submit})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //和接单人聊天
            case R.id.rlyt_lawyer_name:
                if (mDetailBean == null) {
                    getDetail();
                    ToastUtils.show(mContext, "数据异常，请稍后再试...");
                    return;
                }
                MessageUserBean userBean = new MessageUserBean();
                userBean.setName(mDetailBean.getLawyer_name());
                userBean.setHeader(mDetailBean.getLawyer_img());
                userBean.setId("" + mDetailBean.getLawyer_id());
                SaveUserUtils saveUserUtils = new SaveUserUtils(mContext);
                saveUserUtils.refreshHistory(userBean);
                EaseMobHelper.callChatIM(mContext, ChatActivity.class, "" + mDetailBean.getLawyer_name(),
                        "" + mDetailBean.getLawyer_id(), true);
                break;
            //查看合约
            case R.id.right_title:
//                OrderAgreeUtils.watchAgree(mContext);
                if (mDetailBean == null) {
                    getDetail();
                    ToastUtils.show(mContext, "网络异常，请稍后再试...");
                    return;
                }
                bundle.putString("name", "" + mDetailBean.getUser_name());
                bundle.putString("phone", "" + mDetailBean.getPhone());
                bundle.putString("address", "" + mDetailBean.getProvince() + mDetailBean.getCity() + mDetailBean.getDistrict());
                MyApplication.openActivity(mContext, ContractDetailActivity.class, bundle);
                break;
            //选择律师
            case R.id.tv_submit:
                if (mDetailBean == null) {
                    getDetail();
                    ToastUtils.show(mContext, "网络异常，请稍后再试...");
                    return;
                }
                if ("立即支付".equals(tvSubmit.getText().toString().trim())) {
                    if (mDetailBean.getTop_id() == 3 ||
                            mDetailBean.getTop_id() == 2 ||
                            mDetailBean.getTop_id() == 5 ||
                            mDetailBean.getTop_id() == 46) {
                        bundle.putInt("jumpType", 1);
                    } else {
                        bundle.putInt("jumpType", 0);
                    }
                    bundle.putString("orderNum", "" + mOrderId);
                    bundle.putString("orderMoney", "" + mDetailBean.getOrder_money());
                    MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
                } else if ("确认订单完成".equals(tvSubmit.getText().toString().trim())) {
                    bundle.putBoolean("isOrder", true);
                    bundle.putString("id", "" + mOrderId);
                    bundle.putString("name", "" + mDetailBean.getLawyer_name());
                    bundle.putString("header", "" + mDetailBean.getLawyer_img());
                    MyApplication.openActivityForResult(mContext, PublicEvaluateActivity.class, bundle, 101);
                } else if ("选择律师".equals(tvSubmit.getText().toString())) {
                    if (mDetailBean.getTop_id() == 3) {
                        bundle.putString("price", "" + mDetailBean.getOrder_money());
                    }
                    bundle.putString("classifyId", "" + mDetailBean.getSon_id());
                    bundle.putString("orderNum", "" + mOrderId);
                    if (!"市辖区".equals(mDetailBean.getCity())) {
                        bundle.putString("city", "" + mDetailBean.getCity());
                    } else {
                        bundle.putString("city", "" + mDetailBean.getProvince());
                    }
                    MyApplication.openActivity(mContext, SearchActivity.class, bundle);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            getDetail();
        }
    }

}
