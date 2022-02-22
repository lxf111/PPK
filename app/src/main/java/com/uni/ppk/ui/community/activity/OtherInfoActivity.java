package com.uni.ppk.ui.community.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.pop.CommonSelectPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.ui.community.adapter.CommunityAdapter;
import com.uni.ppk.ui.community.bean.CommunityBean;
import com.uni.ppk.ui.community.bean.OtherInfoBean;
import com.uni.ppk.ui.community.bean.ReportTypeBean;
import com.uni.ppk.ui.home.bean.UserViewInfo;
import com.previewlibrary.GPreviewBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
 * Date: 2020/9/17
 * Time: 15:51
 * 他人主页
 */
public class OtherInfoActivity extends BaseActivity {
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
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private CommunityAdapter mAdapter;

    private String mUid = "";//对方的uid
    private String mUserType = "";//对方的用户类型

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_info;
    }

    @Override
    protected void initData() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);

        mUid = getIntent().getStringExtra("uid");
        mUserType = getIntent().getStringExtra("userType");

        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommunityAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommunityBean>() {
            @Override
            public void onItemClick(View view, int position, CommunityBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, CommunityDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, CommunityBean model) {

            }
        });
        mAdapter.setPhotoAndVideoOnClicke(new CommunityAdapter.PhotoAndVideoOnClicke() {
            @Override
            public void photoOnClicke(CommunityBean dataBean, int position, List<View> imageViews) {
                ArrayList<UserViewInfo> userViewInfos = new ArrayList<>();
                for (int i = 0; i < dataBean.getPictures().size(); i++) {
                    Rect rect = new Rect();
                    UserViewInfo userViewInfo = new UserViewInfo(dataBean.getPictures().get(i));
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
            public void headerOnClicke(CommunityBean dataBean) {
//                Intent intent = new Intent(mContext, OthersHomeActivity.class);
//                mContext.startActivity(intent);
            }

            //举报
            @Override
            public void report(int position, CommunityBean dataBean) {
//                TipsUtils.show(mContext, tvContent, "温馨提示", "确定举报吗？", new TipsPopup.OnTipsCallback() {
//                    @Override
//                    public void submit() {
//                        reportCommunity("" + dataBean.getId());
//                    }
//
//                    @Override
//                    public void cancel() {
//
//                    }
//                });
                reportType("" + dataBean.getId());
            }
        });

        initRefreshLayout();
        getData();
    }

    /**
     * 举报
     */
    private void reportType(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "1");
        HttpUtils.reportType(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
//                ToastUtils.show(mContext, msg);
                List<ReportTypeBean> typeBeans = JSONUtils.jsonString2Beans(response, ReportTypeBean.class);
                if (typeBeans == null) {
                    return;
                }
                List<CommonSelectBean> selectBeans = new ArrayList<>();
                for (int i = 0; i < typeBeans.size(); i++) {
                    CommonSelectBean bean = new CommonSelectBean();
                    bean.setName(typeBeans.get(i).getTitle());
                    selectBeans.add(bean);
                }
                CommonSelectPopup commonSelectPopup = new CommonSelectPopup(mContext);
                commonSelectPopup.setTitle("举报类型");
                commonSelectPopup.setmSelectBeans(selectBeans);
                commonSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                    @Override
                    public void onItemClick(View view, int position, CommonSelectBean model) {
                        commonSelectPopup.dismiss();
                        reportCommunity("" + id, model.getName());
                    }

                    @Override
                    public void onItemLongClick(View view, int position, CommonSelectBean model) {

                    }
                });
                commonSelectPopup.showAtLocation(tvContent, Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    /**
     * 举报
     */
    private void reportCommunity(String id,String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("id", "" + id);
        params.put("report_title", "" + type);
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        HttpUtils.communityReport(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("login_user_type", "" + Constants.USER_TYPE);
        params.put("page_size", "" + Constants.PAGE_SIZE);
        params.put("login_user_id", "" + MyApplication.mPreferenceProvider.getUId());
        params.put("page", "" + mPage);
        params.put("user_type", "" + mUserType);
        params.put("user_id", "" + mUid);
        HttpUtils.otherInfo(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                OtherInfoBean listBeans = JSONUtils.jsonString2Bean(response, OtherInfoBean.class);
                if (mPage == 1) {
                    if (listBeans.getUser_info() != null) {
                        tvName.setText("" + listBeans.getUser_info().getUser_nickname());
                        ImageUtils.getPic(NetUrlUtils.createPhotoUrl(listBeans.getUser_info().getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                        tvContent.setText("" + listBeans.getUser_info().getDesc());
                    }
                    refreshLayout.finishRefresh();
                    if (listBeans.getList() != null && listBeans.getList().size() > 0) {
                        mAdapter.refreshList(listBeans.getList());
                        llytNoData.setVisibility(View.GONE);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        llytNoData.setVisibility(View.VISIBLE);
                        mAdapter.clear();
                    }
                } else {
                    if (listBeans.getList() != null && listBeans.getList().size() > 0) {
                        refreshLayout.finishLoadMore();
                        mAdapter.appendToList(listBeans.getList());
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
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
