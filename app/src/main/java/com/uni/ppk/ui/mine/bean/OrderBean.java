package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:08
 */
public class OrderBean implements Serializable {

    /**
     * lawyer_id : 0
     * thumb : http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg
     * city : 市辖区
     * atlas : ["http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg"]
     * top_id : 14
     * order_money : 300.00
     * reply_atlas :
     * title : 法务110
     * body : [{"name":"悬赏金额","value":"300","key":"rewards"}]
     * content : 1233
     * province : 北京市
     * user_nickname : 用户46632
     * id : 32
     * reply :
     * email : 12344292982@qq.com
     * son_id : 0
     * create_time : 2020-10-09 10:53:49
     * payable_money : 300.00
     * head_img : http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg
     * real_money : 0.00
     * shipping_status : 0
     * phone : 19664646649
     * user_id : 6
     * district : 丰台区
     * lawyer_img :
     * order_sn : LSS20201009105349839729
     * lawyer_nick_name : 用户46632
     */

    private int lawyer_id;
    private String thumb;
    private String city;
    private int top_id;
    private String order_money;
    private String reply_atlas;
    private String title;
    private String content;
    private String province;
    private String user_nickname;
    private int id;
    private String reply;
    private String email;
    private int son_id;
    private String create_time;
    private String payable_money;
    private String head_img;
    private String real_money;
    private int shipping_status;
    private String phone;
    private int user_id;
    private String district;
    private String lawyer_img;
    private String order_sn;
    private String lawyer_nick_name;
    private List<String> atlas;
    private List<BodyBean> body;

    public int getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(int lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTop_id() {
        return top_id;
    }

    public void setTop_id(int top_id) {
        this.top_id = top_id;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getReply_atlas() {
        return reply_atlas;
    }

    public void setReply_atlas(String reply_atlas) {
        this.reply_atlas = reply_atlas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUser_nickname() {
        return user_nickname;
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSon_id() {
        return son_id;
    }

    public void setSon_id(int son_id) {
        this.son_id = son_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPayable_money() {
        return payable_money;
    }

    public void setPayable_money(String payable_money) {
        this.payable_money = payable_money;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getReal_money() {
        return real_money;
    }

    public void setReal_money(String real_money) {
        this.real_money = real_money;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLawyer_img() {
        return lawyer_img;
    }

    public void setLawyer_img(String lawyer_img) {
        this.lawyer_img = lawyer_img;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getLawyer_nick_name() {
        return lawyer_nick_name;
    }

    public void setLawyer_nick_name(String lawyer_nick_name) {
        this.lawyer_nick_name = lawyer_nick_name;
    }

    public List<String> getAtlas() {
        return atlas;
    }

    public void setAtlas(List<String> atlas) {
        this.atlas = atlas;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * name : 悬赏金额
         * value : 300
         * key : rewards
         */

        private String name;
        private String value;
        private String key;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
