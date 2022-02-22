package com.uni.commoncore.utils;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.kongzue.dialog.v3.WaitDialog;

/**
 * 通用进度，对话框封装工具类
 * create by zjn on 2019/5/15 0015
 * email:168455992@qq.com
 */
public class StyledDialogUtils {

    private static StyledDialogUtils mInstance;

    public static StyledDialogUtils getInstance() {
        if (mInstance == null) {
            mInstance = new StyledDialogUtils();
        }
        return mInstance;
    }

    private StyledDialogUtils() {

    }

    public void loading(final Activity activity) {
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WaitDialog.show((AppCompatActivity) activity, "加载中…")
                            .setBackgroundResId(0).setCancelable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*关闭进度条*/
    public void dismissLoading() {
        try{
            WaitDialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
