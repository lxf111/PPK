package com.uni.ppk.ui.mine.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 16:08
 * 诉讼费计算
 */
public class ToolLawsuitActivity extends BaseActivity {
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
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_case_price)
    TextView tvCasePrice;
    @BindView(R.id.tv_keep_price)
    TextView tvKeepPrice;
    @BindView(R.id.tv_execute_price)
    TextView tvExecutePrice;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_property)
    TextView tvProperty;

    private CommonSelectPopup mSelectPopup;
    private List<CommonSelectBean> mSelectBeans;

    private String mCaseId = "";

    private String mIsProperty = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_lawsuit;
    }

    @Override
    protected void initData() {
        mSelectPopup = new CommonSelectPopup(mContext);
        initTitle("诉讼费估算");
    }

    @OnClick({R.id.tv_type, R.id.tv_submit, R.id.tv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //类型
            case R.id.tv_type:
                if (mSelectBeans == null || mSelectBeans.size() == 0) {
                    getCaseType();
                } else {
                    mSelectPopup.setmSelectBeans(mSelectBeans);
                    mSelectPopup.showAtLocation(tvAgree, Gravity.BOTTOM, 0, 0);
                }
                break;
            //是否涉及财产
            case R.id.tv_agree:
                if ("0".equals(mIsProperty)) {
                    mIsProperty = "1";
                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_pay_select);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvAgree.setCompoundDrawables(drawable, null, null, null);
                } else {
                    mIsProperty = "0";
                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_pay_select_no);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvAgree.setCompoundDrawables(drawable, null, null, null);
                }
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
        params.put("type", "" + mCaseId);
        params.put("price", "" + price);
        params.put("is_property", "" + mIsProperty);
        HttpUtils.toolLawsuit(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                tvCasePrice.setText("¥" + ArithUtils.saveSecond(JSONUtils.getNoteJson(response, "shouli")));
                tvKeepPrice.setText("¥" + ArithUtils.saveSecond(JSONUtils.getNoteJson(response, "baoquan")));
                tvExecutePrice.setText("¥" + JSONUtils.getNoteJson(response, "zhixing"));
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
        params.put("types", "lawsuit");
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
                    mSelectPopup.showAtLocation(tvAgree, Gravity.BOTTOM, 0, 0);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
