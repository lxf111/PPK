package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.LabelBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:34
 */
public class LabelAdapter extends AFinalRecyclerViewAdapter<LabelBean> {

    public LabelAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new LabelViewHolder(mInflater.inflate(R.layout.item_label, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((LabelViewHolder) holder).setContent(getItem(position), position);
    }

    protected class LabelViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public LabelViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(LabelBean mBean, int position) {
            tvTitle.setText("" + mBean.getName());
        }
    }
}
