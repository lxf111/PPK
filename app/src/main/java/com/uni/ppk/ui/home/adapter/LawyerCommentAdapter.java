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
import com.uni.ppk.ui.home.bean.CommentLabelBean;
import com.uni.ppk.ui.home.bean.LawyerCommentBean;
import com.uni.ppk.widget.CustomRatingBar;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 16:16
 */
public class LawyerCommentAdapter extends AFinalRecyclerViewAdapter<LawyerCommentBean> {

    public LawyerCommentAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(mInflater.inflate(R.layout.item_lawyer_comment, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommentViewHolder) holder).setContent(getItem(position), position);
    }

    protected class CommentViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ratingbar)
        CustomRatingBar ratingbar;
        @BindView(R.id.rlv_label)
        CustomRecyclerView rlvLabel;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(LawyerCommentBean mBean, int position) {
            ratingbar.setClickable(false);
            if (mBean.getUser() != null) {
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getUser().getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                tvName.setText("" + mBean.getUser().getUser_name());
            }
            ratingbar.setStar(mBean.getStar());
            tvContent.setText("" + mBean.getContent());
            tvTime.setText("" + mBean.getCreate_time());

            rlvLabel.setLayoutManager(new FlowLayoutManager() {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            CommentLabelAdapter mLabelAdapter = new CommentLabelAdapter(mActivity);
            rlvLabel.setAdapter(mLabelAdapter);
            if (mBean.getLabel() != null && mBean.getLabel().size() > 0) {
                List<CommentLabelBean> labelBeans = new ArrayList<>();
                for (int i = 0; i < mBean.getLabel().size(); i++) {
                    CommentLabelBean labelBean = new CommentLabelBean();
                    labelBean.setName(mBean.getLabel().get(i));
                    labelBeans.add(labelBean);
                }
                mLabelAdapter.refreshList(labelBeans);
            } else {
                mLabelAdapter.clear();
            }

        }
    }
}
