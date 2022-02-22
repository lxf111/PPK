package com.uni.ppk.pop.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.VipListBean;
import com.uni.ppk.utils.ArithUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 9:29
 */
public class SelectVipAdapter extends AFinalRecyclerViewAdapter<VipListBean> {

    public SelectVipAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new SelectVipViewHolder(mInflater.inflate(R.layout.item_select_vip, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((SelectVipViewHolder) holder).setContent(getItem(position), position);
    }

    protected class SelectVipViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public SelectVipViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(VipListBean mBean, int position) {
            if (mBean.isSelect()) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_agree_selected);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvTitle.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_agree_no_select);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvTitle.setCompoundDrawables(drawable, null, null, null);
            }
            tvTitle.setText("" + mBean.getName());
            tvPrice.setText("¥" + ArithUtils.saveSecond(mBean.getPrice()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mBean.isSelect()) {
                        mBean.setSelect(false);
                    } else {
                        for (int i = 0; i < getList().size(); i++) {
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
