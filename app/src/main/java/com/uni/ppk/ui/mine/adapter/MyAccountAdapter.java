package com.uni.ppk.ui.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.ui.mine.bean.AccountListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAccountAdapter extends BaseAdapter {

    private Context mContext;
    private List<AccountListBean.BillListBean> mBeans;

    private ViewHolder mHolder = null;

    public MyAccountAdapter(Context mContext, List<AccountListBean.BillListBean> mBeans) {
        this.mContext = mContext;
        this.mBeans = mBeans;
    }

    @Override
    public int getCount() {
        return mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_my_account, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.tvTitle.setText("" + mBeans.get(position).getBal_title());
        mHolder.tvTime.setText("" + mBeans.get(position).getChange_time());
        mHolder.tvMoney.setText("" + mBeans.get(position).getChange_money());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
