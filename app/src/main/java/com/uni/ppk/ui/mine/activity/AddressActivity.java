package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.adapter.AddressAdapter;
import com.uni.ppk.ui.mine.bean.AddressBean;
import com.uni.ppk.widget.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: whx
 * Date: 2019/11/2 0002
 * Time: 14:04
 * 收货地址
 */
public class AddressActivity extends BaseActivity {
    @BindView(R.id.rlv_address)
    CustomRecyclerView rlvAddress;
    @BindView(R.id.gv_address_add)
    CardView gvAddressAdd;
    @BindView(R.id.llyt_null)
    LinearLayout llytNull;

    private AddressAdapter mAddressAdapter;
    private List<AddressBean> mAddressBeans = new ArrayList<>();

    private boolean mType = false;//是否为选择地址

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initData() {
        initTitle("收货地址");

        mType = getIntent().getBooleanExtra("type", false);

        //收货地址的Recyclerview
        rlvAddress.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAddressAdapter = new AddressAdapter(mContext);
        rlvAddress.setAdapter(mAddressAdapter);
        mAddressAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<AddressBean>() {
            @Override
            public void onItemClick(View view, int position, AddressBean model) {
                if (!mType) {
//                    Intent intent = new Intent(mContext, ModificationIdActivity.class);
//                    intent.putExtra("bean", model);
//                    mContext.startActivityForResult(intent, 101);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("bean", model);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onItemLongClick(View view, int position, AddressBean model) {

            }
        });
        getAddressList();
    }

    //获取地址列表
    private void getAddressList() {
        llytNull.setVisibility(View.GONE);
        List<AddressBean> addressBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            addressBeans.add(new AddressBean());
        }
        mAddressAdapter.refreshList(addressBeans);
//        StyledDialogUtils.getInstance().loading(mContext);
//        BaseOkHttpClient.newBuilder()
//                .url(NetUrlUtils.MINE_ADDRESS_RLV)
//                .get()
//                .build()
//                .enqueue(mContext, new BaseCallBack<String>() {
//                    @Override
//                    public void onSuccess(String result, String msg) {
//                        StyledDialogUtils.getInstance().dismissLoading();
//                        mAddressBeans = JSONUtils.jsonString2Beans(result, AddressBean.class);
//                        mAddressAdapter.refreshList(mAddressBeans);
//                        if (mAddressBeans != null && mAddressBeans.size() > 0) {
//                            llytNull.setVisibility(View.GONE);
//                        } else {
//                            llytNull.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        StyledDialogUtils.getInstance().dismissLoading();
//                        toast(msg);
//                    }
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        ToastUtils.show(mContext, getString(R.string.toast_service_error));
//                        StyledDialogUtils.getInstance().dismissLoading();
//                    }
//                });
    }

    @OnClick(R.id.gv_address_add)
    public void onViewClicked() {
        MyApplication.openActivityForResult(mContext, AddAddressActivity.class, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            getAddressList();
        }
    }

}
