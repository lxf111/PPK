package com.uni.ppk.ui.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.mine.activity.OrderDetailActivity;
import com.uni.ppk.ui.mine.adapter.OrderAdapter;
import com.uni.ppk.ui.mine.bean.OrderBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:00
 * 我的订单
 */
public class OrderFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private OrderAdapter mAdapter;

    private String mStatus = "";//订单状态,待接单：unreceive，进行中：receive，已完成：finish

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_order, null);
        return mRootView;
    }

    @Override
    public void initView() {
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OrderAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<OrderBean>() {
            @Override
            public void onItemClick(View view, int position, OrderBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getOrder_sn());
                MyApplication.openActivity(mContext, OrderDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, OrderBean model) {

            }
        });
        if (getArguments().getInt("index", 0) == 0) {
            mStatus = "unreceive";
        } else if (getArguments().getInt("index", 0) == 1) {
            mStatus = "receive";
        } else {
            mStatus = "finish";
        }
    }

    @Override
    public void initData() {
        initRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 1;
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("shipping_status", "" + mStatus);
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("page", "" + mPage);
        HttpUtils.orderList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<OrderBean> listBeans = JSONUtils.parserArray(response, "data", OrderBean.class);
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
            getData();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData();
        });
    }
}
