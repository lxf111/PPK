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
import com.uni.ppk.ui.home.bean.HomeLawyerBean;
import com.uni.ppk.widget.CustomImageView85;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:42
 */
public class HomeLawyerAdapter extends AFinalRecyclerViewAdapter<HomeLawyerBean> {

    public HomeLawyerAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new LawyerViewHolder(mInflater.inflate(R.layout.item_home_lawyer, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((LawyerViewHolder) holder).setContent(getItem(position), position);
    }

    protected class LawyerViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CustomImageView85 ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.rlv_label)
        CustomRecyclerView rlvLabel;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public LawyerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(HomeLawyerBean mBean, int position) {
            tvName.setText("" + mBean.getUser_name());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivImg, mActivity, R.mipmap.ic_default_wide);
            tvAddress.setText("" + mBean.getCity() + " | " + mBean.getLaw_firm());
            rlvLabel.setLayoutManager(new FlowLayoutManager() {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            LabelAdapter mLabelAdapter = new LabelAdapter(mActivity);
            rlvLabel.setAdapter(mLabelAdapter);
            mLabelAdapter.refreshList(mBean.getService());
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

    private static int random2() {
        return (int) (Math.random() * 20);
    }


}
