package com.uni.ppk.ui.message.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/29
 * Time: 14:48
 */
public class LastMessageBean implements Serializable {

    /**
     * count : 74
     * latest_title : 订单取消通知
     */

    private int count;
    private String latest_title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLatest_title() {
        return latest_title;
    }

    public void setLatest_title(String latest_title) {
        this.latest_title = latest_title;
    }
}
