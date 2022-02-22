package com.uni.ppk.ui.community;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.config.Constants;
import com.uni.ppk.pop.CommonSelectPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.ui.community.activity.CommunityDetailActivity;
import com.uni.ppk.ui.community.activity.PublicCommunityActivity;
import com.uni.ppk.ui.community.adapter.CommunityAdapter;
import com.uni.ppk.ui.community.bean.CommunityBean;
import com.uni.ppk.ui.community.bean.ReportTypeBean;
import com.uni.ppk.ui.home.bean.UserViewInfo;
import com.uni.ppk.utils.LoginCheckUtils;
import com.previewlibrary.GPreviewBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:17
 * 社群
 */
public class CommunityFragment extends LazyBaseFragments {
    @BindView(R.id.tv_public)
    TextView tvPublic;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.view_top)
    View viewTop;

    private int mPage = 1;
    private CommunityAdapter mAdapter;
    private String mSearch = "";
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//rxbus取消订阅

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_community, null);
        return mRootView;
    }

    @Override
    public void initView() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
        tvPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, PublicCommunityActivity.class);
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
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String type) {
                        if ("refreshCommunity".equals(type)) {
                            mPage = 1;
                            getData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initData() {
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommunityAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        initRefreshLayout();
        getData();

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
//                TipsUtils.show(mContext, tvPublic, "温馨提示", "确定举报吗？", new TipsPopup.OnTipsCallback() {
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
    }

    private void getData() {
        mSearch = edtSearch.getText().toString().trim();
        Map<String, Object> params = new HashMap<>();
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("page_size", "" + Constants.PAGE_SIZE);
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        params.put("page", "" + mPage);
        params.put("keyword", "" + mSearch);
        HttpUtils.communityList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<CommunityBean> listBeans = JSONUtils.jsonString2Beans(response, CommunityBean.class);
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
                commonSelectPopup.showAtLocation(tvPublic, Gravity.BOTTOM, 0, 0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
