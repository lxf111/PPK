package com.uni.ppk.ui.community.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.community.adapter.EvaluateAdapter;
import com.uni.ppk.ui.community.bean.CommunityDetailBean;
import com.uni.ppk.ui.community.bean.EvaluateBean;
import com.uni.ppk.ui.home.adapter.PhotoAdapter;
import com.uni.ppk.ui.home.bean.UserViewInfo;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.ninegrid.NineGridTestLayout;
import com.previewlibrary.GPreviewBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
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
 * Date: 2020/9/15
 * Time: 11:37
 * 社群详情
 */
public class CommunityDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.rlyt_bottom)
    RelativeLayout rlytBottom;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rlv_photo)
    CustomRecyclerView rlvPhoto;
    @BindView(R.id.tv_report)
    TextView tvReport;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.iv_praise)
    ImageView ivPraise;
    @BindView(R.id.tv_praise_num)
    TextView tvPraiseNum;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.tv_repose_num)
    TextView tvReposeNum;
    @BindView(R.id.rlv_list)
    CustomRecyclerView rlvList;
    @BindView(R.id.ninegridview)
    NineGridTestLayout ninegridview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_concern)
    TextView tvConcern;
    @BindView(R.id.iv_reply_header)
    CircleImageView ivReplyHeader;
    @BindView(R.id.tv_reply_name)
    TextView tvReplyName;
    @BindView(R.id.tv_reply_label)
    TextView tvReplyLabel;
    @BindView(R.id.tv_reply_time)
    TextView tvReplyTime;
    @BindView(R.id.llyt_reply)
    LinearLayout llytReply;
    @BindView(R.id.tv_reply_content)
    TextView tvReplyContent;
    @BindView(R.id.rlyt_reply)
    RelativeLayout rlytReply;

    private EvaluateAdapter mAdapter;
    private PhotoAdapter mPhotoAdapter;

    private String mId = "";//
    private int mPage = 1;

    private CommunityDetailBean mDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_detail;
    }

    @Override
    protected void initData() {
        mId = getIntent().getStringExtra("id");
        initTitle("详情");

//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_community_share);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        rightTitle.setCompoundDrawables(drawable, null, null, null);

        //评论列表
        rlvList.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new EvaluateAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailBean == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("uid", "" + mDetailBean.getUser_id());
                bundle.putString("userType", "" + mDetailBean.getUser_type());
                MyApplication.openActivity(mContext, OtherInfoActivity.class, bundle);
            }
        });

        edtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //关闭软键盘
                    SoftInputUtils.hideSoftInput(mContext);
                    comment();
                    return true;
                }
                return false;
            }
        });
        initRefreshLayout();
        //获取详情
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        HttpUtils.communityDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                CommunityDetailBean detailBean = JSONUtils.jsonString2Bean(response, CommunityDetailBean.class);
                if (detailBean != null) {
                    mDetailBean = detailBean;
                    //1男 2女
                    if (detailBean.getSex() == 1) {
                        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_sex_man);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvName.setCompoundDrawables(null, null, drawable, null);
                    } else if (detailBean.getSex() == 2) {
                        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_sex_woman);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tvName.setCompoundDrawables(null, null, drawable, null);
                    } else {
                        tvName.setCompoundDrawables(null, null, null, null);
                    }
                    if (mDetailBean.getAnswer() != null && mDetailBean.getIs_answer() == 1) {
                        rlytReply.setVisibility(View.VISIBLE);
                        ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDetailBean.getAnswer().getHead_img()), ivReplyHeader, mContext, R.mipmap.ic_default_header);
                        tvReplyName.setText("" + mDetailBean.getAnswer().getUser_nickname());
                        tvReplyTime.setText("" + mDetailBean.getAnswer().getUpdate_time());
                        tvReplyContent.setText("" + mDetailBean.getAnswer().getContent());
                    } else {
                        rlytReply.setVisibility(View.GONE);
                    }
                    tvTitle.setText("" + mDetailBean.getTitle());
                    tvName.setText("" + detailBean.getUser_nickname());
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(detailBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                    tvContent.setText("" + detailBean.getContent());
                    tvTime.setText("" + detailBean.getCreate_time());
                    tvLabel.setVisibility(View.GONE);
                    tvReposeNum.setText("（" + detailBean.getComments_count() + "）");
                    tvPraiseNum.setText("" + detailBean.getGoods_count());
                    if (detailBean.getIs_goods() == 1) {
                        //已点赞
                        ivPraise.setImageResource(R.mipmap.ic_community_praised);
                    } else {
                        //未点赞
                        ivPraise.setImageResource(R.mipmap.ic_community_praise);
                    }

                    if (detailBean.getPictures() != null &&
                            detailBean.getPictures().size() > 0) {
                        //图片
                        ninegridview.setUrlList(detailBean.getPictures());
                        ninegridview.setOnImageLoaderListener(new NineGridTestLayout.ImageLoaderListener() {
                            @Override
                            public void onLoadImgList(int position, List<String> urlList, List<View> imageViews) {
                                ArrayList<UserViewInfo> userViewInfos = new ArrayList<>();
                                for (int i = 0; i < detailBean.getPictures().size(); i++) {
                                    Rect rect = new Rect();
                                    UserViewInfo userViewInfo = new UserViewInfo(detailBean.getPictures().get(i));
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
                        });
                        ninegridview.setVisibility(View.VISIBLE);
                    } else {
                        ninegridview.setVisibility(View.GONE);
                    }
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

    @Override
    protected void onResume() {
        super.onResume();
        //获取评论列表
        mPage = 1;
        getComment();
    }

    private void getComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("page", "" + mPage);
        params.put("page_size", "" + Constants.PAGE_SIZE);
        HttpUtils.commentList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<EvaluateBean> listBeans = JSONUtils.jsonString2Beans(response, EvaluateBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mAdapter.refreshList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
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
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    @OnClick({R.id.tv_submit, R.id.rlyt_reply, R.id.right_title, R.id.iv_praise, R.id.tv_praise_num, R.id.tv_concern})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_title:
//                SharePopup mSharePopup = new SharePopup(mContext);
//                mSharePopup.showAtLocation(tvSubmit, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rlyt_reply:
                if (mDetailBean == null || mDetailBean.getIs_answer() != 1 || mDetailBean.getAnswer() == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("uid", "" + mDetailBean.getAnswer().getLawyer_id());
                bundle.putString("userType", "2");
                MyApplication.openActivity(mContext, OtherInfoActivity.class, bundle);
                break;
            //评论
            case R.id.tv_submit:
                //关闭软键盘
                SoftInputUtils.hideSoftInput(mContext);
                comment();
                break;
            case R.id.tv_concern:
                break;
            //点赞
            case R.id.iv_praise:
            case R.id.tv_praise_num:
                praise();
                break;
        }
    }

    //点赞
    private void praise() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("type", "" + 1);
        HttpUtils.communityPraise(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                RxBus.getInstance().post("refreshCommunity");
                getData();
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

    //评论
    private void comment() {
        String content = edtContent.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(mContext, "请输入评论内容");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("pid", "" + 0);
        params.put("content", "" + content);
        HttpUtils.communityComment(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                edtContent.setText("");
                mPage = 1;
                RxBus.getInstance().post("refreshCommunity");
                getComment();
                getData();
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
            getComment();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getComment();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
