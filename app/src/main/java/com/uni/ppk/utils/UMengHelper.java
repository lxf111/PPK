package com.uni.ppk.utils;

import android.content.Context;

import com.uni.ppk.config.Constants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * 用于友盟初始化
 */
public class UMengHelper {

    //对应于服务后台的位置：应用管理 -> 应用信息 -> Appkey
    public static String APP_KEY = "5f88003994846f78a9737b68";
    //渠道号
    public static String CHANNEL_ID = "umeng";
    //设备类型
    public static int DEVICE_TYPE = UMConfigure.DEVICE_TYPE_PHONE;
    public static String MESSAGE_SECRET = "";

    /**
     * 友盟初始化common库
     * 参数1:上下文，不能为空
     * 参数2:【友盟+】 AppKey
     * 参数3:【友盟+】 Channel
     * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
     */
    public static void init(final Context context) {
        UMConfigure.init(context,
                UMengHelper.APP_KEY,
                UMengHelper.CHANNEL_ID,
                UMengHelper.DEVICE_TYPE,
                UMengHelper.MESSAGE_SECRET);

        UMConfigure.setLogEnabled(true);
        //微信
        PlatformConfig.setWeixin(Constants.WX_APP_KEY, Constants.WX_SECRET);
        //QQ
//        PlatformConfig.setQQZone(Constants.QQ_APP_KEY, Constants.QQ_APP_SECRET);
        //微博
//        PlatformConfig.setSinaWeibo(Constants.SINA_APP_KEY, Constants.SINA_APP_SECRET, "https://api.weibo.com/oauth2/default.html");

    }

}
