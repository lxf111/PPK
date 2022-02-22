package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.SelectMoneyBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 11:33
 */
public class SelectMoneyAdapter extends AFinalRecyclerViewAdapter<SelectMoneyBean> {

    public SelectMoneyAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new MoneyViewHolder(mInflater.inflate(R.layout.item_select_money, parent, false));
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

        private void setContent(SelectMoneyBean mBean, int position) {
            tvContent.setText("" + mBean.getPrice());
            if (mBean.isSelect()) {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvContent.setBackgroundResource(R.drawable.shape_border_theme_radius2);
            } else {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvContent.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);
            }
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
