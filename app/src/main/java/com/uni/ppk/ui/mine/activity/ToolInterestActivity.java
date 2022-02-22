package com.uni.ppk.ui.mine.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.SoftInputUtils;
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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
 * Time: 16:17
 * 利息计算器
 */
public class ToolInterestActivity extends BaseActivity {
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
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.edt_power)
    EditText edtPower;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_interest_price)
    TextView tvInterestPrice;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_rate2)
    TextView tvRate2;

    private TimePickerView pvTime;
    private Date mStartData = null;
    private int mSelectTimeType = 1;//1开始时间 2结束时间

    private CommonSelectPopup mSelectPopup;
    private List<CommonSelectBean> mSelectBeans;

    private String mRates = "";//利率

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_interest;
    }

    @Override
    protected void initData() {
        initTitle("利息计算器");
        mSelectPopup = new CommonSelectPopup(mContext);
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.tv_rate, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                SoftInputUtils.hideSoftInput(mContext);
                initTimeSelect();
                mSelectTimeType = 1;
                pvTime.show();
                break;
            case R.id.tv_end_time:
                SoftInputUtils.hideSoftInput(mContext);
                if (mStartData == null) {
                    ToastUtils.show(mContext, "请选择开始时间");
                    return;
                }
                initTimeSelectEnd(mStartData);
                mSelectTimeType = 2;
                pvTime.show();
                break;
            case R.id.tv_rate:
                SoftInputUtils.hideSoftInput(mContext);
                getRateList();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    private void submit() {
        String price = edtMoney.getText().toString().trim();
        String power = edtPower.getText().toString().trim();
        String startTime = tvStartTime.getText().toString().trim();
        String endTime = tvEndTime.getText().toString().trim();
        if (StringUtils.isEmpty(startTime)) {
            ToastUtils.show(mContext, "请选择起始时间");
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            ToastUtils.show(mContext, "请选择截止时间");
            return;
        }
        if (StringUtils.isEmpty(price)) {
            ToastUtils.show(mContext, "请输入标的金额");
            return;
        }
        if (StringUtils.isEmpty(power)) {
            ToastUtils.show(mContext, "请输入倍率");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("rates", "" + mRates);
        params.put("start_date", "" + startTime);
        params.put("end_date", "" + endTime);
        params.put("price", "" + price);
        params.put("magnification", "" + power);
        HttpUtils.toolRate(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                tvInterestPrice.setText("¥" + ArithUtils.saveSecond(JSONUtils.getNoteJson(response, "interest")));
                tvTotalPrice.setText("" + JSONUtils.getNoteJson(response, "days") + "天");
                tvRate2.setText("" + mRates + "%");
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

    //获取利率列表
    private void getRateList() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.toolRateList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mSelectBeans = JSONUtils.jsonString2Beans(response, CommonSelectBean.class);
                if (mSelectBeans != null && mSelectBeans.size() > 0) {
                    mSelectPopup.setmSelectBeans(mSelectBeans);
                    mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                        @Override
                        public void onItemClick(View view, int position, CommonSelectBean model) {
                            tvRate.setText("" + model.getName());
                            mRates = "" + model.getRate();
                            mSelectPopup.dismiss();
                        }

                        @Override
                        public void onItemLongClick(View view, int position, CommonSelectBean model) {

                        }
                    });
                    mSelectPopup.showAtLocation(tvEndTime, Gravity.BOTTOM, 0, 0);
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

    /**
     * 时间选择器
     * 1开始时间
     * 2结束时间
     */
    private void initTimeSelect() {
        //时间选择器
        Calendar calendar = Calendar.getInstance();  //获取当前时间
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year + 100, month, day);
        Calendar startDate = Calendar.getInstance();
        startDate.set(year - 100, month, day);
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mStartData = date;
                tvEndTime.setText("");
                if (mSelectTimeType == 1) {
                    tvStartTime.setText(fmortData(date));
                } else if (mSelectTimeType == 2) {
                    tvEndTime.setText(fmortData(date));
                }
            }
        }).setRangDate(startDate, endDate).setDate(calendar).build();
    }

    /**
     * 时间选择器
     * 1开始时间
     * 2结束时间
     */
    private void initTimeSelectEnd(Date date) {
        //时间选择器
        Calendar startData = Calendar.getInstance();
        startData.setTime(date);

        Calendar calendar = Calendar.getInstance();  //获取当前时间
        int year = startData.get(Calendar.YEAR);
        int month = startData.get(Calendar.MONTH);
        int day = startData.get(Calendar.DAY_OF_MONTH);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year + 100, month, day);
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mSelectTimeType == 1) {
                    tvStartTime.setText(fmortData(date));
                } else if (mSelectTimeType == 2) {
                    tvEndTime.setText(fmortData(date));
                }
            }
        }).setRangDate(startData, endDate).build();
    }

    /**
     * 时间格式转换
     *
     * @param date
     * @return
     */
    private String fmortData(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
