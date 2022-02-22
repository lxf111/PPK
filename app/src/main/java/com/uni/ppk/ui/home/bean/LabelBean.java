package com.uni.ppk.ui.home.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:33
 */
public class LabelBean implements Serializable {

    private int id;
    private int lawfirm_id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawfirm_id() {
        return lawfirm_id;
    }

    public void setLawfirm_id(int lawfirm_id) {
        this.lawfirm_id = lawfirm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
