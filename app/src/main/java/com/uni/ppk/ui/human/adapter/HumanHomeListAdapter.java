package com.uni.ppk.ui.human.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.human.bean.HumanHomeListBean;

import butterknife.ButterKnife;

/**
 * @ClassName HumanHomeListAdapter
 * @Description TODO
 * @Author LXF
 * @Date 2022/2/28 20:04
 * @Version 1.0
 */
public class HumanHomeListAdapter extends AFinalRecyclerViewAdapter<HumanHomeListBean> {
    public HumanHomeListAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new HumanHomeViewHolder(mInflater.inflate(R.layout.item_human_home_list,parent,false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((HumanHomeViewHolder) holder).setContent(position, getItem(position));
    }

    protected class HumanHomeViewHolder extends BaseRecyclerViewHolder {
        public HumanHomeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(int position, HumanHomeListBean mBean) {

        }
    }

}
