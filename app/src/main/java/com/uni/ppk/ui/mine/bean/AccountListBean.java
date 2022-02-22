package com.uni.ppk.ui.mine.bean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/14
 * Time: 18:04
 */
public class AccountListBean {

    /**
     * bal_total :
     * bal_year :
     * bal_month :
     * bill_list : [{"bal_title":"","change_money":"","change_time":""},{"bal_title":"","change_money":"","change_time":""}]
     */

    private String bal_total;
    private String bal_year;
    private String bal_month;
    private List<BillListBean> bill_list;

    public String getBal_total() {
        return bal_total;
    }

    public void setBal_total(String bal_total) {
        this.bal_total = bal_total;
    }

    public String getBal_year() {
        return bal_year;
    }

    public void setBal_year(String bal_year) {
        this.bal_year = bal_year;
    }

    public String getBal_month() {
        return bal_month;
    }

    public void setBal_month(String bal_month) {
        this.bal_month = bal_month;
    }

    public List<BillListBean> getBill_list() {
        return bill_list;
    }

    public void setBill_list(List<BillListBean> bill_list) {
        this.bill_list = bill_list;
    }

    public static class BillListBean {
        /**
         * bal_title :
         * change_money :
         * change_time :
         */

        private String bal_title;
        private String change_money;
        private String change_time;

        public String getBal_title() {
            return bal_title;
        }

        public void setBal_title(String bal_title) {
            this.bal_title = bal_title;
        }

        public String getChange_money() {
            return change_money;
        }

        public void setChange_money(String change_money) {
            this.change_money = change_money;
        }

        public String getChange_time() {
            return change_time;
        }

        public void setChange_time(String change_time) {
            this.change_time = change_time;
        }
    }
}
