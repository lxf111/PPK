package com.uni.ppk.ui.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.CityListBean;
import com.uni.ppk.utils.PinyinUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description: 城市列表
 * @Author: longyu
 * @CreateDate: 2020/1/20 0020$ 11:30$
 * @Version: 1.0
 */
public class CityListAdapater extends AFinalRecyclerViewAdapter<CityListBean.ChildrenBean> {

    public CityListAdapater(Context ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_location_city_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).setData(position);
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_pinyin)
        TextView tvPinyin;
        @BindView(R.id.tv_city)
        TextView tvCity;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            tvCity.setText("" + getItem(position).getName());
            String currentLetter = PinyinUtils.getFirstLetter(getItem(position).getPinyin());
            String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(getItem(position - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                tvPinyin.setVisibility(View.VISIBLE);
            } else {
                tvPinyin.setVisibility(View.GONE);
            }
            tvPinyin.setText(currentLetter);
            tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position, getItem(position));
                    }
                }
            });
        }
    }

}
