package com.uni.ppk.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.uni.ppk.MyApplication;
import com.uni.ppk.pop.TipsPopup;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/13
 * Time: 8:38
 */
public class TipsUtils {
    //只设置标题
    public static void show(Activity mContext, View view, String title, String content, TipsPopup.OnTipsCallback callback) {
        if (MyApplication.mTipsPopup == null) {
            MyApplication.mTipsPopup = new TipsPopup(mContext);
        }
        if (!MyApplication.mTipsPopup.isShowing()) {
            MyApplication.mTipsPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
        MyApplication.mTipsPopup.setmOnTipsCallback(callback,true);
        MyApplication.mTipsPopup.setTitle(title, content);
    }

    //四个控件都设置
    public static void show(Activity mContext, View view,
                            String title, String content, String cancel, String submit,
                            TipsPopup.OnTipsCallback callback) {
        if (MyApplication.mTipsPopup == null) {
            MyApplication.mTipsPopup = new TipsPopup(mContext);
        }
        if (!MyApplication.mTipsPopup.isShowing()) {
            MyApplication.mTipsPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
        MyApplication.mTipsPopup.setmOnTipsCallback(callback,true);
        MyApplication.mTipsPopup.setTitle(title, content);
        MyApplication.mTipsPopup.setButton(cancel, submit);
    }
}
