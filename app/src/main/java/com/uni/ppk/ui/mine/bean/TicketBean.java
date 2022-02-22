package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/13
 * Time: 17:56
 */
public class TicketBean implements Serializable {

    /**
     * id : 1
     * user_id : 6
     * name : 发票名称
     * tax_num : 591385189448
     * address : 地址
     * phone : 18496646467
     * account_bank : 开户行及账户
     * email : 15454646464646@qq.com
     * remark : null
     * way : user
     * create_time : 2020-09-24 17:20:08
     * update_time : 2020-09-24 17:20:08
     */

    private int id;
    private int user_id;
    private String name;
    private String tax_num;
    private String address;
    private String phone;
    private String account_bank;
    private String email;
    private Object remark;
    private String way;
    private String create_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTax_num() {
        return tax_num;
    }

    public void setTax_num(String tax_num) {
        this.tax_num = tax_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
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
}
