package com.uni.ppk.ui.mine.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.CommonSelectPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.utils.ArithUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 15:57
 * 律师费估算
 */
public class ToolLawyerActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_basis)
    TextView tvBasis;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_reckon)
    TextView tvReckon;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;


    private CommonSelectPopup mSelectPopup;
    private List<CommonSelectBean> mSelectBeans;

    private String mCityId = "";
    private String mCaseId = "";

    private List<CommonSelectBean> mAddressBeans = new ArrayList<>();
    private CommonSelectPopup mAddressSelectPopup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_lawyer;
    }

    @Override
    protected void initData() {
        mSelectPopup = new CommonSelectPopup(mContext);
        mAddressSelectPopup = new CommonSelectPopup(mContext);

        initTitle("律师费估算");
    }

    @OnClick({R.id.tv_address, R.id.tv_type, R.id.tv_basis, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //地址
            case R.id.tv_address:
//                mAddressPopup.setmAddressId(new AddressSelectPopup.OnAddressCallback() {
//                    @Override
//                    public void callback(String province, String city, String area) {
//                        //获取id
//                        mCityId = area;
//                    }
//                });
//                mAddressPopup.setmAddressCallback(new AddressSelectPopup.OnAddressCallback() {
//                    @Override
//                    public void callback(String province, String city, String area) {
//                        //获取名字
//                        tvAddress.setText(province + city + area);
//                    }
//                });
//                mAddressPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                if (mAddressBeans == null || mAddressBeans.size() == 0) {
                    getAddress();
                } else {
                    mAddressSelectPopup.setmSelectBeans(mAddressBeans);
                    mAddressSelectPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                }
                break;
            //类型
            case R.id.tv_type:
                if (mSelectBeans == null || mSelectBeans.size() == 0) {
                    getCaseType();
                } else {
                    mSelectPopup.setmSelectBeans(mSelectBeans);
                    mSelectPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                }
                break;
            //依据
            case R.id.tv_basis:
                break;
            //计算
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void submit() {
        String price = edtMoney.getText().toString().trim();
        if (StringUtils.isEmpty(price)) {
            ToastUtils.show(mContext, "请输入标的金额");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("city_id", "" + mCityId);
        params.put("typeid", "" + mCaseId);
        params.put("price", "" + price);
        HttpUtils.toolLawyer(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                tvReckon.setText("" + ArithUtils.saveSecond(response));
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    //获取案件类型
    private void getCaseType() {
        Map<String, Object> params = new HashMap<>();
        params.put("types", "types");
        HttpUtils.toolCaseType(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mSelectBeans = JSONUtils.jsonString2Beans(response, CommonSelectBean.class);
                if (mSelectBeans != null && mSelectBeans.size() > 0) {
                    mSelectPopup.setmSelectBeans(mSelectBeans);
                    mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                        @Override
                        public void onItemClick(View view, int position, CommonSelectBean model) {
                            tvType.setText("" + model.getName());
                            mCaseId = "" + model.getId();
                            mSelectPopup.dismiss();
                        }

                        @Override
                        public void onItemLongClick(View view, int position, CommonSelectBean model) {

                        }
                    });
                    mSelectPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    //获取案件类型
    private void getAddress() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.toolAddress(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<CommonSelectBean> beans = JSONUtils.jsonString2Beans(response, CommonSelectBean.class);
                if (beans != null && beans.size() > 0) {
                    for (CommonSelectBean bean : beans) {
                        bean.setName(bean.getCityname());
                        mAddressBeans.add(bean);
                    }
                    mAddressSelectPopup.setmSelectBeans(mAddressBeans);
                    mAddressSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                        @Override
                        public void onItemClick(View view, int position, CommonSelectBean model) {
                            tvAddress.setText("" + model.getName());
                            mCityId = "" + model.getId();
                            mAddressSelectPopup.dismiss();
                        }

                        @Override
                        public void onItemLongClick(View view, int position, CommonSelectBean model) {

                        }
                    });
                    mAddressSelectPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

}
