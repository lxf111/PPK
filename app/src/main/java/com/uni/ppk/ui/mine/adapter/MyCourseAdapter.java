package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.mine.bean.MyCourseBean;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CustomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:26
 */
public class MyCourseAdapter extends AFinalRecyclerViewAdapter<MyCourseBean> {

    public MyCourseAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CourseViewHolder(mInflater.inflate(R.layout.item_my_course, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CourseViewHolder) holder).setContent(getItem(position), position);
    }

    protected class CourseViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CustomImageView ivImg;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public CourseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(MyCourseBean mBean, int position) {
            if (mBean.getSubject() != null) {
                tvName.setText("" + mBean.getSubject().getTitle());
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getSubject().getCover()), ivImg, mContext, R.mipmap.ic_default_wide);
                tvNumber.setText("" + ArithUtils.showNumber(mBean.getSubject().getLearning()) + "人学习");
                try {
                    //1公益课2收费课
                    if (mBean.getSubject().getType() == 1) {
                        tvPrice.setText("" + mContext.getResources().getString(R.string.class_type));
                        tvUnit.setVisibility(View.GONE);
                    } else {
                        if (Double.parseDouble(mBean.getSubject().getPrice()) == 0) {
                            tvPrice.setText("" + mContext.getResources().getString(R.string.class_type));
                            tvUnit.setVisibility(View.GONE);
                        } else {
                            tvUnit.setVisibility(View.VISIBLE);
                            tvPrice.setText("" + ArithUtils.saveSecond(mBean.getSubject().getPrice()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    tvUnit.setVisibility(View.VISIBLE);
                    tvPrice.setText("" + mBean.getSubject().getPrice());
                }
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
