package com.uni.ppk.ui.mine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 16:30
 * 天数计算器
 */
public class ToolDaysActivity extends BaseActivity {
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
    @BindView(R.id.tv_total_price1)
    TextView tvTotalPrice1;
    @BindView(R.id.tv_total_price2)
    TextView tvTotalPrice2;
    @BindView(R.id.tv_total_price3)
    TextView tvTotalPrice3;
    @BindView(R.id.tv_total_price4)
    TextView tvTotalPrice4;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private TimePickerView pvTime;
    private Date mStartData = null;
    private int mSelectTimeType = 1;//1开始时间 2结束时间

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_days;
    }

    @Override
    protected void initData() {
        initTitle("天数计算器");
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                initTimeSelect();
                mSelectTimeType = 1;
                pvTime.show();
                break;
            case R.id.tv_end_time:
                if (mStartData == null) {
                    ToastUtils.show(mContext, "请选择开始时间");
                    return;
                }
                initTimeSelectEnd(mStartData);
                mSelectTimeType = 2;
                pvTime.show();
                break;
            case R.id.tv_submit:
                submit();
                break;
        }
    }

    /**
     * 获取天数
     */
    private void submit() {
        if (StringUtils.isEmpty(tvStartTime.getText().toString())) {
            ToastUtils.show(mContext, "请选择开始时间");
            return;
        }
        if (StringUtils.isEmpty(tvEndTime.getText().toString())) {
            ToastUtils.show(mContext, "请选择结束时间");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("start_date", "" + tvStartTime.getText().toString());
        params.put("end_date", "" + tvEndTime.getText().toString());
        HttpUtils.toolDay(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                tvTotalPrice4.setText("" + JSONUtils.getNoteJson(response, "total_month") + "");
                tvTotalPrice3.setText("" + JSONUtils.getNoteJson(response, "week_days"));
                tvTotalPrice2.setText("" + JSONUtils.getNoteJson(response, "working_days") + "");
                tvTotalPrice1.setText("" + JSONUtils.getNoteJson(response, "total_days") + "");
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

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
}
