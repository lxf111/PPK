package com.uni.ppk.ui.home.adapter;

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
import com.uni.ppk.ui.home.bean.SearchLawyerBean;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 17:27
 */
public class SearchLawyerAdapter extends AFinalRecyclerViewAdapter<SearchLawyerBean> {

    public SearchLawyerAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(mInflater.inflate(R.layout.item_search_lawyer, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((SearchViewHolder) holder).setContent(getItem(position), position);
    }

    protected class SearchViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.rlv_label)
        CustomRecyclerView rlvLabel;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_service)
        TextView tvService;

        public SearchViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(SearchLawyerBean mBean, int position) {
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
            tvName.setText("" + mBean.getUser_name());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
            tvScore.setText("" + mBean.getScore() + "分");
            tvService.setText("服务" + mBean.getCase_num() + "人");
            tvPrice.setText("¥" + mBean.getPrice() + "/时");
            tvAddress.setText("" + mBean.getCity() + " | " + mBean.getLaw_firm());
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
