package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.HomeClassifyBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 15:17
 */
public class HomeClassifyAdapter extends AFinalRecyclerViewAdapter<HomeClassifyBean> {

    public HomeClassifyAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new HomeClassifyViewHolder(mInflater.inflate(R.layout.item_home_classify, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((HomeClassifyViewHolder) holder).setContent(getItem(position), position);
    }

    protected class HomeClassifyViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public HomeClassifyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(HomeClassifyBean mBean, int position) {
            Drawable drawable = mContext.getResources().getDrawable(mBean.getImg());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvTitle.setCompoundDrawables(null, drawable, null, null);
            tvTitle.setText("" + mBean.getName());
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
