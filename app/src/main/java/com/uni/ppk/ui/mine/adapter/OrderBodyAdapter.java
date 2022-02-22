package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.mine.bean.OrderDetailBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/14
 * Time: 11:53
 */
public class OrderBodyAdapter extends AFinalRecyclerViewAdapter<OrderDetailBean.BodyBean> {

    private Map<String, Integer> mIcon = new HashMap<>();

    public OrderBodyAdapter(Activity ctx) {
        super(ctx);
        mIcon.put(Constants.ORDER_REWARDS, R.mipmap.ic_order_money);
        mIcon.put(Constants.ORDER_DEFAULT_PRICE, R.mipmap.ic_order_money);
        mIcon.put(Constants.ORDER_TAOCAN, R.mipmap.ic_order_food);
        mIcon.put(Constants.ORDER_HETONGLEIXING, R.mipmap.ic_order_constract);
        mIcon.put(Constants.ORDER_HETONGXIANG, R.mipmap.ic_order_itms);
        mIcon.put(Constants.ORDER_YUANGAO, R.mipmap.ic_order_yuangao);
        mIcon.put(Constants.ORDER_BEIGAO, R.mipmap.ic_order_beigao);
        mIcon.put(Constants.ORDER_BIAODEJINE, R.mipmap.ic_order_feiyong);
        mIcon.put(Constants.ORDER_JIEDUAN, R.mipmap.ic_order_time);
        mIcon.put(Constants.ORDER_ANHAO, R.mipmap.ic_order_anhao);
        mIcon.put(Constants.ORDER_HUIJIAN, R.mipmap.ic_order_zhixingname);
        mIcon.put(Constants.ORDER_DANGWEI, R.mipmap.ic_order_lianfayuan);
        mIcon.put(Constants.ORDER_PAGE, R.mipmap.ic_order_page);
        mIcon.put(Constants.ORDER_TXT_FIRST, R.mipmap.ic_order_constract);
        mIcon.put(Constants.ORDER_TXT_SECOND, R.mipmap.ic_order_itms);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new BodyViewHolder(mInflater.inflate(R.layout.item_order_body, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((BodyViewHolder) holder).setContent(getItem(position), position);
    }

    protected class BodyViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_body)
        TextView tvBody;

        public BodyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(OrderDetailBean.BodyBean mBean, int position) {
            tvBody.setText("" + mBean.getName() + "：" + mBean.getValue());
            if (mIcon.containsKey(mBean.getKey())) {
                Drawable drawable = mContext.getResources().getDrawable(mIcon.get(mBean.getKey()));
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvBody.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }
}
