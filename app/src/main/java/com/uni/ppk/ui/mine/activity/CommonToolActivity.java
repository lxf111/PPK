package com.uni.ppk.ui.mine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 15:32
 * 常用工具
 */
public class CommonToolActivity extends BaseActivity {
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
    @BindView(R.id.tv_tool_fee)
    TextView tvToolFee;
    @BindView(R.id.tv_lawsuit)
    TextView tvLawsuit;
    @BindView(R.id.tv_interest)
    TextView tvInterest;
    @BindView(R.id.tv_days)
    TextView tvDays;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_tool;
    }

    @Override
    protected void initData() {
        initTitle("常用工具");
    }

    @OnClick({R.id.tv_tool_fee, R.id.tv_lawsuit, R.id.tv_interest, R.id.tv_days})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //律师费估算
            case R.id.tv_tool_fee:
                MyApplication.openActivity(mContext, ToolLawyerActivity.class);
                break;
            //诉讼费估算
            case R.id.tv_lawsuit:
                MyApplication.openActivity(mContext, ToolLawsuitActivity.class);
                break;
            //利息计算器
            case R.id.tv_interest:
                MyApplication.openActivity(mContext, ToolInterestActivity.class);
                break;
            //天数计算器
            case R.id.tv_days:
                MyApplication.openActivity(mContext, ToolDaysActivity.class);
                break;
        }
    }
}
