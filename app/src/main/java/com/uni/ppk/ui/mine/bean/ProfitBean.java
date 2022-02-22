package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/24
 * Time: 17:22
 */
public class ProfitBean implements Serializable {

    /**
     * id : 4
     * order_sn : SW20200923204941492107
     * from_id : 3
     * user_id : 38
     * agent_id : 0
     * partner_id : 3
     * money : 1000.00
     * rebate : 1
     * rebate_money : 10.00
     * remark : 购买课程
     * create_time : 2020-09-23 20:40:37
     * update_time : 2020-09-23 20:40:37
     * from : {"id":3,"user_name":"用户20812","mobile":"13193835002","head_img":"http://api.suwenlawyer.com/static/admin/images/none.png"}
     */

    private int id;
    private String order_sn;
    private int from_id;
    private int user_id;
    private int agent_id;
    private int partner_id;
    private String money;
    private int rebate;
    private String rebate_money;
    private String remark;
    private String create_time;
    private String update_time;
    private FromBean from;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getRebate() {
        return rebate;
    }

    public void setRebate(int rebate) {
        this.rebate = rebate;
    }

    public String getRebate_money() {
        return rebate_money;
    }

    public void setRebate_money(String rebate_money) {
        this.rebate_money = rebate_money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public FromBean getFrom() {
        return from;
    }

    public void setFrom(FromBean from) {
        this.from = from;
    }

    public static class FromBean {
        /**
         * id : 3
         * user_name : 用户20812
         * mobile : 13193835002
         * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
         */

        private int id;
        private String user_name;
        private String user_nickname;
        private String mobile;
        private String head_img;

        public String getUser_nickname() {
            return user_nickname == null ? "" : user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
