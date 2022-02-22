package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.widget.CustomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 16:07
 */
public class PhotoAdapter extends AFinalRecyclerViewAdapter<String> {

    public PhotoAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(mInflater.inflate(R.layout.item_photo, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((PhotoViewHolder) holder).setContent(getItem(position), position);
    }

    protected class PhotoViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_img)
        CustomImageView ivImg;

        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(String mBean, int position) {
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean), ivImg, mContext, R.mipmap.ic_default_wide);
        }
    }
}
