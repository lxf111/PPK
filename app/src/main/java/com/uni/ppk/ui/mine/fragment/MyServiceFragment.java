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
import com.uni.ppk.ui.mine.activity.ServiceDetailActivity;
import com.uni.ppk.ui.mine.adapter.MyServiceAdapter;
import com.uni.ppk.ui.mine.bean.MyServiceBean;
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
 * Time: 17:40
 * 我的咨询
 */
public class MyServiceFragment extends LazyBaseFragments {
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private MyServiceAdapter mAdapter;
    private int mIndex = 0;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_my_service, null);
        return mRootView;
    }

    @Override
    public void initView() {
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MyServiceAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<MyServiceBean>() {
            @Override
            public void onItemClick(View view, int position, MyServiceBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, ServiceDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, MyServiceBean model) {

            }
        });
    }

    @Override
    public void initData() {
        mIndex = getArguments().getInt("index", 0);
        initRefreshLayout();
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "" + mIndex);
        params.put("page_size", "" + Constants.PAGE_SIZE);
        params.put("page", "" + mPage);
        HttpUtils.serviceList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<MyServiceBean> listBeans = JSONUtils.jsonString2Beans(response, MyServiceBean.class);
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
