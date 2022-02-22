package com.uni.ppk.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.pop.adapter.SelectVipAdapter;
import com.uni.ppk.ui.home.activity.OrderPromptActivity;
import com.uni.ppk.ui.home.activity.PayMoneyActivity;
import com.uni.ppk.ui.home.bean.VipListBean;

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
 * 选择vip等级：底部弹出
 */
public class SelectVipPopup extends PopupWindow {

    @BindView(R.id.tv_private)
    TextView tvPrivate;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;

    private View view;
    private Activity mContext;

    private SelectVipAdapter mSelectAdapter;
    private List<VipListBean> mPersonList;
    private List<VipListBean> mCompanyList;

    public SelectVipPopup(Activity activity, List<VipListBean> mPersonList, List<VipListBean> mCompanyList) {
        super(activity);
        this.mContext = activity;
        this.mPersonList = mPersonList;
        this.mCompanyList = mCompanyList;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_select_vip, null);
        ButterKnife.bind(this, view);

        tvOpenVip.setText("取消");
        tvPay.setText("立即开通");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rlvList.setLayoutManager(linearLayoutManager);

        mSelectAdapter = new SelectVipAdapter(mContext);
        rlvList.setAdapter(mSelectAdapter);
        mSelectAdapter.refreshList(mPersonList);

        //取消
        tvOpenVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //个人
        tvPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrivate.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                tvCompany.setTextColor(mContext.getResources().getColor(R.color.color_ADADAD));
                mSelectAdapter.refreshList(mPersonList);
            }
        });
        //企业
        tvCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrivate.setTextColor(mContext.getResources().getColor(R.color.color_ADADAD));
                tvCompany.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                mSelectAdapter.refreshList(mCompanyList);
            }
        });
        //开通
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "";
                for (int i = 0; i < mSelectAdapter.getList().size(); i++) {
                    if (mSelectAdapter.getList().get(i).isSelect()) {
                        id = "" + mSelectAdapter.getList().get(i).getAid();
                    }
                }
                if (StringUtils.isEmpty(id)) {
                    ToastUtils.show(mContext, "请选择开通的级别");
                    return;
                }
                submit(id);
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

    private void submit(String id) {
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("vip_type", "" + id);
        orderInfo.put("body", "[]");
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "5");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(orderInfo));
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                Bundle bundle = new Bundle();
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
                bundle.putInt("jumpType", 5);
                AppManager.getInstance().finishActivity(OrderPromptActivity.class);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
                dismiss();
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
}
