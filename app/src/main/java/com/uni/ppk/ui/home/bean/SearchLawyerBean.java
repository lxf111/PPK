package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 17:27
 */
public class SearchLawyerBean implements Serializable {

    /**
     * lawyer_id : 6
     * price : 14.00
     * user_name : 迷童
     * law_firm : 江南律师事务所
     * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
     * city : 郑州市
     * case_num : 1
     * score : 0.0
     * service : [{"price":"14.00","name":"代写文书"},{"price":"15.00","name":"案件诉讼(民事)"}]
     */

    private int lawyer_id;
    private String price;
    private String user_name;
    private String law_firm;
    private String head_img;
    private String city;
    private int case_num;
    private String score;
    private List<LabelBean> service;

    public int getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(int lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public int getCase_num() {
        return case_num;
    }

    public void setCase_num(int case_num) {
        this.case_num = case_num;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<LabelBean> getService() {
        return service;
    }

    public void setService(List<LabelBean> service) {
        this.service = service;
    }

}
