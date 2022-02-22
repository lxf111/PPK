package com.uni.ppk.utils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.NormalWebViewActivity;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.config.NormalWebViewConfig;
import com.uni.ppk.ui.home.activity.VipListActivity;
import com.uni.ppk.ui.message.activity.ChatActivity;
import com.uni.ppk.ui.message.bean.MessageUserBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/8
 * Time: 8:52
 */
public class OrderAgreeUtils {
    public static void getRule(Activity mContext) {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "4");
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                Bundle bundle = new Bundle();
                bundle.putString(NormalWebViewConfig.URL, "" + content);
                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
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

    /**
     * 标题的跳转
     *
     * @param mContext
     * @param title
     * @param content
     * @param isHtml
     */
    public static void jumpAgree(Activity mContext, String title, String content, boolean isHtml) {
        Bundle bundle = new Bundle();
        bundle.putString(NormalWebViewConfig.URL, "" + content);
        bundle.putString(NormalWebViewConfig.TITLE, "" + title);
        bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, isHtml);
        MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
    }

    /**
     * 跳转到vip
     *
     * @param mContext
     */
    public static void jumpVip(Activity mContext) {
        Bundle bundle = new Bundle();
        MyApplication.openActivity(mContext, VipListActivity.class, bundle);
    }

    /**
     * 查看合约
     *
     * @param mContext
     */
    public static void watchAgree(Activity mContext) {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "9");
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                Bundle bundle = new Bundle();
                bundle.putString(NormalWebViewConfig.URL, "" + content);
                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
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

    /**
     * 跳转到客服
     *
     * @param mContext
     */
    public static void jumpChat(Activity mContext) {
//        Bundle bundle = new Bundle();
//        MyApplication.openActivity(mContext, VipListActivity.class, bundle);
        if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        HttpUtils.platformCustom(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
//                String title = JSONUtils.getNoteJson(response, "name");
//                String content = JSONUtils.getNoteJson(response, "content");
//                Bundle bundle = new Bundle();
//                bundle.putString(NormalWebViewConfig.URL, "" + content);
//                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
//                bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, true);
//                MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
                String id = JSONUtils.getNoteJson(response, "id");
                String name = JSONUtils.getNoteJson(response, "user_nickname");
                String header = JSONUtils.getNoteJson(response, "head_img");
                MessageUserBean userBean = new MessageUserBean();
                userBean.setName(name);
                userBean.setHeader(header);
                userBean.setId("" + id);
                SaveUserUtils saveUserUtils = new SaveUserUtils(mContext);
                saveUserUtils.refreshHistory(userBean);
                EaseMobHelper.callChatIM(mContext, ChatActivity.class, "" + name,
                        "" + id, true);
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

    public static String showPrice(Activity mContext, String vipPrice, String normalPrice) {
        String price = "";
        if (Integer.parseInt(MyApplication.mPreferenceProvider.getLevel()) > 0) {
            price = vipPrice;
        } else {
            price = normalPrice;
        }
        return price;
    }

    public static void showVipTxt(TextView tvVip) {
        if (Integer.parseInt(MyApplication.mPreferenceProvider.getLevel()) > 0) {
            tvVip.setText("会员权益");
        } else {
            tvVip.setText("开通会员");
        }
    }

}
