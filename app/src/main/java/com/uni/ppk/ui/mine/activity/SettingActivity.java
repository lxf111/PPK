package com.uni.ppk.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.AppUtils;
import com.uni.commoncore.utils.DataCleanManager;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ThreadPoolUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.NormalWebViewActivity;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.NormalWebViewConfig;
import com.uni.ppk.ui.login.LoginActivity;
import com.uni.ppk.ui.mine.bean.VersionBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.zjn.updateapputils.util.CheckVersionRunnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {

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
    @BindView(R.id.tv_update_pwd)
    TextView tvUpdatePwd;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_now_version)
    TextView tvNowVersion;
    @BindView(R.id.tv_cache_title)
    TextView tvCacheTitle;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.rl_delete_cache)
    RelativeLayout rlDeleteCache;
    @BindView(R.id.btn_exit)
    Button btnExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        initTitle("" + getString(R.string.setting));
        tvNowVersion.setText("" + getString(R.string.setting_now_version) + AppUtils.getVerName(this));
        try {
            tvCacheSize.setText("" + DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_update_pwd, R.id.tv_bind, R.id.tv_private_agree, R.id.tv_agree, R.id.tv_version, R.id.tv_now_version, R.id.rl_delete_cache, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //修改手机号
            case R.id.tv_update_pwd:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, UpdatePassword.class);
                break;
            //绑定手机号
            case R.id.tv_bind:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, CheckPhoneActivity.class);
                break;
            //用户协议
            case R.id.tv_agree:
                getAgree("1");
                break;
            //隐私协议
            case R.id.tv_private_agree:
                getAgree("7");
                break;
            //版本更新
            case R.id.tv_version:
            case R.id.tv_now_version:
                getVersion();
                break;
            //清除缓存
            case R.id.rl_delete_cache:
                DataCleanManager.clearAllCache(mContext);
                ToastUtils.show(mContext, "缓存已清除");
                try {
                    tvCacheSize.setText("" + DataCleanManager.getTotalCacheSize(mContext));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //退出登录
            case R.id.btn_exit:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                LoginCheckUtils.clearUserInfo(mContext);
                MyApplication.openActivity(mContext, LoginActivity.class);
                break;
        }
    }

    private void getVersion() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.updateVersion(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                VersionBean mBean = JSONUtils.parserObject(response, "version", VersionBean.class);
                if (mBean == null) {
                    toast("当前为最新版本");
                    return;
                }
                if (mBean.getVercode() == null) {
                    toast("当前为最新版本");
                    return;
                }
                if (AppUtils.getVersionCode(mContext) >= Double.parseDouble(mBean.getVercode())) {
                    toast("当前为最新版本");
                    return;
                }
                if ("1".equals(mBean.getForce())) {
                    //是否强制更新 0：否 1：是
                    CheckVersionRunnable runnable = CheckVersionRunnable.from(mContext)
//                                    .setApkPath(SystemDir.DIR_UPDATE_APK)//文件存储路径
                            .setDownLoadUrl(mBean.getAddress())
                            .setHandleQzgx(false)
                            .setServerUpLoadLocalVersion("" + (AppUtils.getVersionCode(mContext) + 1))
                            .setServerVersion("" + (AppUtils.getVersionCode(mContext) + 2))
                            .setUpdateMsg(mBean.getContent())
                            .isUseCostomDialog(true)
                            .setHandleQzgx(false)
                            .setNotifyTitle(getResources().getString(R.string.app_name))
                            .setVersionShow(mBean.getContent());
                    //启动通知，去下载
                    ThreadPoolUtils.newInstance().execute(runnable);
                } else {
                    CheckVersionRunnable runnable = CheckVersionRunnable.from(mContext)
//                                    .setApkPath(SystemDir.DIR_UPDATE_APK)//文件存储路径
                            .setHandleQzgx(true)
                            .setDownLoadUrl(mBean.getAddress())
                            .setServerUpLoadLocalVersion("" + (AppUtils.getVersionCode(mContext) - 1))
                            .setServerVersion("" + (AppUtils.getVersionCode(mContext) + 2))
                            .setUpdateMsg(mBean.getContent())
                            .isUseCostomDialog(true)
                            .setHandleQzgx(false)
                            .setNotifyTitle(getResources().getString(R.string.app_name))
                            .setVersionShow(mBean.getContent());
                    //启动通知，去下载
                    ThreadPoolUtils.newInstance().execute(runnable);
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

    private void getAgree(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "" + type);
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                Bundle bundle = new Bundle();
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
                bundle.putString(NormalWebViewConfig.URL, "" + content);
                bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, true);
                MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
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
