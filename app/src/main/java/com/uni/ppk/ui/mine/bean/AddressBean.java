package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: whx
 * Date: 2019/11/2 0002
 * Time: 10:15
 * 收货地址的Bean
 */
public class AddressBean implements Serializable {
    /**
     * addressLabel :
     * areac : 天津市
     * areap : 天津市
     * areax : 和平区
     * createBy : jeecg
     * createTime : 2019-11-1210:05:16
     * defaultFlag : 1
     * delFlag : 1
     * detailedAddress : 金水
     * id : 9e3933781c0981615ee1a3cf45b7faa7
     * lat : 0
     * lng : 0
     * reciverName : 王
     * reciverTelephone : 15538679968
     * sex :
     * updateBy :
     * updateTime : null
     * userId : 6b18753d54e740c4517c050f05c0fa50
     */

    private String addressLabel;
    private String areac;
    private String areap;
    private String areax;
    private String createBy;
    private String createTime;
    private String defaultFlag;
    private String delFlag;
    private String detailedAddress;
    private String id;
    private int lat;
    private int lng;
    private String reciverName;
    private String reciverTelephone;
    private String sex;
    private String updateBy;
    private Object updateTime;
    private String userId;

    public String getAddressLabel() {
        return addressLabel;
    }

    public void setAddressLabel(String addressLabel) {
        this.addressLabel = addressLabel;
    }

    public String getAreac() {
        return areac;
    }

    public void setAreac(String areac) {
        this.areac = areac;
    }

    public String getAreap() {
        return areap;
    }

    public void setAreap(String areap) {
        this.areap = areap;
    }

    public String getAreax() {
        return areax;
    }

    public void setAreax(String areax) {
        this.areax = areax;
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

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getReciverTelephone() {
        return reciverTelephone;
    }

    public void setReciverTelephone(String reciverTelephone) {
        this.reciverTelephone = reciverTelephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
