package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/8
 * Time: 9:43
 */
public class OrderEarnestBean implements Serializable {

    /**
     * prices : {"0":{"type":"default","member_price":"70000.00","price":"80000.00"},"1":{"type":"area","member_price":"1000.00","price":"2000.00"},"2":{"type":"money","field":"money","value":"123444","member_price":"20","price":"30"},"default":{"member_price":"70000.00","price":"80000.00"}}
     * sum_price : {"member_price":"141020.00","price":"162030.00"}
     * is_vip : 0
     * earnest : {"earnest":"10","member_price":"14102.00","price":"16203.00"}
     * earnest_list : [{"earnest":"10","member_price":"14102.00","price":"16203.00"},{"earnest":"20","member_price":"28204.00","price":"32406.00"},{"earnest":"30","member_price":"42306.00","price":"48609.00"}]
     */

    private SumPriceBean sum_price;
    private int is_vip;
    private EarnestBean earnest;
    private List<EarnestListBean> earnest_list;

    public SumPriceBean getSum_price() {
        return sum_price;
    }

    public void setSum_price(SumPriceBean sum_price) {
        this.sum_price = sum_price;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public EarnestBean getEarnest() {
        return earnest;
    }

    public void setEarnest(EarnestBean earnest) {
        this.earnest = earnest;
    }

    public List<EarnestListBean> getEarnest_list() {
        return earnest_list;
    }

    public void setEarnest_list(List<EarnestListBean> earnest_list) {
        this.earnest_list = earnest_list;
    }

    public static class SumPriceBean implements Serializable{
        /**
         * member_price : 141020.00
         * price : 162030.00
         */

        private String member_price;
        private String price;

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
    }

    public static class EarnestBean implements Serializable{
        /**
         * earnest : 10
         * member_price : 14102.00
         * price : 16203.00
         */

        private String earnest;
        private String member_price;
        private String price;

        public String getEarnest() {
            return earnest;
        }

        public void setEarnest(String earnest) {
            this.earnest = earnest;
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
    }

    public static class EarnestListBean implements Serializable{
        /**
         * earnest : 10
         * member_price : 14102.00
         * price : 16203.00
         */

        private String earnest;
        private String member_price;
        private String price;

        public String getEarnest() {
            return earnest;
        }

        public void setEarnest(String earnest) {
            this.earnest = earnest;
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
    }
}
