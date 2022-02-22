package com.uni.ppk.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.CityBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 城市搜索和热门
 * @Author: longyu
 * @CreateDate: 2020/1/20 0020$ 11:13$
 * @Version: 1.0
 */
public class CityLabelAdapter extends AFinalRecyclerViewAdapter<CityBean> {

    public CityLabelAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_city_select_label, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setData(position, getItem(position));
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_city_label)
        TextView tvCityLabel;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position, CityBean item) {
            if (item.getName().indexOf(",") != -1) {
                tvCityLabel.setText(item.getName().substring(0, item.getName().indexOf(",")));
            } else {
                tvCityLabel.setText(item.getName());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position, item);
                    }
                }
            });
        }
    }

}
