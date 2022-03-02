package com.uni.ppk.pop.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.pop.bean.TypeLeftBean;

import butterknife.ButterKnife;

/**
 * @ClassName TypeLeftAdapter
 * @Description TODO
 * @Author LXF
 * @Date 2022/3/2 21:36
 * @Version 1.0
 */
public class TypeLeftAdapter extends AFinalRecyclerViewAdapter<TypeLeftBean> {
    public TypeLeftAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {

    }

    protected class TypeLeftViewHolder extends BaseRecyclerViewHolder {
        public TypeLeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(int position, TypeLeftBean mBean) {

        }
    }
}
