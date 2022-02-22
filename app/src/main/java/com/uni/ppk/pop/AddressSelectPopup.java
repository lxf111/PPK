package com.uni.ppk.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.pop.adapter.CommonSelectAdapter;
import com.uni.ppk.pop.bean.CommonSelectBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 地址选择器：底部弹出
 */
public class AddressSelectPopup extends PopupWindow {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;
    @BindView(R.id.rlv_province)
    RecyclerView rlvProvince;
    @BindView(R.id.rlv_city)
    RecyclerView rlvCity;
    @BindView(R.id.rlv_area)
    RecyclerView rlvArea;

    private View view;
    private Activity mContext;

    private CommonSelectAdapter mProvincedapter;
    private CommonSelectAdapter mCityAdapter;
    private CommonSelectAdapter mAreaAdapter;

    private String mProvince = "";//省份
    private String mCity = "";//城市
    private String mArea = "";//地区

    private String mProvinceId = "";
    private String mCityId = "";
    private String mAreaId = "";

    public AddressSelectPopup(Activity activity) {
        super(activity);
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_address_select, null);
        ButterKnife.bind(this, view);
        SoftInputUtils.hideSoftInput(mContext);
        rlvProvince.setLayoutManager(new LinearLayoutManager(mContext));
        rlvCity.setLayoutManager(new LinearLayoutManager(mContext));
        rlvArea.setLayoutManager(new LinearLayoutManager(mContext));

        //省份
        mProvincedapter = new CommonSelectAdapter(mContext);
        rlvProvince.setAdapter(mProvincedapter);
        mProvincedapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
            @Override
            public void onItemClick(View view, int position, CommonSelectBean model) {
                mProvince = model.getName();
                mProvinceId = "" + model.getId();
                tvCancel.setText("返回");

                tvTitle.setText("选择城市");
                getProvince(model.getId(), "2");
            }

            @Override
            public void onItemLongClick(View view, int position, CommonSelectBean model) {

            }
        });

        //城市
        mCityAdapter = new CommonSelectAdapter(mContext);
        rlvCity.setAdapter(mCityAdapter);
        mCityAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
            @Override
            public void onItemClick(View view, int position, CommonSelectBean model) {
                mCity = model.getName();
                mCityId = "" + model.getId();
                tvCancel.setText("返回");

                tvTitle.setText("选择地区");
                getProvince(model.getId(), "3");
            }

            @Override
            public void onItemLongClick(View view, int position, CommonSelectBean model) {

            }
        });

        //地区
        mAreaAdapter = new CommonSelectAdapter(mContext);
        rlvArea.setAdapter(mAreaAdapter);
        mAreaAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
            @Override
            public void onItemClick(View view, int position, CommonSelectBean model) {
                mArea = model.getName();
                mAreaId = "" + model.getId();
                tvCancel.setText("返回");
                if (mAddressCallback != null) {
                    mAddressCallback.callback(mProvince, mCity, mArea);
                }
                if (mAddressId != null) {
                    mAddressId.callback(mProvinceId, mCityId, mAreaId);
                }
                dismiss();
            }

            @Override
            public void onItemLongClick(View view, int position, CommonSelectBean model) {

            }
        });
        //获取省份
        getProvince("", "1");

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(mCity)) {
                    //返回城市
                    mCity = "";
                    rlvArea.setVisibility(View.GONE);
                    rlvCity.setVisibility(View.VISIBLE);
                    rlvProvince.setVisibility(View.GONE);
                    tvCancel.setText("返回");
                    tvTitle.setText("选择城市");
                    return;
                }
                if (!StringUtils.isEmpty(mProvince)) {
                    //返回身份
                    mProvince = "";
                    rlvArea.setVisibility(View.GONE);
                    rlvCity.setVisibility(View.GONE);
                    rlvProvince.setVisibility(View.VISIBLE);
                    tvCancel.setText("取消");
                    tvTitle.setText("选择省份");
                    return;
                }
                dismiss();
            }
        });

        // 导入布局
        this.setContentView(view);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);

        // 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.ll_pop).getTop();
                int bottom = view.findViewById(R.id.ll_pop).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowManager.LayoutParams layoutParams = mContext.getWindow().getAttributes();
        layoutParams.alpha = 0.7f;
        mContext.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams layoutParams = mContext.getWindow().getAttributes();
        layoutParams.alpha = 1f;
        mContext.getWindow().setAttributes(layoutParams);
    }

    public void setTitle(String title) {
        tvTitle.setText("" + title);
    }

    private void getProvince(String id, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("parent_id", "" + id);
        HttpUtils.cityList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<CommonSelectBean> beans = JSONUtils.jsonString2Beans(response, CommonSelectBean.class);
                if ("1".equals(type)) {
                    mProvincedapter.refreshList(beans);
                    rlvArea.setVisibility(View.GONE);
                    rlvCity.setVisibility(View.GONE);
                    rlvProvince.setVisibility(View.VISIBLE);
                } else if ("2".equals(type)) {
                    rlvArea.setVisibility(View.GONE);
                    rlvCity.setVisibility(View.VISIBLE);
                    rlvProvince.setVisibility(View.GONE);
                    mCityAdapter.refreshList(beans);
                } else if ("3".equals(type)) {
                    mAreaAdapter.refreshList(beans);
                    rlvArea.setVisibility(View.VISIBLE);
                    rlvCity.setVisibility(View.GONE);
                    rlvProvince.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, mContext.getResources().getString(R.string.service_error));
            }
        });
    }

    private OnAddressCallback mAddressCallback;
    private OnAddressCallback mAddressId;

    public void setmAddressCallback(OnAddressCallback mAddressCallback) {
        this.mAddressCallback = mAddressCallback;
    }

    public interface OnAddressCallback {
        void callback(String province, String city, String area);
    }

    public void setmAddressId(OnAddressCallback mAddressId) {
        this.mAddressId = mAddressId;
    }
}
