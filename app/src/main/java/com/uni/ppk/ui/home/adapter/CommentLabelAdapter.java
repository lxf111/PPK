package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.CommentLabelBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 16:25
 */
public class CommentLabelAdapter extends AFinalRecyclerViewAdapter<CommentLabelBean> {

    public CommentLabelAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommentLabelViewHolder(mInflater.inflate(R.layout.item_comment_label, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommentLabelViewHolder) holder).setContent(getItem(position), position);
    }

    protected class CommentLabelViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_label)
        TextView tvLabel;

        public CommentLabelViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(CommentLabelBean mBean, int position) {
            tvLabel.setText("" + mBean.getName());
        }
    }

}
