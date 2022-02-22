package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/17
 * Time: 15:35
 */
public class OrderDetailBean implements Serializable {

    /**
     * id : 51
     * order_sn : LSS20201009105349839729
     * title : 法务110
     * thumb : http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg
     * content : 1233
     * atlas : ["http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg"]
     * province : 北京市
     * city : 市辖区
     * district : 丰台区
     * top_id : 14
     * son_id : 0
     * lawyer_id : 0
     * reply :
     * reply_atlas :
     * phone : 19664646649
     * email : 12344292982@qq.com
     * body : [{"key":"rewards","name":"悬赏金额","value":"300"}]
     * shipping_status : 0
     * order_money : 300.00
     * payable_money : 300.00
     * real_money : 0.00
     * create_time : 2020-10-09 10:53:49
     * user_id : 6
     * user_name : 用户46632
     * head_img : http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg
     * lawyer_name : null
     * lawyer_img :
     * comment : []
     */

    private int id;
    private String order_sn;
    private String title;
    private String thumb;
    private String content;
    private String province;
    private String city;
    private String district;
    private int top_id;
    private int son_id;
    private int lawyer_id;
    private String reply;
    private String reply_atlas;
    private String phone;
    private String email;
    private int shipping_status;
    private String order_money;
    private String payable_money;
    private String real_money;
    private String create_time;
    private int user_id;
    private String user_name;
    private String head_img;
    private String pay_status;
    private String lawyer_name;
    private String lawyer_img;
    private List<String> atlas;
    private List<BodyBean> body;
    private CommentBean comment;

    public static class CommentBean {

        /**
         * id : 5
         * order_id : 104
         * type : 1
         * consultations_id : 0
         * user_id : 33
         * lawyer_id : 38
         * label : ["服务周到","态度友好"]
         * star : 5
         * content : 你好
         * create_time : 9秒前
         * is_comment : 1
         */

        private int id;
        private int order_id;
        private int type;
        private int consultations_id;
        private int user_id;
        private int lawyer_id;
        private int star;
        private String content;
        private String create_time;
        private int is_comment;
        private List<String> label;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getConsultations_id() {
            return consultations_id;
        }

        public void setConsultations_id(int consultations_id) {
            this.consultations_id = consultations_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getLawyer_id() {
            return lawyer_id;
        }

        public void setLawyer_id(int lawyer_id) {
            this.lawyer_id = lawyer_id;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getTop_id() {
        return top_id;
    }

    public void setTop_id(int top_id) {
        this.top_id = top_id;
    }

    public int getSon_id() {
        return son_id;
    }

    public void setSon_id(int son_id) {
        this.son_id = son_id;
    }

    public int getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(int lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply_atlas() {
        return reply_atlas;
    }

    public void setReply_atlas(String reply_atlas) {
        this.reply_atlas = reply_atlas;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getPayable_money() {
        return payable_money;
    }

    public void setPayable_money(String payable_money) {
        this.payable_money = payable_money;
    }

    public String getReal_money() {
        return real_money;
    }

    public void setReal_money(String real_money) {
        this.real_money = real_money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getLawyer_name() {
        return lawyer_name == null ? "" : lawyer_name;
    }

    public void setLawyer_name(String lawyer_name) {
        this.lawyer_name = lawyer_name;
    }

    public String getLawyer_img() {
        return lawyer_img;
    }

    public void setLawyer_img(String lawyer_img) {
        this.lawyer_img = lawyer_img;
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

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public static class BodyBean {
        /**
         * key : rewards
         * name : 悬赏金额
         * value : 300
         */

        private String key;
        private String name;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

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
    }
}
