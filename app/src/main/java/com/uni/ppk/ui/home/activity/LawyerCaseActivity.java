package com.uni.ppk.ui.home.activity;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.home.adapter.LawyerCaseAdapter2;
import com.uni.ppk.ui.home.bean.LawyerCaseBean2;
import com.uni.ppk.ui.home.bean.UserViewInfo;
import com.previewlibrary.GPreviewBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/23
 * Time: 13:46
 * 律师案例
 */
public class LawyerCaseActivity extends BaseActivity {
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
    private String mId = "";

    private LawyerCaseAdapter2 mAdapter;//案例

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lawyer_case;
    }

    @Override
    protected void initData() {
        mId = getIntent().getStringExtra("id");
        initTitle("更多案例");
        initCase();
        initRefreshLayout();
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page_size", "" + Constants.PAGE_SIZE);
        params.put("page", "" + mPage);
        params.put("lawyer_id", "" + mId);
        HttpUtils.moreCase(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<LawyerCaseBean2> listBeans = JSONUtils.jsonString2Beans(response, LawyerCaseBean2.class);
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

    //初始化案例
    private void initCase() {
        rlvList.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new LawyerCaseAdapter2(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<LawyerCaseBean2>() {
            @Override
            public void onItemClick(View view, int position, LawyerCaseBean2 model) {

            }

            @Override
            public void onItemLongClick(View view, int position, LawyerCaseBean2 model) {

            }
        });
        mAdapter.setPhotoAndVideoOnClicke(new LawyerCaseAdapter2.PhotoAndVideoOnClicke() {
            @Override
            public void photoOnClicke(LawyerCaseBean2 dataBean, int position, List<View> imageViews) {
                ArrayList<UserViewInfo> userViewInfos = new ArrayList<>();
                for (int i = 0; i < dataBean.getImgs().size(); i++) {
                    Rect rect = new Rect();
                    UserViewInfo userViewInfo = new UserViewInfo(dataBean.getImgs().get(i));
                    if (i > 8) {
                        imageViews.get(8).getGlobalVisibleRect(rect);
                    } else {
                        imageViews.get(i).getGlobalVisibleRect(rect);
                    }
                    userViewInfo.setBounds(rect);
                    userViewInfos.add(userViewInfo);
                }
                GPreviewBuilder.from(mContext)//activity实例必须
                        .setData(userViewInfos)//集合
                        .setCurrentIndex(position)
                        .setSingleFling(true)//是否在黑屏区域点击返回
                        .setDrag(true)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
            }

            @Override
            public void headerOnClicke(LawyerCaseBean2 dataBean) {
            }

            @Override
            public void deleteCase(int position, LawyerCaseBean2 bean) {

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
