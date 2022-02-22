package com.uni.ppk.ui.community.activity;

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
import com.uni.ppk.ui.community.adapter.ReplyAdapter;
import com.uni.ppk.ui.community.bean.ReplyDetailBean;
import com.uni.ppk.widget.CustomRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 14:54
 * 回复详情
 */
public class ReplyDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rlv_reply)
    CustomRecyclerView rlvReply;
    @BindView(R.id.llyt_reply)
    LinearLayout llytReply;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private int mPage = 1;
    private String mId = "";//评论id
    private String mFirstId = "";//帖子id
    private ReplyAdapter mAdapter;

    private ReplyDetailBean mDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reply_detail;
    }

    @Override
    protected void initData() {
        initTitle("评论详情");

        mFirstId = getIntent().getStringExtra("firstId");
        mId = getIntent().getStringExtra("id");

        rlvReply.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new ReplyAdapter(mContext);
        rlvReply.setAdapter(mAdapter);

        initRefreshLayout();
        getData();
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
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        HttpUtils.reposeList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                refreshLayout.finishRefresh();
                ReplyDetailBean detailBean = JSONUtils.jsonString2Bean(response, ReplyDetailBean.class);
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

                    tvTime.setText("" + detailBean.getCreate_time());
                    tvContent.setText("" + detailBean.getContent());
                    tvName.setText("" + detailBean.getUser_nickname());
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(detailBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                    if (detailBean.getReply() != null && detailBean.getReply().size() > 0) {
                        mAdapter.refreshList(detailBean.getReply());
                        rlvReply.setVisibility(View.VISIBLE);
                    } else {
                        rlvReply.setVisibility(View.GONE);
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

    private void initRefreshLayout() {
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

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        //提交
        //关闭软键盘
        SoftInputUtils.hideSoftInput(mContext);
        comment();
    }

    private void comment() {
        String content = edtContent.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(mContext, "请输入评论内容");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mFirstId);
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("pid", "" + mId);
        params.put("content", "" + content);
        HttpUtils.communityComment(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                edtContent.setText("");
                mPage = 1;
                getData();
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

}
