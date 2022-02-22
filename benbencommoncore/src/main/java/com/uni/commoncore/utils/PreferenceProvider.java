package com.uni.commoncore.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by caojieting on 2017/12/15.
 */

public class PreferenceProvider {
    private Context context;
    /**
     * 保存在手机里面的文件名
     */
    public final String SP_NAME = "empty_config";
    public final int SP_MODE = Context.MODE_PRIVATE;
    private SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public PreferenceProvider(Context context) {
        this.context = context;
    }


    public void setIsCompleteInfo(boolean photo) {
        put(context, "isCompleteInfo", photo);
    }

    public boolean getIsCompleteInfo() {//是否完善个人信息
        return (boolean) get(context, "isCompleteInfo", false);
    }

    public void setIsCompleteCar(boolean photo) {
        put(context, "isCompleteCar", photo);
    }

    public boolean getIsCompleteCar() {//是否完善汽车信息
        return (boolean) get(context, "isCompleteCar", false);
    }

    public void setPhoto(String photo) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("photo", photo);
//        editor.commit();
        put(context, "photo", photo);
    }

    public String getPhoto() {//头像
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);

//        return spf.getString("photo", "");
        return (String) get(context, "photo", "");
    }

    public void setUserName(String username) {
        put(context, "username", username);
    }

    public String getUserName() {//昵称
        return (String) get(context, "username", "");
    }

    public void setLevel(String level) {
        put(context, "level", "" + level);
    }

    public String getLevel() {//会员等级
        return (String) get(context, "level", "0");
    }

    public void setIsAgree(String photo) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("agree", photo);
//        editor.commit();
        put(context, "agree", photo);
    }

    public String getIsAgree() {//是否同意协议 同意为1 否则为没有同意
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("agree", "");
        return (String) get(context, "agree", "");
    }

    public void setCityCode(String username) {
        put(context, "cityCode", username);
    }

    public String getCityCode() {//城市code
        return (String) get(context, "cityCode", "1756");
    }

    public void setInvitationCode(String username) {
        put(context, "invitationCode", username);
    }

    public String getInvitationCode() {
        return (String) get(context, "invitationCode", "1756");
    }

    public void setLatitude(String username) {//纬度
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("Latitude", username);
//        editor.commit();
        put(context, "Latitude", username);
    }

    public String getLatitude() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("Latitude", "");
        return (String) get(context, "Latitude", "");
    }

    public void setIsBindWx(String pwd) {//是否绑定微信
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("bindWx", pwd);
//        editor.commit();
        put(context, "bindWx", pwd);
    }


    public String getIsBindWx() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("bindWx", "");
        return (String) get(context, "bindWx", "");
    }

    public void setRealAuth(String pwd) {//是否实名认证
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("realAuth", pwd);
//        editor.commit();
        put(context, "realAuth", pwd);
    }

    public String getRealAuth() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("realAuth", "");
        return (String) get(context, "realAuth", "");
    }

    public void setHxName(String pwd) {//环信账号
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("hxName", pwd);
//        editor.commit();
        put(context, "hxName", pwd);
    }

    public String getHxName() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("hxName", "");
        return (String) get(context, "hxName", "");
    }

    public void setHxPwd(String pwd) {//环信密码
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("hxPwd", pwd);
//        editor.commit();
        put(context, "hxPwd", pwd);
    }

    public String getHxPwd() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("hxPwd", "123456");
        return (String) get(context, "hxPwd", "123456");
    }

    public void setKFAccount(String pwd) {//客服账号
        put(context, "kfAccount", pwd);
    }

    public String getKFAccount() {
        return (String) get(context, "kfAccount", "123456");
    }

    public void setKFName(String pwd) {//客服昵称
        put(context, "kfName", pwd);
    }

    public String getKFName() {
        return (String) get(context, "kfName", "123456");
    }

    public void setKFHeader(String pwd) {//客服头像
        put(context, "kfHeader", pwd);
    }

    public String getKFHeader() {
        return (String) get(context, "kfHeader", "");
    }

    public void setIsPayPwd(String pwd) {//是否设置支付密码 是否设置支付支付密码 1已设置 0未设置
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("isPayPwd", pwd);
//        editor.commit();
        put(context, "isPayPwd", pwd);
    }

    public String getIsPayPwd() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("isPayPwd", "");
        return (String) get(context, "isPayPwd", "");
    }

    public void setAddress(String address) {//纬度
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("address", address);
//        editor.commit();
        put(context, "address", address);
    }

    public String getAddress() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("address", "");
        return (String) get(context, "address", "");
    }

    public void setLocationAddress(String address) {//纬度
        put(context, "locationAddress", address);
    }

    public String getLocationAddress() {
        return (String) get(context, "locationAddress", "");
    }

    public void setLongitude(String username) {//经度
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("Longitude", username);
//        editor.commit();
        put(context, "Longitude", username);
    }

    public String getLongitude() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("Longitude", "");
        return (String) get(context, "Longitude", "");
    }

    public void setId(String id) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("id", id);
//        editor.commit();
        put(context, "id", id);
    }

    public String getId() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("id", "");
        return (String) get(context, "id", "");
    }

    public void setUId(String uid) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("uid", uid);
//        editor.commit();
        put(context, "uid", uid);
    }

    public String getUId() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("uid", "0");
        return (String) get(context, "uid", "0");
    }

    public void setUserType(String userType) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("userType", userType);
//        editor.commit();
        put(context, "userType", userType);
    }

    public String getUserType() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("userType", "");
        return (String) get(context, "userType", "");
    }

    public void setRceiver(String userType) {//是否接单 1接单0不接单
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("receiver", userType);
//        editor.commit();
        put(context, "receiver", userType);
    }

    public String getReceiver() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("receiver", "");
        return (String) get(context, "receiver", "");
    }

    public void setUserTypeId(int userType) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putInt("user_type_id", userType);
//        editor.commit();
        put(context, "user_type_id", userType);
    }

    public int getUserTypeId() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getInt("user_type_id", 0);
        return (int) get(context, "user_type_id", 0);
    }

    public void setMobile(String mobile) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("mobile", mobile);
//        editor.commit();
        put(context, "mobile", mobile);
    }

    public String getMobile() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("mobile", "");
        return (String) get(context, "mobile", "");
    }

    public void setShopId(String shop_id) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("shop_id", shop_id);
//        editor.commit();
        put(context, "shop_id", shop_id);
    }

    public String getShopId() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("shop_id", "");
        return (String) get(context, "shop_id", "");
    }

    public void setIdNumber(String id_number) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("id_number", id_number);
//        editor.commit();
        put(context, "id_number", id_number);
    }

    public String getIdNumber() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("id_number", "");
        return (String) get(context, "id_number", "");
    }


    public void setPwd(String pwd) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("pwd", pwd);
//        editor.commit();
        put(context, "pwd", pwd);
    }

    public String getPwd() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("pwd", "");
        return (String) get(context, "pwd", "");
    }

    public void setIMUserName(String name) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("im_user_name", name);
//        editor.commit();
        put(context, "im_user_name", name);
    }

    public String getIMUserName() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("im_user_name", "");
        return (String) get(context, "im_user_name", "");
    }

    public void setIMJurisdiction(String permisson) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("im_permission", permisson);
//        editor.commit();
        put(context, "im_permission", permisson);
    }

    public String getIMJurisdiction() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("im_permission", "");
        return (String) get(context, "im_permission", "");
    }


    public void setToken(String token) {
        put(context, "token", token);
    }

    public String getToken() {
        return (String) get(context, "token", "");
    }

    public void setLastTime(String token) {
        put(context, "lastTime", token);
    }

    public String getLastTime() {
        return (String) get(context, "lastTime", "");
    }

    //省份
    public void setProvince(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("province", token);
//        editor.commit();
        put(context, "province", token);
    }

    public String getProvince() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("province", "");
        return (String) get(context, "province", "");
    }

    //城市
    public void setCity(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("city", token);
//        editor.commit();
        put(context, "city", token);
    }

    public String getCity() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("city", "郑州市");
        return (String) get(context, "city", "");
    }

    //地区
    public void setDistrict(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("district", token);
//        editor.commit();
        put(context, "district", token);
    }

    public String getDistrict() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("district", "");
        return (String) get(context, "district", "");
    }

    //公司名称
    public void setCompanyName(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("company_name", token);
//        editor.commit();
        put(context, "company_name", token);
    }

    public String getCompanyName() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("company_name", "");
        return (String) get(context, "company_name", "");
    }

    //纳税人识别号
    public void setCompanyCode(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("company_code", token);
//        editor.commit();
        put(context, "company_code", token);
    }

    public String getCompanyCode() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("company_code", "");
        return (String) get(context, "company_code", "");
    }

    //真实姓名
    public void setRealName(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("real_name", token);
//        editor.commit();
        put(context, "real_name", token);
    }

    public String getRealName() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("real_name", "");
        return (String) get(context, "real_name", "");
    }

    //联系方式
    public void setRealPhone(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("real_phone", token);
//        editor.commit();
        put(context, "real_phone", token);
    }

    public String getRealPhone() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("real_phone", "");
        return (String) get(context, "real_phone", "");
    }

    //收货地址
    public void setRealAddress(String token) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putString("real_address", token);
//        editor.commit();
        put(context, "real_address", token);
    }

    public String getRealAddress() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getString("real_address", "");
        return (String) get(context, "real_address", "");
    }

    //声音通知
    public void setNotifySound(Boolean isClose) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putBoolean("notify_sound", isClose);
//        editor.commit();
        put(context, "notify_sound", isClose);
    }

    public boolean getNotifySound() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getBoolean("notify_sound", true);
        return (boolean) get(context, "notify_sound", true);
    }

    //震动通知
    public void setNotifyVibrate(Boolean isClose) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putBoolean("notify_vibrate", isClose);
//        editor.commit();
        put(context, "notify_vibrate", isClose);
    }

    public boolean getNotifyVibrate() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getBoolean("notify_vibrate", true);
        return (boolean) get(context, "notify_vibrate", true);
    }

    //开启支付密码
    public void setClosePayPwd(Boolean isClose) {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = spf.edit();
//        editor.putBoolean("close_pwd", isClose);
//        editor.commit();
        put(context, "close_pwd", isClose);
    }

    public boolean getClosePayPwd() {
//        SharedPreferences spf = PreferenceManager
//                .getDefaultSharedPreferences(context);
//        return spf.getBoolean("close_pwd", true);
        return (boolean) get(context, "close_pwd", true);
    }

    /**
     * 根据类型调用不同的保存方法
     *
     * @param context 上下文
     * @param key     添加的键
     * @param value   添加的值
     * @return 是否添加成功（可以使用apply提交）
     */
    public boolean put(Context context, String key, Object value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, SP_MODE);
        }
        editor = sp.edit();
        if (value == null) {
            editor.putString(key, null);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        return editor.commit();
    }


    public Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                SP_MODE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


}
