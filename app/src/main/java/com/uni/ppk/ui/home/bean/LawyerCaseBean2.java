package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 15:59
 */
public class LawyerCaseBean2 implements Serializable {

    /**
     * id : 7
     * lawyer_id : 38
     * title : 案例标题
     * content : 就斤斤计较
     * imgs : ["http://127.0.0.1/uploads/images/91/7253468378f3161d84d664df63766b.jpeg"]
     * create_time : 2020-10-21 17:24:16
     * update_time : 2020-10-21 17:24:16
     * user_name : 用户12228
     * head_img : http://127.0.0.1/uploads/images/91/7253468378f3161d84d664df63766b.jpeg
     */

    private int id;
    private int lawyer_id;
    private String title;
    private String content;
    private String create_time;
    private String update_time;
    private String user_name;
    private String head_img;
    private List<String> imgs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(int lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
