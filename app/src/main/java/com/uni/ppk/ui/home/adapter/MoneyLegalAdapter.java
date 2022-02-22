package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.MoneyLegalBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 11:33
 */
public class MoneyLegalAdapter extends AFinalRecyclerViewAdapter<MoneyLegalBean> {

    public MoneyLegalAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MoneyViewHolder(mInflater.inflate(R.layout.item_money_legal, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((MoneyViewHolder) holder).setContent(getItem(position), position);
    }

    protected class MoneyViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public MoneyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(MoneyLegalBean mBean, int position) {
            if (mBean.isSelect()) {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvContent.setBackgroundResource(R.drawable.shape_border_theme_radius2);
            } else {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvContent.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);
            }
            tvContent.setText("" + mBean.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mBean.isSelect()) {
                        mBean.setSelect(false);
                    } else {
                        for (int i = 0; i < getItemCount(); i++) {
                            getList().get(i).setSelect(false);
                        }
                        mBean.setSelect(true);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
