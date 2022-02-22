package com.uni.ppk.ui.home.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
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
import com.uni.ppk.ui.home.adapter.LawyerCaseAdapter;
import com.uni.ppk.ui.home.adapter.LawyerCommentAdapter;
import com.uni.ppk.ui.home.adapter.ServiceTypeAdapter;
import com.uni.ppk.ui.home.bean.LawsuitJumpBean;
import com.uni.ppk.ui.home.bean.LawyerCaseBean;
import com.uni.ppk.ui.home.bean.LawyerCommentBean;
import com.uni.ppk.ui.home.bean.LawyerDetailBean;
import com.uni.ppk.ui.home.bean.ServiceTypeBean;
import com.uni.ppk.ui.home.bean.UserViewInfo;
import com.uni.ppk.ui.mine.activity.EnlargePhotoActivity;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CustomRatingBar;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FloatRatingBar;
import com.previewlibrary.GPreviewBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 11:21
 * 律师详情
 */
public class LawyerDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_firm_name)
    TextView tvFirmName;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_receiver_num)
    TextView tvReceiverNum;
    @BindView(R.id.ratingbar)
    CustomRatingBar ratingbar;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.rlv_type)
    CustomRecyclerView rlvType;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.rlv_case)
    CustomRecyclerView rlvCase;
    @BindView(R.id.rlv_comment)
    CustomRecyclerView rlvComment;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.rating_bar4)
    FloatRatingBar ratingBar4;
    @BindView(R.id.tv_more)
    TextView tvMore;

    private ServiceTypeAdapter mTypeAdapter;//服务类型

    private LawyerCaseAdapter mCaseAdapter;//案例

    private LawyerCommentAdapter mCommentAdapter;//律师评价

    private LawsuitJumpBean mJumpBean;

    private String mId = "";
    private int mPage = 1;
    private boolean isSelect = false;//是否未选择律师
    private String mOrderNum = "";
    private String mClassifyId = "";
    private LawyerDetailBean mDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lawyer_detail;
    }

    @Override
    protected void initData() {
        mId = getIntent().getStringExtra("id");
        isSelect = getIntent().getBooleanExtra("select", false);
        initTitle("律师主页");
        ratingbar.setClickable(false);
        initRefreshLayout();
        //初始化服务类型
        initServiceType();
        //初始化案例
        initCase();
        //初始化评论
        initComment();
        //获取律师详情
        getDetail();
        //获取律师的评价列表
        getEvaluate();
        if (isSelect) {
            mClassifyId = getIntent().getStringExtra("classifyId");
            if ("65".equals(mClassifyId)) {
                mJumpBean = (LawsuitJumpBean) getIntent().getSerializableExtra("bean");
                tvSubmit.setVisibility(View.GONE);
            } else {
                mOrderNum = getIntent().getStringExtra("orderNum");
                tvSubmit.setVisibility(View.VISIBLE);
            }
        } else {
            tvSubmit.setVisibility(View.GONE);
        }
    }

    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("lawyer_id", "" + mId);
        HttpUtils.lawyerDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LawyerDetailBean bean = JSONUtils.jsonString2Bean(response, LawyerDetailBean.class);
                if (bean != null) {
                    mDetailBean = bean;
                    tvName.setText("" + bean.getUser_name());
                    tvFirmName.setText("" + bean.getLaw_firm());
                    tvOrderNum.setText("执业编号：" + bean.getPractice_num());
                    tvYear.setText("执业年限：" + bean.getPractice_years() + "年");
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(bean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
//                    ratingbar.setStar(Float.parseFloat(ArithUtils.saveSecond(bean.getScore())));
                    ratingBar4.setRate(Float.parseFloat(ArithUtils.saveSecond(bean.getScore())));
                    tvReceiverNum.setText("" + bean.getCase_num() + "单");
                    tvScore.setText("" + bean.getScore());
                    mTypeAdapter.refreshList(bean.getService());
                    tvIntroduction.setText("" + bean.getIntroduction());
                    List<LawyerCaseBean> caseBeans = new ArrayList<>();
                    if (bean.getLawyer_case() != null && !StringUtils.isEmpty(bean.getLawyer_case().getTitle())) {
                        caseBeans.add(bean.getLawyer_case());
                    }
                    mCaseAdapter.refreshList(caseBeans);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
                finish();
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
                finish();
            }
        });
    }

    private void getEvaluate() {
        Map<String, Object> params = new HashMap<>();
        params.put("lawyer_id", "" + mId);
        params.put("page", "" + mPage);
        params.put("size", "" + Constants.PAGE_SIZE);
        HttpUtils.lawyerEvaluate(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<LawyerCommentBean> listBeans = JSONUtils.parserArray(response, "data", LawyerCommentBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mCommentAdapter.refreshList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        mCommentAdapter.clear();
                    }
                } else {
                    if (listBeans != null && listBeans.size() > 0) {
                        refreshLayout.finishLoadMore();
                        mCommentAdapter.appendToList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mCommentAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mCommentAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }


    //初始化服务类型
    private void initServiceType() {
        rlvType.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mTypeAdapter = new ServiceTypeAdapter(mContext);
        rlvType.setAdapter(mTypeAdapter);
        mTypeAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<ServiceTypeBean>() {
            @Override
            public void onItemClick(View view, int position, ServiceTypeBean model) {
                if (isSelect) {
                    if ("65".equals(mClassifyId)) {
                        createOrder("" + model.getTypeid());
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "法律咨询");
                    bundle.putString("classifyTitle", "" + model.getName());
                    bundle.putString("classifyId", "" + model.getTypeid());
                    bundle.putString("id", "3");
                    bundle.putString("lawyerId", "" + mId);
                    bundle.putString("money", "" + model.getPrice());
                    MyApplication.openActivity(mContext, OrderEnquireActivity.class, bundle);
                }
            }

            @Override
            public void onItemLongClick(View view, int position, ServiceTypeBean model) {

            }
        });
    }

    //初始化案例
    private void initCase() {
        rlvCase.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCaseAdapter = new LawyerCaseAdapter(mContext);
        rlvCase.setAdapter(mCaseAdapter);
        mCaseAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<LawyerCaseBean>() {
            @Override
            public void onItemClick(View view, int position, LawyerCaseBean model) {

            }

            @Override
            public void onItemLongClick(View view, int position, LawyerCaseBean model) {

            }
        });
        mCaseAdapter.setPhotoAndVideoOnClicke(new LawyerCaseAdapter.PhotoAndVideoOnClicke() {
            @Override
            public void photoOnClicke(LawyerCaseBean dataBean, int position, List<View> imageViews) {
                ArrayList<UserViewInfo> userViewInfos = new ArrayList<>();
//                String sp[] = dataBean.getThumb().split(",");
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
            public void headerOnClicke(LawyerCaseBean dataBean) {
//                Intent intent = new Intent(mContext, OthersHomeActivity.class);
//                mContext.startActivity(intent);
            }
        });

    }

    //初始化评论
    private void initComment() {
        rlvComment.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCommentAdapter = new LawyerCommentAdapter(mContext);
        rlvComment.setAdapter(mCommentAdapter);
        mCommentAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<LawyerCommentBean>() {
            @Override
            public void onItemClick(View view, int position, LawyerCommentBean model) {

            }

            @Override
            public void onItemLongClick(View view, int position, LawyerCommentBean model) {

            }
        });

    }

    @OnClick({R.id.tv_submit, R.id.iv_header, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选定律师
            case R.id.tv_submit:
                if (mDetailBean == null) {
                    ToastUtils.show(mContext, "网络异常，请稍后再试...");
                    getDetail();
                    return;
                }
                selectLawyer();
                break;
            //头像
            case R.id.iv_header:
                if (mDetailBean == null) {
                    ToastUtils.show(mContext, "网络异常，请稍后再试...");
                    getDetail();
                    return;
                }
                List<String> headerList = new ArrayList<>();
                headerList.add(mDetailBean.getHead_img());
                Intent intent = new Intent(mContext, EnlargePhotoActivity.class);
                intent.putExtra(Constants.EXTRA_ENLARGE_INDEX, 0);
                intent.putExtra(Constants.EXTRA_ENLARGE_PHOTO, (Serializable) headerList);
                mContext.startActivity(intent);
                break;
            //更多案例
            case R.id.tv_more:
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + mId);
                MyApplication.openActivity(mContext, LawyerCaseActivity.class, bundle);
                break;
        }
    }

    private void selectLawyer() {
        Map<String, Object> params = new HashMap<>();
        params.put("order_sn", "" + mOrderNum);
        params.put("lawyer_id", "" + mId);
        HttpUtils.selectLawyer(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", mDetailBean);
                MyApplication.openActivity(mContext, SelectSuccessActivity.class, bundle);
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
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            getDetail();
            getEvaluate();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getEvaluate();
        });
    }

    private void createOrder(String mTypeId) {
        Map<String, Object> params = new HashMap<>();
        mJumpBean.getOrderInfo().put("son_id", "" + mTypeId);
        mJumpBean.getOrderInfo().put("lawyer_id", "" + mId);
        params.put("order_type", "1");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(mJumpBean.getOrderInfo()));
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                String status = JSONUtils.getNoteJson(response, "pay_status");
                if ("1".equals(status)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 0);
                    MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
                    finish();
                    return;
                }
                Bundle bundle = new Bundle();
//                if ("65".equals(mClassifyId)) {
//                    getOrderDetail(orderNum);
//                    return;
//                }
                AppManager.getInstance().finishActivity(SearchActivity.class);
                AppManager.getInstance().finishActivity(OrderAppointActivity.class);
                AppManager.getInstance().finishActivity(OrderPromptActivity.class);
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
                finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
