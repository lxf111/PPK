package com.uni.ppk.ui.home.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 14:27
 */
public class AdviserBean implements Serializable {
    /**
     * arealist_id : 130683000000-1
     * member_price : 44
     * price : 55
     * title : 33
     */
    private String arealist_id;
    private String member_price;
    private String content;
    private String price;
    private String title;
    private String background;
    private boolean isSelect;
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getArealist_id() {
        return arealist_id;
    }

    public void setArealist_id(String arealist_id) {
        this.arealist_id = arealist_id;
    }

    public String getMember_price() {
        return member_price;
    }

    public void setMember_price(String member_price) {
        this.member_price = member_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
