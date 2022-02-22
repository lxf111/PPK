package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.mine.bean.EvaluateLabelBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/28
 * Time: 18:11
 */
public class EvaluateLabelAdapter extends AFinalRecyclerViewAdapter<EvaluateLabelBean> {
    public EvaluateLabelAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateLabelViewHolder(mInflater.inflate(R.layout.item_evaluate_label, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((EvaluateLabelViewHolder) holder).setContent(getItem(position), position);
    }

    protected class EvaluateLabelViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;

        public EvaluateLabelViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(EvaluateLabelBean mBean, int position) {
            tvContent.setText("" + mBean.getName());
            if (mBean.isSelect()) {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvContent.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);
            } else {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvContent.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBean.setSelect(!mBean.isSelect());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
