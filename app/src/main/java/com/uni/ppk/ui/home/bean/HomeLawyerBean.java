package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:24
 */
public class HomeLawyerBean implements Serializable {

    /**
     * id : 6
     * user_name : 用户29557
     * law_firm : 江南律师事务所
     * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
     * city : 郑州市
     * service : [{"name":"代写文书"},{"name":"案件诉讼(民事)"}]
     */

    private int id;
    private String user_name;
    private String law_firm;
    private String head_img;
    private String city;
    private List<LabelBean> service;

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

    public String getLaw_firm() {
        return law_firm;
    }

    public void setLaw_firm(String law_firm) {
        this.law_firm = law_firm;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<LabelBean> getService() {
        return service;
    }

    public void setService(List<LabelBean> service) {
        this.service = service;
    }

}
