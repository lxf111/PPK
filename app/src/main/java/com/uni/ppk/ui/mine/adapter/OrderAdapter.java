package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.mine.bean.OrderBean;
import com.uni.ppk.utils.ArithUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:08
 */
public class OrderAdapter extends AFinalRecyclerViewAdapter<OrderBean> {

    public OrderAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(mInflater.inflate(R.layout.item_order, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((OrderViewHolder) holder).setContent(getItem(position), position);
    }

    protected class OrderViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.llyt_price)
        LinearLayout llytPrice;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(OrderBean mBean, int position) {
            tvName.setText("" + mBean.getTitle());
            tvContent.setText("" + mBean.getContent());
            tvTime.setText("" + mBean.getCreate_time());
            tvPrice.setText("" + ArithUtils.saveSecond(mBean.getOrder_money()));
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
