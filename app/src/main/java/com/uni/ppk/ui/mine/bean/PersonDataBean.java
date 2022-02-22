package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/14
 * Time: 16:01
 */
public class PersonDataBean implements Serializable {

    /**
     * id : 6
     * user_name : 用户46632
     * user_nickname : 用户46632
     * mobile : 181****6406
     * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
     * sex : 0
     * invite_code : SW006
     * user_money :
     * user_virtual_money :
     * freeze_money :
     * score :
     * user_type :
     * status : 1
     * age : 50
     * address :
     * client_id :
     */

    private int id;
    private String user_name;
    private String user_nickname;
    private String motto;
    private String birthday;
    private String mobile;
    private String head_img;
    private int sex;
    private String invite_code;
    private String user_money;
    private String user_virtual_money;
    private String freeze_money;
    private String score;
    private String user_type;
    private int status;
    private int age;
    private String address;
    private String user_vip;
    private String lawyer_vip;
    private String client_id;

    public String getUser_vip() {
        return user_vip == null ? "0" : user_vip;
    }

    public void setUser_vip(String user_vip) {
        this.user_vip = user_vip;
    }

    public String getLawyer_vip() {
        return lawyer_vip;
    }

    public void setLawyer_vip(String lawyer_vip) {
        this.lawyer_vip = lawyer_vip;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMotto() {
        return motto == null ? "" : motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getUser_virtual_money() {
        return user_virtual_money;
    }

    public void setUser_virtual_money(String user_virtual_money) {
        this.user_virtual_money = user_virtual_money;
    }

    public String getFreeze_money() {
        return freeze_money;
    }

    public void setFreeze_money(String freeze_money) {
        this.freeze_money = freeze_money;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
