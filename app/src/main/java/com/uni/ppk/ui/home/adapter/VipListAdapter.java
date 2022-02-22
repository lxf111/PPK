package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.home.bean.VipListBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 8:48
 */
public class VipListAdapter extends AFinalRecyclerViewAdapter<VipListBean> {

    public VipListAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new VipViewHolder(mInflater.inflate(R.layout.item_vip_list, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((VipViewHolder) holder).setContent(getItem(position), position);
    }

    protected class VipViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title_price)
        TextView tvTitlePrice;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.llyt_item)
        LinearLayout llytItem;
        @BindView(R.id.iv_bg)
        ImageView ivBg;

        public VipViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(VipListBean mBean, int position) {
            if (position == 0) {
//                llytItem.setBackgroundResource(R.mipmap.ic_vip1);
            } else if (position == 1) {
//                llytItem.setBackgroundResource(R.mipmap.ic_vip1);
            } else if (position == 2) {
//                llytItem.setBackgroundResource(R.mipmap.ic_vip_3);
            } else if (position == 3) {
//                llytItem.setBackgroundResource(R.mipmap.ic_vip4);
//                tvContent.setTextColor(mContext.getResources().getColor(R.color.vip_content));
//                tvTitlePrice.setTextColor(mContext.getResources().getColor(R.color.vip_content));
            }
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getBackground()), ivBg, mContext, R.mipmap.ic_vip1);
            tvTitlePrice.setText(mBean.getName() + "\n" + mBean.getPrice() + "元/年");
            String content = "";
            if (mBean.getService() != null && mBean.getService().size() > 0) {
                for (int i = 0; i < mBean.getService().size(); i++) {
                    if (i == 0) {
                        content = "· " + mBean.getService().get(i).getName();
                    } else {
                        content = content + "\n· " + mBean.getService().get(i).getName();
                    }
                }
            }
            tvContent.setText("" + content);
        }
    }
}
