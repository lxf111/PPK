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
import com.uni.ppk.ui.home.bean.AdviserBean;
import com.uni.ppk.utils.OrderAgreeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 14:27
 */
public class AdviseAdapter extends AFinalRecyclerViewAdapter<AdviserBean> {

    public AdviseAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new AdviseViewHolder(mInflater.inflate(R.layout.item_advise, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((AdviseViewHolder) holder).setContent(getItem(position), position);
    }

    protected class AdviseViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rlyt_item)
        RelativeLayout rlytItem;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.iv_down)
        ImageView ivDown;

        public AdviseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(AdviserBean mBean, int position) {
            if (mBean.isSelect()) {
                tvTitle.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvContent.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvPrice.setTextColor(mContext.getResources().getColor(R.color.theme));
                rlytItem.setBackgroundResource(R.drawable.shape_border_theme_radius8);
            } else {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvTitle.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                rlytItem.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius8);
            }

            tvContent.setText("" + mBean.getContent());
            tvPrice.setText("¥" + OrderAgreeUtils.showPrice(mActivity, mBean.getMember_price(), mBean.getPrice()));
            tvTitle.setText("" + mBean.getTitle());

            ivDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBean.setShow(!mBean.isShow());
                    notifyDataSetChanged();
                }
            });
            if (mBean.isShow()) {
                ivDown.setImageResource(R.mipmap.ic_add_address_up);
                tvContent.setMaxLines(10000);
            } else {
                ivDown.setImageResource(R.mipmap.ic_add_address_down);
                tvContent.setMaxLines(3);
            }

            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getBackground()), ivImg, mContext, R.mipmap.ic_vip1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mBean.isSelect()) {
                        mBean.setSelect(false);
                    } else {
                        for (int i = 0; i < getItemCount(); i++) {
                            getList().get(i).setSelect(false);
                        }
                        mBean.setSelect(true);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
