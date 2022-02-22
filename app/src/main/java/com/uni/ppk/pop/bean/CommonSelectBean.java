package com.uni.ppk.pop.bean;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/11/6
 * Time: 11:59
 */
public class CommonSelectBean {

    /**
     * id : 110000000000
     * name : 北京市
     */

    private String id;
    private String name;
    private String cityname;
    private String rate;
    private String price;
    private String memberPrice;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String text;
    private String title;
    private String value;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
