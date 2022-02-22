package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.AddressListBean;
import com.uni.ppk.widget.CustomRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/16
 * Time: 19:18
 */
public class AddressListAdapter extends AFinalRecyclerViewAdapter<AddressListBean> {

    public AddressListAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new AddressListViewHolder(mInflater.inflate(R.layout.item_address_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((AddressListViewHolder) holder).setContent(getItem(position), position);
    }

    protected class AddressListViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_pinyin)
        TextView tvPinyin;
        @BindView(R.id.rlv_list)
        CustomRecyclerView rlvList;
        @BindView(R.id.tv_city)
        TextView tvCity;

        public AddressListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(AddressListBean mBean, int position) {
            tvPinyin.setText("" + mBean.getPinyin());
            rlvList.setLayoutManager(new LinearLayoutManager(mActivity) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            AddressCityAdapter adapter = new AddressCityAdapter(mActivity);
            rlvList.setAdapter(adapter);
            adapter.refreshList(mBean.getCitys());
            adapter.setOnItemClickListener(new OnItemClickListener<AddressListBean.CitysBean>() {
                @Override
                public void onItemClick(View view, int p, AddressListBean.CitysBean model) {
                    onAddressCityOnclick.setOnCityCallback(model, position, p);
                }

                @Override
                public void onItemLongClick(View view, int position, AddressListBean.CitysBean model) {

                }
            });
        }
    }

    public interface OnAddressCityOnclick {
        void setOnCityCallback(AddressListBean.CitysBean mbea, int firstPosition, int secondPosition);
    }

    private OnAddressCityOnclick onAddressCityOnclick;

    public void setOnAddressCityOnclick(OnAddressCityOnclick onAddressCityOnclick) {
        this.onAddressCityOnclick = onAddressCityOnclick;
    }

}
