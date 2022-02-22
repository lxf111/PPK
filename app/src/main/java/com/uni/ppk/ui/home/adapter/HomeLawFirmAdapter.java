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
import com.uni.ppk.ui.home.bean.HomeLawFirmBean;
import com.uni.ppk.widget.CustomImageView55;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:24
 */
public class HomeLawFirmAdapter extends AFinalRecyclerViewAdapter<HomeLawFirmBean> {

    public HomeLawFirmAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new LawFirmViewHolder(mInflater.inflate(R.layout.item_home_law_firm, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((LawFirmViewHolder) holder).setContent(getItem(position), position);
    }

    protected class LawFirmViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CustomImageView55 ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rlv_label)
        CustomRecyclerView rlvLabel;

        public LawFirmViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(HomeLawFirmBean mBean, int position) {
            rlvLabel.setLayoutManager(new FlowLayoutManager() {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            LabelAdapter mLabelAdapter = new LabelAdapter(mActivity);
            rlvLabel.setAdapter(mLabelAdapter);
            if (mBean.getService() != null && mBean.getService().size() > 0) {
                mLabelAdapter.refreshList(mBean.getService());
            } else {
                mLabelAdapter.clear();
            }
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getImage()), ivImg, mActivity, R.mipmap.ic_default_wide);
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
