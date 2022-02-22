package com.uni.ppk.ui.mine.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.mine.bean.AddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: whx
 * Date: 2019/11/2 0002
 * Time: 14:15
 * 收货地址的Adapter
 */
public class AddressAdapter extends AFinalRecyclerViewAdapter<AddressBean> {

    public AddressAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(mInflater.inflate(R.layout.item_address, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((AddressViewHolder) holder).setContent(getItem(position), position);
    }

    protected class AddressViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_address_name)
        TextView tvAddressName;
        @BindView(R.id.tv_address_phone)
        TextView tvAddressPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_address_set)
        TextView tvAddressSet;
        @BindView(R.id.tv_default)
        TextView tvDefault;
        private Drawable drawableno;
        private Drawable drawable;
        private String mId;


        public AddressViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(AddressBean mBean, int position) {
//            tvAddressName.setText("" + mBean.getReciverName());
//            tvAddressPhone.setText("" + mBean.getReciverTelephone());
//            String areac = mBean.getAreac();//省
//            String areap = mBean.getAreap();//市
//            String areax = mBean.getAreax();//区
//            String address = mBean.getDetailedAddress();//详细地址
//            tvAddress.setText("" + areac + " " + areap + " " + areax + " " + address);
//            mId = mBean.getId();
//            String flag = mBean.getDefaultFlag();
//            if ("0".equals(flag)) {
//                tvDefault.setVisibility(View.INVISIBLE);
//                drawableno = m_Context.getResources().getDrawable(R.mipmap.ic_agree_no_select);
//                drawableno.setBounds(0, 0, drawableno.getMinimumWidth(), drawableno.getMinimumHeight());
//                tvAddressSet.setCompoundDrawables(drawableno, null, null, null);
//            } else {
//                tvDefault.setVisibility(View.VISIBLE);
//                tvAddressSet.setVisibility(View.VISIBLE);
//                drawable = m_Context.getResources().getDrawable(R.mipmap.ic_agree_selected);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                tvAddressSet.setCompoundDrawables(drawable, null, null, null);
//            }
//
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.onItemClick(v, position, mBean);
//                    }
//                }
//            });
//            tvAddressSet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getDefaultAddress(position);//默认地址
//                }
//            });

        }

        private void getDefaultAddress(int position) {
//            StyledDialogUtils.getInstance().loading(m_Activity);
//            BaseOkHttpClient.newBuilder()
//                    .url(NetUrlUtils.MINE_ADDRESS_DEFAULT)
//                    .addParam("id", "" + mId)
//                    .post()
//                    .build()
//                    .enqueue(m_Activity, new BaseCallBack<String>() {
//                        @Override
//                        public void onSuccess(String result, String msg) {
//                            StyledDialogUtils.getInstance().dismissLoading();
//                            ToastUtils.show(m_Context, msg);
//
//                            if ("0".equals(getList().get(position).getDefaultFlag())) {
//                                for (int i = 0; i < getList().size(); i++) {
//                                    getList().get(i).setDefaultFlag("0");
//                                }
//                                getList().get(position).setDefaultFlag("1");
//                            } else {
//                                getList().get(position).setDefaultFlag("0");
//                            }
//                            notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onError(int code, String msg) {
//                            StyledDialogUtils.getInstance().dismissLoading();
//                            ToastUtils.show(m_Context, msg);
//                        }
//
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            ToastUtils.show(m_Context, m_Context.getString(R.string.toast_service_error));
//                            StyledDialogUtils.getInstance().dismissLoading();
//                        }
//                    });
        }
    }


}
