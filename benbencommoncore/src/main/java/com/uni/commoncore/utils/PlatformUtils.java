package com.uni.commoncore.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

/**
 * Author:zhn
 * Time:2019/7/8 0008 15:15
 * 三方平台
 */
public class PlatformUtils {

    //1 微信 2 QQ 3 微博
    public static int TYPE_WEI_XIN = 1;
    public static int TYPE_QQ = 2;
    public static int TYPE_WEIBO = 3;


//    /**
//     * 判断是否安装了微信
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isInstallWX(Context context) {
//        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
//        return msgApi.isWXAppInstalled();
//    }

    //判断是否安装了微信
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否安装支付宝
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * //判断是否安装了QQ
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


}
