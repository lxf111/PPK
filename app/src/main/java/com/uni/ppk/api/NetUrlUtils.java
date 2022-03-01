package com.uni.ppk.api;

import com.uni.commoncore.utils.StringUtils;

/**
 * 功能:APP接口类
 */
public class NetUrlUtils {
    //该项目接口命名，根据后台接口的真实地址，全参数命名
//    public static String BASEURL = "http://47.93.233.254:8081/api/";
    public static String BASEURL = "https://hello.pinpinkan.vip/pinpinkan-app/api/v1/";

    /**
     * 图片地址拼接
     *
     * @param srcUrl
     * @return
     */
    public static String createPhotoUrl(String srcUrl) {
        if (StringUtils.isEmpty(srcUrl)) {
            return "";
        }
        if (srcUrl.startsWith("http")) {
            return srcUrl;
        } else {
            return BASEURL + srcUrl;
        }
    }

    //上传图片
    public static String UPLOAD_PHOTO = BASEURL + "common/uploadImageAli";

    //用户注册协议
    public static String AGREE_REGISTER = "http://zx.zqsmoney.com:8082/regAgreement/xy.html";

    //邀请链接
    public static String QR_CODE_SHARE = "https://api.suwenlawyer.com/user/share/index?invite_code=";

}
