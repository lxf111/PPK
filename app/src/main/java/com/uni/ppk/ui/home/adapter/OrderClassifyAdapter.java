package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.home.bean.OrderClassifyBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 17:23
 */
public class OrderClassifyAdapter extends AFinalRecyclerViewAdapter<OrderClassifyBean> {

    public OrderClassifyAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new OrderClassifyViewHolder(mInflater.inflate(R.layout.item_order_classify, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((OrderClassifyViewHolder) holder).setContent(getItem(position), position);
    }

    protected class OrderClassifyViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.rlyt_item)
        RelativeLayout rlytItem;

        public OrderClassifyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(OrderClassifyBean mBean, int position) {
            if (mBean.isSelect()) {
                rlytItem.setBackgroundResource(R.drawable.shape_8radius_f6f6f6);
                ivSelect.setVisibility(View.VISIBLE);
            } else {
                rlytItem.setBackgroundResource(R.color.white);
                ivSelect.setVisibility(View.INVISIBLE);
            }

            if(mBean.isOther()){
                ivImg.setImageResource(R.mipmap.ic_prompt_othem);
            }else{
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getImage()), ivImg, mContext, R.mipmap.ic_default_wide);
            }
            tvTitle.setText("" + mBean.getName());
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
