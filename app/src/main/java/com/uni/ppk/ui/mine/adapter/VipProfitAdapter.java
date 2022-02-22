package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.mine.bean.VipProfitBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/17
 * Time: 15:45
 */
public class VipProfitAdapter extends AFinalRecyclerViewAdapter<VipProfitBean> {

    public VipProfitAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ProfitViewHolder(mInflater.inflate(R.layout.item_vip_profit, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ProfitViewHolder) holder).setContent(getItem(position), position);
    }

    protected class ProfitViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ProfitViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(VipProfitBean mBean, int position) {
            tvTitle.setText("特权" + (position + 1));
            tvContent.setText("" + mBean.getName());
        }
    }
}
