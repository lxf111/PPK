package com.uni.ppk.ui.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 14:16
 */
public class EvaluateBean implements Serializable {

    /**
     * id : 11
     * associate_id : 18
     * pid : 0
     * user_type : 1
     * user_id : 6
     * content : 你好
     * create_time : 2020-10-12 15:58:18
     * update_time : 2020-10-12 15:58:18
     * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
     * user_nickname : 用户46632
     * reply : []
     */

    private int id;
    private int associate_id;
    private int pid;
    private int user_type;
    private int user_id;
    private int sex;
    private String content;
    private String create_time;
    private String update_time;
    private String head_img;
    private String user_nickname;
    private List<ReplyBean> reply;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssociate_id() {
        return associate_id;
    }

    public void setAssociate_id(int associate_id) {
        this.associate_id = associate_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }
}
