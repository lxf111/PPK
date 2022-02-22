package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 8:48
 */
public class VipListBean implements Serializable {


    /**
     * aid : 1
     * name : 个人会员
     * background : http://api.suwenlawyer.com/uploads/images/none.png
     * logo : http://api.suwenlawyer.com/uploads/images/none.png
     * price : 10.00
     * service : [{"id":4,"vipid":1,"name":"享受全年法律咨询r，全网消费享受8折优惠"}]
     */

    private int aid;
    private String name;
    private String background;
    private String logo;
    private String price;
    private List<ServiceBean> service;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

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

    public List<ServiceBean> getService() {
        return service;
    }

    public void setService(List<ServiceBean> service) {
        this.service = service;
    }

    public static class ServiceBean {
        /**
         * id : 4
         * vipid : 1
         * name : 享受全年法律咨询r，全网消费享受8折优惠
         */

        private int id;
        private int vipid;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVipid() {
            return vipid;
        }

        public void setVipid(int vipid) {
            this.vipid = vipid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
