package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.mine.bean.MyServiceBean;
import com.uni.ppk.utils.ArithUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:47
 */
public class MyServiceAdapter extends AFinalRecyclerViewAdapter<MyServiceBean> {

    public MyServiceAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MyServiceViewHolder(mInflater.inflate(R.layout.item_my_service, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((MyServiceViewHolder) holder).setContent(getItem(position), position);
    }

    protected class MyServiceViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.llyt_price)
        LinearLayout llytPrice;
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public MyServiceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(MyServiceBean mBean, int position) {

            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
            tvName.setText("" + mBean.getUser_nickname());
            tvTime.setText("" + mBean.getCreate_time());
            tvPrice.setText("" + ArithUtils.saveSecond(mBean.getReward()));
            tvTitle.setText("" + mBean.getTitle());
            tvContent.setText("" + mBean.getContent());

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
