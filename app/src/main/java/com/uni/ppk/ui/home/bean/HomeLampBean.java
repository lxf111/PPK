package com.uni.ppk.ui.home.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 15:58
 */
public class HomeLampBean implements Serializable {

    /**
     * ask_user : 用户45874
     * answer_user : 迷童
     * content : 迷童抢答了用户45874的问题
     * answer_head_img : http://api.suwenlawyer.com/static/admin/images/none.png
     */

    private String ask_user;
    private String answer_user;
    private String content;
    private String answer_head_img;

    public String getAsk_user() {
        return ask_user;
    }

    public void setAsk_user(String ask_user) {
        this.ask_user = ask_user;
    }

    public String getAnswer_user() {
        return answer_user;
    }

    public void setAnswer_user(String answer_user) {
        this.answer_user = answer_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer_head_img() {
        return answer_head_img;
    }

    public void setAnswer_head_img(String answer_head_img) {
        this.answer_head_img = answer_head_img;
    }
}
