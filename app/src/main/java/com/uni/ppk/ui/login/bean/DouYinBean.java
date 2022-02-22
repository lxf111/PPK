package com.uni.ppk.ui.login.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/18
 * Time: 9:14
 */
public class DouYinBean implements Serializable {
    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
