package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.mine.bean.RecommendBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 11:41
 */
public class RecommendAdapter extends AFinalRecyclerViewAdapter<RecommendBean> {

    public RecommendAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(mInflater.inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((RecommendViewHolder) holder).setContent(getItem(position), position);
    }

    protected class RecommendViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_name)
        TextView tvName;

        public RecommendViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(RecommendBean mBean, int position) {

            tvName.setText("" + mBean.getUser_name());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
            tvTime.setText("" + mBean.getCreate_time());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mBean);
                    }
                }
            });
        }
    }
}
