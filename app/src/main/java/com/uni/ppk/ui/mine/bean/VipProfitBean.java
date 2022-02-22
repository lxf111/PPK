package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/17
 * Time: 15:45
 */
public class VipProfitBean implements Serializable {
    /**
     * id : 5
     * vipid : 2
     * name : 享受全年法律咨询，全网消费享受8折优惠
     */

    private int id;
    private int vipid;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVipid() {
        return vipid;
    }

    public void setVipid(int vipid) {
        this.vipid = vipid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
