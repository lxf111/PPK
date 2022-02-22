package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.mine.activity.EnlargePhotoActivity;
import com.uni.ppk.widget.CustomImageView55;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/17
 * Time: 8:53
 */
public class OrderPhotoAdapter extends AFinalRecyclerViewAdapter<String> {

    public OrderPhotoAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new OrderPhotoViewHolder(mInflater.inflate(R.layout.item_order_photo, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((OrderPhotoViewHolder) holder).setContent(getItem(position), position);
    }

    protected class OrderPhotoViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CustomImageView55 ivImg;

        public OrderPhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(String mBean, int position) {
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean), ivImg, mContext, R.mipmap.ic_default_wide);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EnlargePhotoActivity.class);
                    intent.putExtra(Constants.EXTRA_ENLARGE_INDEX, position);
                    intent.putExtra(Constants.EXTRA_ENLARGE_PHOTO, (Serializable) getList());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
