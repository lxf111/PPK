package com.uni.ppk.ui.home.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.home.adapter.ClassroomAdapter;
import com.uni.ppk.ui.home.bean.ClassroomBean;
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
 * Time: 14:30
 * 在线课堂
 */
public class ClassroomActivity extends BaseActivity {
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
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;

    private ClassroomAdapter mAdapter;

    private String mSearch = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classroom;
    }

    @Override
    protected void initData() {
        initTitle("在线课堂");
        rlvList.setLayoutManager(new GridLayoutManager(mContext, 2));
        mAdapter = new ClassroomAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<ClassroomBean>() {
            @Override
            public void onItemClick(View view, int position, ClassroomBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, VideoDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, ClassroomBean model) {

            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    SoftInputUtils.hideSoftInput(mContext);
                    mPage = 1;
                    getData();
                    return true;
                }
                return false;
            }
        });

        initRefreshLayout();
        getData();
    }

    private void getData() {
        mSearch = edtSearch.getText().toString().trim();
        Map<String, Object> params = new HashMap<>();
        params.put("page", "" + mPage);
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("title", "" + mSearch);
        HttpUtils.courseList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<ClassroomBean> listBeans = JSONUtils.parserArray(response, "data", ClassroomBean.class);
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
