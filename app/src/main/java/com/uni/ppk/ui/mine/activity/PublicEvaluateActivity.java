package com.uni.ppk.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.adapter.EvaluateLabelAdapter;
import com.uni.ppk.ui.mine.bean.EvaluateLabelBean;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CustomRatingBar;
import com.uni.ppk.widget.FlowLayoutManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/28
 * Time: 9:10
 * 发布评价
 */
public class PublicEvaluateActivity extends BaseActivity {
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
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ratingbar)
    CustomRatingBar ratingbar;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.llyt_lawyer)
    LinearLayout llytLawyer;

    private EvaluateLabelAdapter mLabelAdapter;

    private String mId = "";//订单id
    private boolean isOrder = false;//是否为服务订单 true是 false否

    private String mstar = "5";//默认五颗星
    private String mName = "";//昵称
    private String mHeader = "";//头像

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_evaluate;
    }

    @Override
    protected void initData() {
        mId = getIntent().getStringExtra("id");
        mName = getIntent().getStringExtra("name");
        mHeader = getIntent().getStringExtra("header");
        isOrder = getIntent().getBooleanExtra("isOrder", false);
        if (!StringUtils.isEmpty(mName)) {
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mHeader), ivHeader, mContext, R.mipmap.ic_default_header);
            tvName.setText("" + mName);
            llytLawyer.setVisibility(View.VISIBLE);
            initTitle("律师评价");
        } else {
            initTitle("订单评价");
        }
        ratingbar.setOnRatingChangeListener(new CustomRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                mstar = "" + ArithUtils.saveInt(ratingCount);
            }
        });

        rlvList.setLayoutManager(new FlowLayoutManager());
        mLabelAdapter = new EvaluateLabelAdapter(mContext);
        rlvList.setAdapter(mLabelAdapter);
        getLabel();
    }

    //获取标签
    private void getLabel() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.evaluateLabel(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<EvaluateLabelBean> beans = JSONUtils.jsonString2Beans(response, EvaluateLabelBean.class);
                if (beans != null && beans.size() > 0) {
                    mLabelAdapter.refreshList(beans);
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
        //提交
        if (!isOrder) {
            submitCommunity();
        } else {
            submitorder();
        }
    }

    //社群的评价
    private void submitCommunity() {
        String content = edtContent.getText().toString().trim();
        String label = "";
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(mContext, "请输入内容");
            return;
        }
        for (int i = 0; i < mLabelAdapter.getItemCount(); i++) {
            if (mLabelAdapter.getItem(i).isSelect()) {
                if (StringUtils.isEmpty(label)) {
                    label = "" + mLabelAdapter.getItem(i).getName();
                } else {
                    label = label + "," + mLabelAdapter.getItem(i).getName();
                }
            }
        }
        if (StringUtils.isEmpty(label)) {
            ToastUtils.show(mContext, "请选择标签");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("label", "" + label);
        params.put("star", "" + mstar);
        params.put("content", "" + content);
        HttpUtils.communityEvaluate(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                setResult(RESULT_OK);
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

    //订单的评价
    private void submitorder() {
        String content = edtContent.getText().toString().trim();
        String label = "";
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(mContext, "请输入内容");
            return;
        }
        for (int i = 0; i < mLabelAdapter.getItemCount(); i++) {
            if (mLabelAdapter.getItem(i).isSelect()) {
                if (StringUtils.isEmpty(label)) {
                    label = "" + mLabelAdapter.getItem(i).getName();
                } else {
                    label = label + "," + mLabelAdapter.getItem(i).getName();
                }
            }
        }
        if (StringUtils.isEmpty(label)) {
            ToastUtils.show(mContext, "请选择标签");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("order_sn", "" + mId);
        params.put("label", "" + label);
        params.put("star", "" + mstar);
        params.put("content", "" + content);
        HttpUtils.orderEvaluate(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                setResult(RESULT_OK);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
