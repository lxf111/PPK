package com.uni.ppk.ui.mine.activity;

import android.graphics.drawable.Drawable;
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
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.pop.ShareCodePopup;
import com.uni.ppk.pop.SharePopup;
import com.uni.ppk.ui.mine.adapter.RecommendAdapter;
import com.uni.ppk.ui.mine.bean.RecommendBean;
import com.uni.ppk.utils.ArithUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 11:28
 * 我的推荐
 */
public class MyRecommendActivity extends BaseActivity {
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
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_month_money)
    TextView tvMonthMoney;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.llyt_withdraw)
    LinearLayout llytWithdraw;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private RecommendAdapter mAdapter;

    private ShareCodePopup mCodePopup;//分享二维码

    private SharePopup mSharePopup;//分享微信朋友圈

    private int mSelectType = 1;//1直接推荐 2间接推荐

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_recommend;
    }

    @Override
    protected void initData() {
        initTitle("我的推荐");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_community_share);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(drawable, null, null, null);

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RecommendAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<RecommendBean>() {
            @Override
            public void onItemClick(View view, int position, RecommendBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, ProfitListActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, RecommendBean model) {

            }
        });

        initRefreshLayout();
        getDirect();
        getProfit();
    }

    //获取我的收益
    private void getProfit() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.profitData(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    return;
                }
                String today = JSONUtils.getNoteJson(response, "today");
                String month = JSONUtils.getNoteJson(response, "month");
                String total = JSONUtils.getNoteJson(response, "total");
                tvBalance.setText("" + ArithUtils.saveSecond(today));
                tvMonthMoney.setText("" + ArithUtils.saveSecond(month));
                tvTotalMoney.setText("" + ArithUtils.saveSecond(total));
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

            }
        });
    }

    @OnClick({R.id.llyt_withdraw, R.id.tv_recommend, R.id.iv_back, R.id.tv_public, R.id.right_title, R.id.tv_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            //提现
            case R.id.llyt_withdraw:
                MyApplication.openActivity(mContext, WithdrawActivity.class);
                break;
            //分享二维码
            case R.id.tv_public:
                mCodePopup = new ShareCodePopup(mContext, new ShareCodePopup.OnShareCodeCallback() {
                    @Override
                    public void onCodeCallback(String qrCode) {
                        mCodePopup.dismiss();
                        mSharePopup = new SharePopup(mContext);
                        mSharePopup.setmImg(qrCode);
                        mSharePopup.showAtLocation(tvBalance, Gravity.BOTTOM, 0, 0);
                    }
                });
                mCodePopup.showAtLocation(tvBalance, Gravity.CENTER, 0, 0);
                break;
            //直接推荐
            case R.id.tv_recommend:
                mSelectType = 1;
                tvRecommend.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tvSecond.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                mPage = 1;
                getDirect();
                break;
            //间接推荐
            case R.id.tv_second:
                mSelectType = 2;
                tvRecommend.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                tvSecond.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                mPage = 1;
                getIndirect();
                break;
        }
    }

    /**
     * 获取直接推荐
     */
    private void getDirect() {
        Map<String, Object> params = new HashMap<>();
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("page", "" + mPage);
        HttpUtils.recommendDirect(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<RecommendBean> listBeans = JSONUtils.parserArray(response, "data", RecommendBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mAdapter.refreshList(listBeans);
                        llytNoData.setVisibility(View.GONE);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        llytNoData.setVisibility(View.VISIBLE);
                        mAdapter.clear();
                    }
                } else {
                    if (listBeans != null && listBeans.size() > 0) {
                        refreshLayout.finishLoadMore();
                        mAdapter.appendToList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    llytNoData.setVisibility(View.VISIBLE);
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    llytNoData.setVisibility(View.VISIBLE);
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    /**
     * 获取间接推荐
     */
    private void getIndirect() {
        Map<String, Object> params = new HashMap<>();
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("page", "" + mPage);
        HttpUtils.recommendIndirect(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<RecommendBean> listBeans = JSONUtils.parserArray(response, "data", RecommendBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mAdapter.refreshList(listBeans);
                        llytNoData.setVisibility(View.GONE);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        llytNoData.setVisibility(View.VISIBLE);
                        mAdapter.clear();
                    }
                } else {
                    if (listBeans != null && listBeans.size() > 0) {
                        refreshLayout.finishLoadMore();
                        mAdapter.appendToList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    llytNoData.setVisibility(View.VISIBLE);
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    llytNoData.setVisibility(View.VISIBLE);
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    private void initRefreshLayout() {
        llytNoData.setVisibility(View.GONE);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            if (mSelectType == 1) {
                getDirect();
            } else {
                getIndirect();
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            if (mSelectType == 1) {
                getDirect();
            } else {
                getIndirect();
            }
        });
    }
}
