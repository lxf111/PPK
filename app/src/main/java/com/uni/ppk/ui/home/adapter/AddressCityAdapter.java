package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.AddressListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/16
 * Time: 19:18
 */
public class AddressCityAdapter extends AFinalRecyclerViewAdapter<AddressListBean.CitysBean> {

    public AddressCityAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new AddressListViewHolder(mInflater.inflate(R.layout.item_address_city, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((AddressListViewHolder) holder).setContent(getItem(position), position);
    }

    protected class AddressListViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_pinyin)
        TextView tvPinyin;
        @BindView(R.id.tv_city)
        TextView tvCity;

        public AddressListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(AddressListBean.CitysBean mBean, int position) {
            tvCity.setText("" + mBean.getName());
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
