package com.uni.ppk.ui.home.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.SelectVipPopup;
import com.uni.ppk.ui.home.adapter.VipListAdapter;
import com.uni.ppk.ui.home.bean.VipListBean;
import com.uni.ppk.ui.home.bean.VipNewBean;
import com.uni.ppk.utils.LoginCheckUtils;
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
 * Date: 2020/9/15
 * Time: 8:38
 * 会员权益
 */
public class VipListActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_create_order)
    TextView tvCreateOrder;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;

    private VipListAdapter mAdapter;

    private SelectVipPopup mVipPopup;

    private List<VipListBean> mPersonList = new ArrayList<>();
    private List<VipListBean> mCompanyList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_list;
    }

    @Override
    protected void initData() {
        initTitle("会员权益");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new VipListAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        getVipList();
        if (Integer.parseInt(MyApplication.mPreferenceProvider.getLevel()) > 0) {
            tvSubmit.setVisibility(View.INVISIBLE);
            llytBottom.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.VISIBLE);
            llytBottom.setVisibility(View.GONE);
        }
    }

    //获取vip列表
    private void getVipList() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.vipList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                VipNewBean newBean = JSONUtils.jsonString2Bean(response, VipNewBean.class);
                List<VipListBean> beans = new ArrayList<>();
                if (newBean != null) {
                    if (newBean.getPersonal() != null && newBean.getPersonal().size() > 0) {
                        mPersonList = newBean.getPersonal();
                        beans.addAll(mPersonList);
                    }
                    if (newBean.getCompany() != null && newBean.getCompany().size() > 0) {
                        mCompanyList = newBean.getCompany();
                        beans.addAll(mCompanyList);
                    }
                }
                mAdapter.refreshList(beans);
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

            }
        });
    }

    @OnClick({R.id.tv_submit, R.id.tv_open_vip, R.id.tv_create_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
            case R.id.tv_open_vip:
                if (mAdapter.getItemCount() < 4) {
                    ToastUtils.show(mContext, "网络异常，请稍后再试...");
                    return;
                }
                //开通
                if (mVipPopup == null) {
                    mVipPopup = new SelectVipPopup(mContext, mPersonList, mCompanyList);
                }
                mVipPopup.showAtLocation(tvSubmit, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_create_order:
                if(Integer.parseInt(MyApplication.mPreferenceProvider.getLevel()) > 0){
                    try {
                        if (!LoginCheckUtils.checkUserIsLogin(mContext)) {
                            ToastUtils.show(mContext, "未登录，请先登录");
                            return;
                        }
                        OrderAgreeUtils.jumpChat(mContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", 3);
                    bundle.putString("title", "法律咨询");
                    bundle.putString("id", "3");
                    MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                }
                break;
        }
    }
}
