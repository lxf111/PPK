package com.uni.ppk.ui.community.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.community.bean.RewardBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 15:53
 */
public class RewardAdapter extends AFinalRecyclerViewAdapter<RewardBean> {

    public RewardAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new RewardViewHolder(mInflater.inflate(R.layout.item_reward, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((RewardViewHolder) holder).setContent(getItem(position), position);
    }

    protected class RewardViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.edt_content)
        EditText edtContent;

        public RewardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(RewardBean mBean, int position) {
            tvContent.setText("" + mBean.getName());
            if (mBean.isSelect()) {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvContent.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);

                edtContent.setTextColor(mContext.getResources().getColor(R.color.theme));
                edtContent.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);
            } else {
                tvContent.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                tvContent.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);

                edtContent.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                edtContent.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);
            }
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //通过接口回调将数据传递到Activity中,修改list里的bean
                    mBean.setName("" + editable.toString().trim());
                }
            };
            edtContent.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    //有焦点，添加监听 addTextChangedListener
                    edtContent.addTextChangedListener(watcher);
                } else {
                    //无焦点，移除监听 removeTextChangedListener
                    edtContent.removeTextChangedListener(watcher);
                }
            });
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
