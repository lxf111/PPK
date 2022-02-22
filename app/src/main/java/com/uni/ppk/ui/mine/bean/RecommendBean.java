package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 11:41
 */
public class RecommendBean implements Serializable {

    /**
     * user_name : 用户30426
     * create_time : 2020-10-15 13:56:10
     * head_img : http://api.suwenlawyer.com/uploads/images/none.png
     * id : 26
     */

    private String user_name;
    private String create_time;
    private String head_img;
    private int id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
