package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.mine.bean.HelpBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 17:49
 */
public class HelpAdapter extends AFinalRecyclerViewAdapter<HelpBean> {

    public HelpAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new HelpViewHolder(mInflater.inflate(R.layout.item_help, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((HelpViewHolder) holder).setContent(getItem(position), position);
    }

    protected class HelpViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public HelpViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(HelpBean mBean, int position) {
            tvTitle.setText("" + mBean.getTitle());
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
