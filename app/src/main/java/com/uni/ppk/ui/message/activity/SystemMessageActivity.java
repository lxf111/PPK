package com.uni.ppk.ui.message.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.message.adapter.MessageAdapter;
import com.uni.ppk.ui.message.bean.MessageBean;
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
 * Date: 2020/9/15
 * Time: 16:27
 * 系统消息
 */
public class SystemMessageActivity extends BaseActivity {
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
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private MessageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initData() {
        initTitle("系统消息");
        initRefreshLayout();
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MessageAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        getData();

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<MessageBean>() {
            @Override
            public void onItemClick(View view, int position, MessageBean model) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", model);
                MyApplication.openActivity(mContext, MessageDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, MessageBean model) {

            }
        });
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("page", "" + mPage);
        params.put("type", "" + 3);
        HttpUtils.messageList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<MessageBean> listBeans = JSONUtils.jsonString2Beans(response, MessageBean.class);
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
