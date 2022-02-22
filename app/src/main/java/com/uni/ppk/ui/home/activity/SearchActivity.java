package com.uni.ppk.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.home.adapter.SearchLawyerAdapter;
import com.uni.ppk.ui.home.bean.LawsuitJumpBean;
import com.uni.ppk.ui.home.bean.SearchLawyerBean;
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
 * Date: 2020/9/9
 * Time: 17:09
 * 搜索：找律师
 */
public class SearchActivity extends BaseActivity {
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
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.iv_city)
    ImageView ivCity;
    @BindView(R.id.llyt_city)
    LinearLayout llytCity;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.iv_quantity)
    ImageView ivQuantity;
    @BindView(R.id.llyt_quantity)
    LinearLayout llytQuantity;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;
    @BindView(R.id.iv_evaluate)
    ImageView ivEvaluate;
    @BindView(R.id.llyt_evaluate)
    LinearLayout llytEvaluate;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private SearchLawyerAdapter mAdapter;

    private String mReceiverSort = "desc";//按接单量排序：正序：asc，倒序：desc默认倒序
    private String mScoreSort = "desc";//根据律师评分排序：正序：asc，倒序：desc默认倒序
    private String mSearch = "";
    private String mCity = "";
    private String mClassifyId = "";//选择律师的子分类id
    private String mPrice = "";//价格筛选律师
    private String mOrderNum = "";//价格筛选律师
    private LawsuitJumpBean mJumpBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        initTitle("找律师");
        mClassifyId = getIntent().getStringExtra("classifyId");
        mPrice = getIntent().getStringExtra("price");
        mOrderNum = getIntent().getStringExtra("orderNum");
        mJumpBean = (LawsuitJumpBean) getIntent().getSerializableExtra("bean");

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SearchLawyerAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<SearchLawyerBean>() {
            @Override
            public void onItemClick(View view, int position, SearchLawyerBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getLawyer_id());
                if (!StringUtils.isEmpty(mClassifyId)) {
                    bundle.putBoolean("select", true);//是否为选择律师
                    if ("65".equals(mClassifyId)) {
                        bundle.putSerializable("bean", mJumpBean);
                        bundle.putString("classifyId", "" + mClassifyId);
                    } else {
                        bundle.putString("orderNum", "" + mOrderNum);
                    }
                }
                MyApplication.openActivity(mContext, LawyerDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, SearchLawyerBean model) {

            }
        });

        if (!StringUtils.isEmpty(mClassifyId)) {
            mCity = getIntent().getStringExtra("city");
            if (StringUtils.isEmpty(mCity)) {
                llytCity.setVisibility(View.GONE);
            } else {
                llytCity.setVisibility(View.VISIBLE);
                llytCity.setEnabled(false);
            }
        } else {
            mCity = MyApplication.mPreferenceProvider.getCity();
        }

        initRefreshLayout();
        getData();

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
    }

    private void getData() {
        tvCity.setText("" + mCity);
        mSearch = edtSearch.getText().toString().trim();
        Map<String, Object> params = new HashMap<>();
        params.put("page", "" + mPage);
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("case_num_sort", "" + mReceiverSort);
        params.put("score_sort", "" + mScoreSort);
        params.put("lawyer_name", "" + mSearch);
        params.put("city", "" + mCity);
//        params.put("typeid", (mClassifyId == null || "65".equals(mClassifyId)) ? "" : mClassifyId);
        params.put("typeid", (mClassifyId == null) ? "" : mClassifyId);
        params.put("price", mPrice == null ? "" : mPrice);
        HttpUtils.searchLawyerList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<SearchLawyerBean> listBeans = JSONUtils.parserArray(response, "data", SearchLawyerBean.class);
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

    @OnClick({R.id.llyt_city, R.id.llyt_quantity, R.id.llyt_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //城市
            case R.id.llyt_city:
                Bundle bundle = new Bundle();
                bundle.putBoolean("fish", true);
                MyApplication.openActivityForResult(mContext, CitySelectActivity.class, bundle, 101);
                break;
            //接单量
            case R.id.llyt_quantity:
                if ("desc".equals(mReceiverSort)) {
                    mReceiverSort = "asc";
                    setDesc(ivQuantity, false);
                } else {
                    mReceiverSort = "desc";
                    setDesc(ivQuantity, true);
                }
                break;
            //评价
            case R.id.llyt_evaluate:
                if ("desc".equals(mScoreSort)) {
                    mScoreSort = "asc";
                    setDesc(ivEvaluate, false);
                } else {
                    mScoreSort = "desc";
                    setDesc(ivEvaluate, true);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {
                mCity = data.getStringExtra("city");
                mPage = 1;
                getData();
            }
        }
    }

    //设置图标
    private void setDesc(ImageView ivIcon, boolean isDesc) {
        if (isDesc) {
            //倒序
            ivIcon.setImageResource(R.mipmap.ic_select_down);
        } else {
            //升序
            ivIcon.setImageResource(R.mipmap.ic_select_up);
        }
        mPage = 1;
        getData();
    }

}
