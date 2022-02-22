package com.uni.ppk.pop.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.pop.bean.CommonSelectBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/11/6
 * Time: 11:59
 */
public class CommonSelectAdapter extends AFinalRecyclerViewAdapter<CommonSelectBean> {

    public CommonSelectAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CancelOrderViewHolder(mInflater.inflate(R.layout.item_common_select, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CancelOrderViewHolder) holder).setContent(getItem(position), position);
    }

    protected class CancelOrderViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public CancelOrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(CommonSelectBean mBean, int position) {
            tvContent.setText("" + mBean.getName());
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
