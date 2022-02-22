package com.uni.ppk.ui.mine.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.ui.mine.activity.AddressActivity;
import com.uni.ppk.ui.mine.bean.AddressManagerBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/14
 * Time: 14:37
 * <p>
 * 地址信息展示
 */
public class AddressFragment extends LazyBaseFragments {
    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.tv_zip_code)
    TextView tvZipCode;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_setting_default)
    TextView tvSettingDefault;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_editor)
    TextView tvEditor;

    private AddressManagerBean.AddressListBean mBean;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_address, null);
        return mRootView;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mBean = (AddressManagerBean.AddressListBean) getArguments().getSerializable("bean");
        tvReceiverName.setText("" + mBean.getLink_man());
        tvPhone.setText("" + mBean.getLink_phone());
        tvDetailAddress.setText("" + mBean.getAddress_info());
        tvZipCode.setText("" + mBean.getZip_code());
        tvArea.setText("" + mBean.getProvince_name());
        tvState.setText("" + mBean.getCity_name());
        if ("1".equals(mBean.getIs_default())) {
            tvSettingDefault.setText("" + getString(R.string.address_defaulted));
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_select_theme);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
            tvSettingDefault.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvSettingDefault.setText("" + getString(R.string.address_default));
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_select_no);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
            tvSettingDefault.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @OnClick({R.id.tv_setting_default, R.id.tv_delete, R.id.tv_editor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_setting_default:
                if ("1".equals(mBean.getIs_default())) {
                    settingDefault("0");
                } else {
                    settingDefault("1");
                }
                break;
            case R.id.tv_delete:
                delete();
                break;
            case R.id.tv_editor:
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                intent.putExtra("bean", mBean);
                intent.putExtra("editor", true);
                startActivityForResult(intent, 1);
                break;
        }
    }

    /**
     * 删除地址
     */
    private void delete() {
//        StyledDialogUtils.getInstance().loading(getActivity());
//        BaseOkHttpClient.newBuilder().url(NetUrlUtils.DELETE_ADDRESS)
//                .addParam("user_id", "" + MegaMartApplication.mPreferenceProvider.getUId())
//                .addParam("address_id", "" + mBean.getAddress_id())
//                .post()
//                .json()
//                .build().enqueue(getActivity(), new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//                getActivity().finish();
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 设置为默认地址
     */
    private void settingDefault(String isDefault) {
//        StyledDialogUtils.getInstance().loading(getActivity());
//        BaseOkHttpClient.newBuilder().url(NetUrlUtils.UPDATE_ADDRESS)
//                .addParam("user_id", "" + MegaMartApplication.mPreferenceProvider.getUId())
//                .addParam("address_id", "" + mBean.getAddress_id())
//                .addParam("link_man", "" + mBean.getLink_man())
//                .addParam("link_phone", "" + mBean.getLink_phone())
//                .addParam("address_info", "" + mBean.getAddress_info())
//                .addParam("zip_code", "" + mBean.getZip_code())
//                .addParam("province", "" + mBean.getProvince())
//                .addParam("city", "" + mBean.getCity())
//                .addParam("is_default", "" + isDefault)
//                .post()
//                .json()
//                .build().enqueue(getActivity(), new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//                getActivity().finish();
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
