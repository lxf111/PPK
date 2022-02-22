package com.uni.ppk.ui.community.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.community.activity.OtherInfoActivity;
import com.uni.ppk.ui.community.bean.ReplyBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 14:28
 */
public class ReplyAdapter extends AFinalRecyclerViewAdapter<ReplyBean> {

    public ReplyAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ReplyViewHolder(mInflater.inflate(R.layout.item_reply, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ReplyViewHolder) holder).setContent(getItem(position), position);
    }

    protected class ReplyViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ReplyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(ReplyBean mBean, int position) {
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
            tvContent.setText("" + mBean.getContent());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
            tvName.setText("" + mBean.getUser_nickname());
            tvTime.setText("" + mBean.getCreate_time());

            ivHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", "" + mBean.getUser_id());
                    bundle.putString("userType", "" + mBean.getUser_type());
                    MyApplication.openActivity(mContext, OtherInfoActivity.class, bundle);
                }
            });
        }
    }
}
