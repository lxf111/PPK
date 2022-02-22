package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/9
 * Time: 15:05
 */
public class LawyerDetailBean implements Serializable {

    /**
     * user_name : 迷童
     * law_firm : 江南律师事务所
     * practice_num : 295575949156
     * lawyer_certification : 2
     * score : 0.0
     * case_num : 1
     * introduction : 正则律师事务所高级律师
     * service : [{"price":"14.00","name":"代写文书"},{"price":"15.00","name":"案件诉讼(民事)"},{"price":"100.00","name":"婚姻家庭"}]
     * lawyer_case : {"order_sn":"LSS20201007172446623056","title":"法律咨询-婚姻家庭","thumb":"http://api.suwenlawyer.com/static/admin/images/none.png","content":"555"}
     */

    private String user_name;
    private String law_firm;
    private String practice_num;
    private String head_img;
    private String mobile;
    private String city;
    private int lawyer_certification;
    private int practice_years;
    private String score;
    private int case_num;
    private String introduction;
    private LawyerCaseBean lawyer_case;
    private List<ServiceTypeBean> service;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public int getPractice_years() {
        return practice_years;
    }

    public void setPractice_years(int practice_years) {
        this.practice_years = practice_years;
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

    public String getPractice_num() {
        return practice_num;
    }

    public void setPractice_num(String practice_num) {
        this.practice_num = practice_num;
    }

    public int getLawyer_certification() {
        return lawyer_certification;
    }

    public void setLawyer_certification(int lawyer_certification) {
        this.lawyer_certification = lawyer_certification;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getCase_num() {
        return case_num;
    }

    public void setCase_num(int case_num) {
        this.case_num = case_num;
    }

    public String getIntroduction() {
        return introduction == null ? "" : introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public LawyerCaseBean getLawyer_case() {
        return lawyer_case;
    }

    public void setLawyer_case(LawyerCaseBean lawyer_case) {
        this.lawyer_case = lawyer_case;
    }

    public List<ServiceTypeBean> getService() {
        return service;
    }

    public void setService(List<ServiceTypeBean> service) {
        this.service = service;
    }

}
