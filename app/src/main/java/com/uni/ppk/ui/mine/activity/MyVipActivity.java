package com.uni.ppk.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.NormalWebViewActivity;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.NormalWebViewConfig;
import com.uni.ppk.ui.mine.adapter.VipProfitAdapter;
import com.uni.ppk.ui.mine.bean.MineVipBean;
import com.uni.ppk.utils.AppDate;
import com.uni.ppk.utils.OrderAgreeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/17
 * Time: 15:30
 * 我的会员权益
 */
public class MyVipActivity extends BaseActivity {
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_renew)
    TextView tvRenew;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;

    private VipProfitAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_vip;
    }

    @Override
    protected void initData() {
        tvName.setText("" + MyApplication.mPreferenceProvider.getUserName());
        ImageUtils.getPic(NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getPhoto()), ivHeader, mContext, R.mipmap.ic_default_header);
        try {
            tvTime.setText("会员有效期至 " + AppDate.timet(MyApplication.mPreferenceProvider.getLastTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new VipProfitAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        getVipList();
    }

    /**
     * 我的vip
     */
    private void getVipList() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.getMineVip(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                MineVipBean vipBean = JSONUtils.jsonString2Bean(response, MineVipBean.class);
                if (vipBean != null) {
                    tvGrade.setText("" + vipBean.getName());
                    if (vipBean.getService() != null) {
                        mAdapter.refreshList(vipBean.getService());
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

    @OnClick({R.id.iv_back, R.id.tv_renew, R.id.iv_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_question:
                getAgree();
                break;
            case R.id.tv_renew:
                OrderAgreeUtils.jumpVip(mContext);
                break;
        }
    }

    private void getAgree() {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "6");
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                Bundle bundle = new Bundle();
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
                bundle.putString(NormalWebViewConfig.URL, "" + content);
                bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, true);
                MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
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

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean needStatusBarDarkText() {
        return false;
    }
}
