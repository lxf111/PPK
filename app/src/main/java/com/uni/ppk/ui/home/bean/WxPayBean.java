package com.uni.ppk.ui.home.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/11/2
 * Time: 10:57
 */
public class WxPayBean {

    /**
     * appid : wxb686f5dd4e432c4e
     * partnerid : 1602646113
     * prepayid : wx15103236586687019bcd163e1a7cd40000
     * package : Sign=WXPay
     * noncestr : mk4i61idrshbc9rlcrpic2r72pqm5a1a
     * timestamp : 1602729156
     * sign : 96D7EA473BAB6DB6A5EE90DDB33A6415
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private String timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
