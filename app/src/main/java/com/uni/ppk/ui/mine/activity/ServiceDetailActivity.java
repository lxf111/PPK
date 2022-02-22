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
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.community.bean.CommunityDetailBean;
import com.uni.ppk.ui.home.activity.PayMoneyActivity;
import com.uni.ppk.ui.home.adapter.LabelAdapter;
import com.uni.ppk.ui.home.bean.LabelBean;
import com.uni.ppk.ui.mine.adapter.OrderPhotoAdapter;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CustomRatingBar;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/13
 * Time: 17:25
 * 我的咨询订单详情
 */
public class ServiceDetailActivity extends BaseActivity {
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
    @BindView(R.id.iv_lawyer_header)
    CircleImageView ivLawyerHeader;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;
    @BindView(R.id.tv_lawyer_name)
    TextView tvLawyerName;
    @BindView(R.id.tv_response)
    TextView tvResponse;
    @BindView(R.id.rlv_response_photo)
    CustomRecyclerView rlvResponsePhoto;
    @BindView(R.id.tv_response_time)
    TextView tvResponseTime;
    @BindView(R.id.llyt_response)
    LinearLayout llytResponse;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.llyt_price)
    LinearLayout llytPrice;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rlv_photo)
    CustomRecyclerView rlvPhoto;
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    //图片
    private OrderPhotoAdapter mPhotoAdapter;
    private OrderPhotoAdapter mResponseAdapter;

    private String mOrderId = "";

    //详情
    private CommunityDetailBean mDetailBean;

    private LabelAdapter mLabelAdapter;//评价标签

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void initData() {
        initTitle("详情");
        mOrderId = getIntent().getStringExtra("id");
        rlvPhoto.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mPhotoAdapter = new OrderPhotoAdapter(mContext);
        rlvPhoto.setAdapter(mPhotoAdapter);
        rlvResponsePhoto.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mResponseAdapter = new OrderPhotoAdapter(mContext);
        rlvResponsePhoto.setAdapter(mResponseAdapter);
        tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailBean == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("isOrder", false);
                bundle.putString("id", "" + mOrderId);
                bundle.putString("name", "" + mDetailBean.getAnswer().getUser_nickname());
                bundle.putString("header", "" + mDetailBean.getAnswer().getHead_img());
                MyApplication.openActivityForResult(mContext, PublicEvaluateActivity.class, bundle, 101);
            }
        });
        getDetail();
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailBean == null) {
                    getDetail();
                    ToastUtils.show(mContext, "网络异常，请稍后再试...");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("orderNum", "" + mDetailBean.getOrder_sn());
                bundle.putString("orderMoney", "" + mDetailBean.getReward());
                bundle.putInt("jumpType", 6);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
            }
        });
    }

    /**
     * 获取详情
     */
    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mOrderId);
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        HttpUtils.communityDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                CommunityDetailBean detailBean = JSONUtils.jsonString2Bean(response, CommunityDetailBean.class);
                if (detailBean != null) {
                    tvSubmit.setVisibility(View.GONE);
                    mDetailBean = detailBean;
                    tvName.setText("" + detailBean.getUser_nickname());
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(detailBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                    tvContent.setText("" + detailBean.getContent());
                    tvTime.setText("发布时间：" + detailBean.getCreate_time());
                    tvTitle.setText("" + detailBean.getTitle());
                    tvPrice.setText("" + ArithUtils.saveSecond(detailBean.getReward()));
                    mPhotoAdapter.refreshList(detailBean.getPictures());
                    //回复
                    if (detailBean.getAnswer() != null && detailBean.getIs_answer() == 1) {
                        tvLawyerName.setText("" + detailBean.getAnswer().getUser_nickname());
                        ImageUtils.getPic(NetUrlUtils.createPhotoUrl(detailBean.getAnswer().getHead_img()), ivLawyerHeader, mContext, R.mipmap.ic_default_header);
                        tvResponse.setText("" + detailBean.getAnswer().getContent());
                        tvResponseTime.setText("回复日期：" + detailBean.getAnswer().getCreate_time());
                        mResponseAdapter.refreshList(detailBean.getAnswer().getPictures());
                        llytResponse.setVisibility(View.VISIBLE);
                    } else {
                        llytResponse.setVisibility(View.GONE);
                        if (mDetailBean.getIs_pay() == 0) {
                            //未支付
                            tvSubmit.setVisibility(View.VISIBLE);
                        }
                    }
                    //评价
                    if (detailBean.getEvaluate() != null && detailBean.getIs_evaluate() == 1) {
                        tvEvaluate.setVisibility(View.GONE);
                        rlytEvaluate.setVisibility(View.VISIBLE);
                        tvEvaluateContent.setText("" + detailBean.getEvaluate().getContent());
                        tvEvaluateTime.setText("" + detailBean.getEvaluate().getCreate_time());
                        ratingbar.setStar(detailBean.getEvaluate().getStar());
                        ratingbar.setClickable(false);
                        rlvType.setLayoutManager(new FlowLayoutManager());
                        mLabelAdapter = new LabelAdapter(mContext);
                        rlvType.setAdapter(mLabelAdapter);
//                        mLabelAdapter.refreshList(detailBean.getEvaluate().getLabel());
                        if (detailBean.getEvaluate().getLabel() != null && detailBean.getEvaluate().getLabel().size() > 0) {
                            List<LabelBean> labelBeans = new ArrayList<>();
                            for (int i = 0; i < detailBean.getEvaluate().getLabel().size(); i++) {
                                LabelBean labelBean = new LabelBean();
                                labelBean.setName(mDetailBean.getEvaluate().getLabel().get(i));
                                labelBeans.add(labelBean);
                            }
                            mLabelAdapter.refreshList(labelBeans);
                        }
                    } else {
                        rlytEvaluate.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            getDetail();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
