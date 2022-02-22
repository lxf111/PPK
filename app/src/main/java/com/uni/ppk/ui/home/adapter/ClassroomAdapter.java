package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.home.bean.ClassroomBean;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CustomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 14:35
 */
public class ClassroomAdapter extends AFinalRecyclerViewAdapter<ClassroomBean> {

    public ClassroomAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new ClassroomViewHolder(mInflater.inflate(R.layout.item_classroom, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ClassroomViewHolder) holder).setContent(getItem(position), position);
    }

    protected class ClassroomViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CustomImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ClassroomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(ClassroomBean mBean, int position) {
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getCover()), ivImg, mContext, R.mipmap.ic_default_wide);
            tvName.setText("" + mBean.getTitle());
            tvNumber.setText("" + ArithUtils.showNumber(mBean.getLearning()) + "人学习");
            try {
                //1公益课2收费课
                if (mBean.getType() == 1) {
                    tvPrice.setText("" + mContext.getResources().getString(R.string.class_type));
                    tvUnit.setVisibility(View.GONE);
                } else {
                    if (Double.parseDouble(mBean.getPrice()) == 0) {
                        tvPrice.setText("" + mContext.getResources().getString(R.string.class_type));
                        tvUnit.setVisibility(View.GONE);
                    } else {
                        tvUnit.setVisibility(View.VISIBLE);
                        tvPrice.setText("" + ArithUtils.saveSecond(mBean.getPrice()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                tvUnit.setVisibility(View.VISIBLE);
                tvPrice.setText("" + mBean.getPrice());
            }
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
