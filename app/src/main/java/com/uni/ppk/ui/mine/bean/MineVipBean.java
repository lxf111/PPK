package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/27
 * Time: 15:34
 */
public class MineVipBean implements Serializable {

    /**
     * aid : 2
     * name : 企业会员
     * background : https://api.suwenlawyer.com/uploads/images/none.png
     * logo : https://api.suwenlawyer.com/static/admin/images/none.png
     * price : 0.01
     * service : [{"id":5,"vipid":2,"name":"享受全年法律咨询，全网消费享受8折优惠"}]
     */

    private int aid;
    private String name;
    private String background;
    private String logo;
    private String price;
    private List<VipProfitBean> service;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<VipProfitBean> getService() {
        return service;
    }

    public void setService(List<VipProfitBean> service) {
        this.service = service;
    }
}
