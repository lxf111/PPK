package com.uni.ppk.ui.message.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/12/7
 * Time: 9:32
 */
public class ChatMessageBean implements Serializable {

    /**
     * id : 122
     * user_nickname : 峰168
     * head_img : https://suwenlawyer.oss-cn-huhehaote.aliyuncs.com/uploads/images/32/1ef43c9e31dbe589eea5126d3a4ad3.jpeg
     */

    private int id;
    private String user_nickname;
    private String head_img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
}
