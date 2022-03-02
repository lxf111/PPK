package com.uni.ppk.ui.login.bean;

import com.google.gson.annotations.SerializedName;

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
    private int age;
    private String authMobile;
    private int authStatus;
    private int authType;
    private String avatar;
    private String backCardId;
    private String cardId;
    private String companyAddress;
    private String companyCreditCode;
    private String companyLegal;
    private String companyLicense;
    private String companyName;
    private String companyScale;
    private String createBy;
    private String createTime;
    private int delFlag;
    private String frontCardId;
    private String mobileEncrypt;
    private String nation;
    private String nickname;
    private String password;
    private int passwordSecurityLevel;
    private String salt;
    private int status;
    private String token;
    private String updateBy;
    private Object updateTime;
    private String username;
    private int workYear;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAuthMobile() {
        return authMobile;
    }

    public void setAuthMobile(String authMobile) {
        this.authMobile = authMobile;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackCardId() {
        return backCardId;
    }

    public void setBackCardId(String backCardId) {
        this.backCardId = backCardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCreditCode() {
        return companyCreditCode;
    }

    public void setCompanyCreditCode(String companyCreditCode) {
        this.companyCreditCode = companyCreditCode;
    }

    public String getCompanyLegal() {
        return companyLegal;
    }

    public void setCompanyLegal(String companyLegal) {
        this.companyLegal = companyLegal;
    }

    public String getCompanyLicense() {
        return companyLicense;
    }

    public void setCompanyLicense(String companyLicense) {
        this.companyLicense = companyLicense;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyScale() {
        return companyScale;
    }

    public void setCompanyScale(String companyScale) {
        this.companyScale = companyScale;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getFrontCardId() {
        return frontCardId;
    }

    public void setFrontCardId(String frontCardId) {
        this.frontCardId = frontCardId;
    }

    public String getMobileEncrypt() {
        return mobileEncrypt;
    }

    public void setMobileEncrypt(String mobileEncrypt) {
        this.mobileEncrypt = mobileEncrypt;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPasswordSecurityLevel() {
        return passwordSecurityLevel;
    }

    public void setPasswordSecurityLevel(int passwordSecurityLevel) {
        this.passwordSecurityLevel = passwordSecurityLevel;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWorkYear() {
        return workYear;
    }

    public void setWorkYear(int workYear) {
        this.workYear = workYear;
    }
}
