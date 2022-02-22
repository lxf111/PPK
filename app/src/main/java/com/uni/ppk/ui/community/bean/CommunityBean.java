package com.uni.ppk.ui.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 10:52
 */
public class CommunityBean implements Serializable {

    /**
     * reward : 0.00
     * is_answer : 1
     * create_time : 2020-09-24 16:33:26
     * is_goods : 0
     * user_name : 用户45874
     * head_img : http://api.suwenlawyer.com/uploads/images/none.png
     * show_type_id : 3
     * goods_count : 0
     * show_type_name : 0悬赏金
     * type : 2
     * title : App用户发布咨询
     * content : content
     * pictures : ["http://api.suwenlawyer.com/uploads/images/lawfirm/20200907/45a445467b9986537a062c11b7285b9e.jpg"]
     * is_delete : 0
     * update_time : 2020-10-07 12:10:19
     * user_type : 1
     * answer : {"update_time":"2020-08-27 14:22:31","lawyer_id":6,"create_time":"2020-08-27 14:22:31","user_name":"用户46632","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","user_nickname":"用户46632","id":2,"associate_id":4,"content":"这是一个律师的回答","pictures":"http://api.suwenlawyer.com/uploads/images/none.png"}
     * user_id : 2
     * comments_count : 0
     * user_nickname : nihao
     * id : 4
     * is_report : 0
     * is_pay : 1
     * order_sn : SW20200924163307971468
     */

    private String reward;
    private int is_answer;
    private String create_time;
    private int is_goods;
    private String user_name;
    private String head_img;
    private int show_type_id;
    private int goods_count;
    private String show_type_name;
    private int type;
    private String title;
    private String content;
    private int is_delete;
    private String update_time;
    private int user_type;
    private int sex;
    private AnswerBean answer;
    private int user_id;
    private int comments_count;
    private String user_nickname;
    private int id;
    private int is_report;
    private int is_pay;
    private String order_sn;
    private List<String> pictures;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public int getIs_answer() {
        return is_answer;
    }

    public void setIs_answer(int is_answer) {
        this.is_answer = is_answer;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_goods() {
        return is_goods;
    }

    public void setIs_goods(int is_goods) {
        this.is_goods = is_goods;
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

    public int getShow_type_id() {
        return show_type_id;
    }

    public void setShow_type_id(int show_type_id) {
        this.show_type_id = show_type_id;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public String getShow_type_name() {
        return show_type_name;
    }

    public void setShow_type_name(String show_type_name) {
        this.show_type_name = show_type_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public AnswerBean getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBean answer) {
        this.answer = answer;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
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

    public int getIs_report() {
        return is_report;
    }

    public void setIs_report(int is_report) {
        this.is_report = is_report;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public static class AnswerBean {
        /**
         * update_time : 2020-08-27 14:22:31
         * lawyer_id : 6
         * create_time : 2020-08-27 14:22:31
         * user_name : 用户46632
         * head_img : http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg
         * user_nickname : 用户46632
         * id : 2
         * associate_id : 4
         * content : 这是一个律师的回答
         * pictures : http://api.suwenlawyer.com/uploads/images/none.png
         */

        private String update_time;
        private int lawyer_id;
        private String create_time;
        private String user_name;
        private String head_img;
        private String user_nickname;
        private int id;
        private int associate_id;
        private String content;
        private String pictures;

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getLawyer_id() {
            return lawyer_id;
        }

        public void setLawyer_id(int lawyer_id) {
            this.lawyer_id = lawyer_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public int getAssociate_id() {
            return associate_id;
        }

        public void setAssociate_id(int associate_id) {
            this.associate_id = associate_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }
    }
}
