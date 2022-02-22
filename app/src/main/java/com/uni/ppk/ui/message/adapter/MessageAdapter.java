package com.uni.ppk.ui.message.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.message.bean.MessageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 16:21
 */
public class MessageAdapter extends AFinalRecyclerViewAdapter<MessageBean> {

    public MessageAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(mInflater.inflate(R.layout.item_message, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((MessageViewHolder) holder).setContent(getItem(position), position);
    }

    protected class MessageViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MessageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(MessageBean mBean, int position) {
            tvTitle.setText("" + mBean.getTitle());
            tvContent.setText("" + mBean.getContent());
            tvTime.setText("" + mBean.getCreate_time());
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
