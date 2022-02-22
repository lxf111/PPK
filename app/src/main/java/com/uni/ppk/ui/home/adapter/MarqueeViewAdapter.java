package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.home.bean.HomeLampBean;
import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/12/3
 * Time: 16:16
 */
public class MarqueeViewAdapter extends XMarqueeViewAdapter<HomeLampBean> {

    private Activity mContext;

    public MarqueeViewAdapter(List<HomeLampBean> datas, Activity mContext) {
        super(datas);
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(XMarqueeView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_lamp, null);
    }

    @Override
    public void onBindView(View parent, View view, int position) {
        LinearLayout llytItem=view.findViewById(R.id.llyt_item);
        llytItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().post("jumpCommunity");
            }
        });
        TextView tvRecommend = view.findViewById(R.id.tv_recommend);
        CircleImageView ivHeader = view.findViewById(R.id.iv_header);
        tvRecommend.setText(""+mDatas.get(position).getContent());
        ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDatas.get(position).getAnswer_head_img()), ivHeader, mContext, R.mipmap.ic_default_header);
    }
}
