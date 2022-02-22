package com.uni.ppk.ui.login.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/6
 * Time: 16:07
 */
public class LoginBean implements Serializable {

    /**
     * user_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJaQlBIUCIsImlhdCI6MTYwMTk3MTQ2NiwibmJmIjoxNjAxOTcxNDY2LCJzY29wZXMiOiJyb2xlX2FjY2VzcyIsImV4cCI6MTYwNDU2MzQ2NiwicGFyYW1zIjp7ImlkIjo2LCJ1c2VyX25hbWUiOiJcdTc1MjhcdTYyMzc0NjYzMiIsInVzZXJfbmlja25hbWUiOiJcdTc1MjhcdTYyMzc0NjYzMiIsInN0YXR1cyI6MSwiaGVhZF9pbWciOiJodHRwOlwvXC9hcGkuc3V3ZW5sYXd5ZXIuY29tXC9zdGF0aWNcL2FkbWluXC9pbWFnZXNcL25vbmUucG5nIiwic2V4IjowLCJ1c2VyX2xldmVsIjowLCJtb2JpbGUiOiIxODEzNDQxNjQwNiIsImNsaWVudF9pZCI6bnVsbH19.djEIRKS3tg90BGfqU6Rp9F9mvj8JUYJE4owpa9VuQMk
     * id : 6
     * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
     * user_name : 用户46632
     * user_nickname : 用户46632
     * sex : 0
     * user_level : 0
     * mobile : 181****6406
     * city :
     * city_id :
     * client_id : null
     */

    private String user_token;
    private int id;
    private String head_img;
    private String vip;
    private String user_vip;
    private String user_name;
    private String user_nickname;
    private int sex;
    private int user_level;
    private String mobile;
    private String city;
    private String city_id;
    private String client_id;

    public String getUser_vip() {
        return user_vip;
    }

    public void setUser_vip(String user_vip) {
        this.user_vip = user_vip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
