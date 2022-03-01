package com.uni.ppk.ui.human.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.human.bean.HumanClassifyBean;

import butterknife.ButterKnife;

/**
 * @ClassName HumanClassifyAdapter
 * @Description TODO
 * @Author LXF
 * @Date 2022/2/28 20:54
 * @Version 1.0
 */
public class HumanClassifyAdapter extends AFinalRecyclerViewAdapter<HumanClassifyBean> {
    public HumanClassifyAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new HumanClassifyViewHolder(mInflater.inflate(R.layout.item_human_classify,parent,false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((HumanClassifyViewHolder) holder).setContent(position, getItem(position));
    }

    protected class HumanClassifyViewHolder extends BaseRecyclerViewHolder {
        public HumanClassifyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(int position, HumanClassifyBean mBean) {

        }
    }

}
