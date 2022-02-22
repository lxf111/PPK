package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/28
 * Time: 18:10
 */
public class EvaluateLabelBean implements Serializable {
    private boolean isSelect;
    /**
     * id : 1
     * name : 非常专业
     * create_time : 2020-09-10 16:44:37
     * update_time : 2020-09-10 16:44:37
     */

    private int id;
    private String name;
    private String create_time;
    private String update_time;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}


