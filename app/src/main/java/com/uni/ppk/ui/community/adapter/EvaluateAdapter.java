package com.uni.ppk.ui.community.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.community.activity.OtherInfoActivity;
import com.uni.ppk.ui.community.activity.ReplyDetailActivity;
import com.uni.ppk.ui.community.bean.EvaluateBean;
import com.uni.ppk.ui.home.activity.VideoReplyActivity;
import com.uni.ppk.widget.CustomRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 14:16
 */
public class EvaluateAdapter extends AFinalRecyclerViewAdapter<EvaluateBean> {

    public EvaluateAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(mInflater.inflate(R.layout.item_evaluate, parent, false));
    }

    private int mJumpType = 1;//1社群 2视频

    public void setmJumpType(int mJumpType) {
        this.mJumpType = mJumpType;
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((EvaluateViewHolder) holder).setContent(getItem(position), position);
    }

    protected class EvaluateViewHolder extends BaseRecyclerViewHolder {
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
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.llyt_reply)
        LinearLayout llytReply;

        public EvaluateViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(EvaluateBean mBean, int position) {
            //1男 2女
            if (mBean.getSex() == 1) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_sex_man);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvName.setCompoundDrawables(null, null, drawable, null);
            } else if (mBean.getSex() == 2) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_sex_woman);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvName.setCompoundDrawables(null, null, drawable, null);
            } else {
                tvName.setCompoundDrawables(null, null, null, null);
            }
            tvTime.setText("" + mBean.getCreate_time());
            tvContent.setText("" + mBean.getContent());
            tvName.setText("" + mBean.getUser_nickname());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);

            rlvReply.setLayoutManager(new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            tvReply.setVisibility(View.GONE);
            ReplyAdapter mAdapter = new ReplyAdapter(mActivity);
            rlvReply.setAdapter(mAdapter);
            if (mBean.getReply() != null && mBean.getReply().size() > 0) {
                if (mBean.getReply().size() > 2) {
                    mAdapter.refreshList(mBean.getReply().subList(0, 2));
                    tvReply.setVisibility(View.VISIBLE);
                    tvReply.setText("查看全部" + mBean.getReply().size() + "条回复");
                } else {
                    mAdapter.refreshList(mBean.getReply());
                }
            } else {
                mAdapter.clear();
            }

            ivHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", "" + mBean.getUser_id());
                    bundle.putString("userType", "" + mBean.getUser_type());
                    MyApplication.openActivity(mContext, OtherInfoActivity.class, bundle);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", "" + mBean.getId());
                    bundle.putString("firstId", "" + mBean.getAssociate_id());
                    if (mJumpType == 1) {
                        MyApplication.openActivity(mContext, ReplyDetailActivity.class, bundle);
                    } else {
                        MyApplication.openActivity(mContext, VideoReplyActivity.class, bundle);
                    }
                }
            });
        }
    }
}
