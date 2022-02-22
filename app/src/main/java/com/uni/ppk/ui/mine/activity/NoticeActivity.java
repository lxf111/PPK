package com.uni.ppk.ui.mine.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/6
 * Time: 10:13
 * 通知
 */
public class NoticeActivity extends BaseActivity {
    @BindView(R.id.cb_switch)
    CheckBox cbSwitch;
    @BindView(R.id.rlyt_switch)
    RelativeLayout rlytSwitch;
    @BindView(R.id.cb_chidao)
    CheckBox cbChidao;
    @BindView(R.id.rlyt_chidao)
    RelativeLayout rlytChidao;
    @BindView(R.id.cb_zaotui)
    CheckBox cbZaotui;
    @BindView(R.id.rlyt_zaotui)
    RelativeLayout rlytZaotui;
    @BindView(R.id.cb_ligang)
    CheckBox cbLigang;
    @BindView(R.id.rlyt_ligang)
    RelativeLayout rlytLigang;
    @BindView(R.id.cb_error_chuli)
    CheckBox cbErrorChuli;
    @BindView(R.id.rlyt_error_chuli)
    RelativeLayout rlytErrorChuli;
    @BindView(R.id.cb_qiandao)
    CheckBox cbQiandao;
    @BindView(R.id.rlyt_qiandao)
    RelativeLayout rlytQiandao;
    @BindView(R.id.cb_qiantui)
    CheckBox cbQiantui;
    @BindView(R.id.rlyt_qiantui)
    RelativeLayout rlytQiantui;
    @BindView(R.id.cb_kuai_chidao_msg)
    CheckBox cbKuaiChidaoMsg;
    @BindView(R.id.rlyt_kuai_chidao_msg)
    RelativeLayout rlytKuaiChidaoMsg;
    @BindView(R.id.cb_yi_zaotui_msg)
    CheckBox cbYiZaotuiMsg;
    @BindView(R.id.rlyt_yi_zaotui_msg)
    RelativeLayout rlytYiZaotuiMsg;
    @BindView(R.id.cb_yi_ligang_msg)
    CheckBox cbYiLigangMsg;
    @BindView(R.id.rlyt_yi_ligang_msg)
    RelativeLayout rlytYiLigangMsg;
    @BindView(R.id.cb_wei_daka)
    CheckBox cbWeiDaka;
    @BindView(R.id.rlyt_wei_daka)
    RelativeLayout rlytWeiDaka;
    @BindView(R.id.cb_xinhao_duankai)
    CheckBox cbXinhaoDuankai;
    @BindView(R.id.rlyt_xinhao_duankai)
    RelativeLayout rlytXinhaoDuankai;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initData() {
        initTitle("通知");
    }

    @OnClick({R.id.cb_switch, R.id.cb_chidao, R.id.cb_zaotui, R.id.cb_ligang, R.id.cb_error_chuli, R.id.cb_qiandao, R.id.cb_qiantui, R.id.cb_kuai_chidao_msg, R.id.cb_yi_zaotui_msg, R.id.cb_yi_ligang_msg, R.id.cb_wei_daka, R.id.cb_xinhao_duankai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_switch:
                break;
            case R.id.cb_chidao:
                break;
            case R.id.cb_zaotui:
                break;
            case R.id.cb_ligang:
                break;
            case R.id.cb_error_chuli:
                break;
            case R.id.cb_qiandao:
                break;
            case R.id.cb_qiantui:
                break;
            case R.id.cb_kuai_chidao_msg:
                break;
            case R.id.cb_yi_zaotui_msg:
                break;
            case R.id.cb_yi_ligang_msg:
                break;
            case R.id.cb_wei_daka:
                break;
            case R.id.cb_xinhao_duankai:
                break;
        }
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }


}
