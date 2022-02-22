package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.ServiceTypeBean;
import com.uni.ppk.utils.ArithUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 15:43
 * 服务类型
 */
public class ServiceTypeAdapter extends AFinalRecyclerViewAdapter<ServiceTypeBean> {

    public ServiceTypeAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ServiceTypeViewHolder(mInflater.inflate(R.layout.item_service_type, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ServiceTypeViewHolder) holder).setContent(getItem(position), position);
    }

    protected class ServiceTypeViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ServiceTypeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(ServiceTypeBean mBean, int position) {
            tvName.setText("" + mBean.getName());
            tvPrice.setText("¥" + ArithUtils.saveSecond(mBean.getPrice()));
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
