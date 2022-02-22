package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.mine.bean.ProfitBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/24
 * Time: 17:22
 */
public class ProfitAdapter extends AFinalRecyclerViewAdapter<ProfitBean> {

    public ProfitAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ProfitListViewHolder(mInflater.inflate(R.layout.item_profit_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ProfitListViewHolder) holder).setContent(getItem(position), position);
    }

    protected class ProfitListViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CircleImageView ivImg;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_profit)
        TextView tvProfit;

        public ProfitListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(ProfitBean mBean, int position) {
            if(mBean.getFrom()!=null){
                tvName.setText("" + mBean.getFrom().getUser_nickname());
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getFrom().getHead_img()), ivImg, mContext, R.mipmap.ic_default_header);
            }
            tvTime.setText("" + mBean.getCreate_time());
            tvProfit.setText("收益：¥" + mBean.getRebate_money());
            tvPrice.setText(mBean.getRemark() + "：¥" + mBean.getMoney());
        }
    }
}
