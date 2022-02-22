package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.NormalWebViewActivity;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.config.NormalWebViewConfig;
import com.uni.ppk.ui.mine.adapter.HelpAdapter;
import com.uni.ppk.ui.mine.bean.HelpBean;
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
 * Date: 2020/9/15
 * Time: 17:44
 * 帮助中心
 */
public class HelpActivity extends BaseActivity {
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
    @BindView(R.id.tv_customer)
    TextView tvCustomer;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;

    private HelpAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initData() {
        initTitle("帮助中心");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new HelpAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<HelpBean>() {
            @Override
            public void onItemClick(View view, int position, HelpBean model) {
                getAgree("" + model.getId());
            }

            @Override
            public void onItemLongClick(View view, int position, HelpBean model) {

            }
        });

        initRefreshLayout();
        getData();
    }

    @OnClick(R.id.tv_customer)
    public void onViewClicked() {
//        //联系客服
//        OrderAgreeUtils.jumpChat(mContext);
        callPhone("4000087807");
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page_size", "" + Constants.PAGE_SIZE);
        params.put("category_id", "" + 2);
        params.put("page", "" + mPage);
        HttpUtils.helpList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<HelpBean> listBeans = JSONUtils.parserArray(response, "data", HelpBean.class);
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
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            getData();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData();
        });
    }

    private void getAgree(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + type);
        HttpUtils.helpDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                Bundle bundle = new Bundle();
                String title = JSONUtils.getNoteJson(response, "title");
                String content = JSONUtils.getNoteJson(response, "body");
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

}
