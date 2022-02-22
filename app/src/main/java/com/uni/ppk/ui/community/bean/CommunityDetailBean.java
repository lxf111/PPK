package com.uni.ppk.ui.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/12
 * Time: 14:58
 */
public class CommunityDetailBean implements Serializable {

    /**
     * id : 3
     * user_type : 1
     * user_id : 6
     * type : 2
     * title : App用户发布咨询
     * content : content
     * pictures : ["http://api.suwenlawyer.com/uploads/images/20190920/cb424395d05e4ca03bb5c5976f4aa600.jpg","http://api.suwenlawyer.com/uploads/images/lawfirm/20200907/d4618da4f580cd7fd8a443c9a9d3ef98.jpg","http://api.suwenlawyer.com/uploads/images/lawfirm/20200907/cefce9ebf6681b90965ae21db2b13e01.jpg"]
     * reward : 100.00
     * order_sn : SW20200923204941492104
     * is_pay : 1
     * is_answer : 1
     * is_report : 0
     * is_delete : 0
     * create_time : 2020-09-24 16:33:07
     * update_time : 2020-09-30 15:13:24
     * comments_count : 0
     * goods_count : 0
     * collects_count : 0
     * head_img : http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg
     * user_nickname : 用户46632
     * answer : {"id":12,"associate_id":3,"lawyer_id":2,"content":"这是一个律师的回答","pictures":["http://api.suwenlawyer.com/uploads/images/00/6e60f06529989fdf7f5ed01299fc3f.png"],"create_time":"2020-09-30 15:13:24","update_time":"2020-09-30 15:13:24","head_img":"http://api.suwenlawyer.com/uploads/images/none.png","user_nickname":"nihao"}
     * is_evaluate : 1
     * evaluate : {"id":2,"consultations_id":3,"lawyer_id":8,"label":["服务周到","技术专业"],"star":5,"content":"服务周到","create_time":"2020-10-07 17:09:18","head_img":"http://api.suwenlawyer.com/uploads/images/none.png","user_nickname":"nihao"}
     * is_goods : 0
     * is_collects : 0
     */

    private int id;
    private int user_type;
    private int user_id;
    private int type;
    private int sex;
    private String title;
    private String content;
    private String reward;
    private String order_sn;
    private int is_pay;
    private int is_answer;
    private int is_report;
    private int is_delete;
    private String create_time;
    private String update_time;
    private int comments_count;
    private int goods_count;
    private int collects_count;
    private String head_img;
    private String user_nickname;
    private AnswerBean answer;
    private int is_evaluate;
    private EvaluateBean evaluate;
    private int is_goods;
    private int is_collects;
    private List<String> pictures;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public int getIs_answer() {
        return is_answer;
    }

    public void setIs_answer(int is_answer) {
        this.is_answer = is_answer;
    }

    public int getIs_report() {
        return is_report;
    }

    public void setIs_report(int is_report) {
        this.is_report = is_report;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
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

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public int getCollects_count() {
        return collects_count;
    }

    public void setCollects_count(int collects_count) {
        this.collects_count = collects_count;
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

    public AnswerBean getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerBean answer) {
        this.answer = answer;
    }

    public int getIs_evaluate() {
        return is_evaluate;
    }

    public void setIs_evaluate(int is_evaluate) {
        this.is_evaluate = is_evaluate;
    }

    public EvaluateBean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateBean evaluate) {
        this.evaluate = evaluate;
    }

    public int getIs_goods() {
        return is_goods;
    }

    public void setIs_goods(int is_goods) {
        this.is_goods = is_goods;
    }

    public int getIs_collects() {
        return is_collects;
    }

    public void setIs_collects(int is_collects) {
        this.is_collects = is_collects;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public static class AnswerBean {
        /**
         * id : 12
         * associate_id : 3
         * lawyer_id : 2
         * content : 这是一个律师的回答
         * pictures : ["http://api.suwenlawyer.com/uploads/images/00/6e60f06529989fdf7f5ed01299fc3f.png"]
         * create_time : 2020-09-30 15:13:24
         * update_time : 2020-09-30 15:13:24
         * head_img : http://api.suwenlawyer.com/uploads/images/none.png
         * user_nickname : nihao
         */

        private int id;
        private int associate_id;
        private int lawyer_id;
        private String content;
        private String create_time;
        private String update_time;
        private String head_img;
        private String user_nickname;
        private List<String> pictures;

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

        public int getLawyer_id() {
            return lawyer_id;
        }

        public void setLawyer_id(int lawyer_id) {
            this.lawyer_id = lawyer_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public List<String> getPictures() {
            return pictures;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }
    }

    public static class EvaluateBean {
        /**
         * id : 2
         * consultations_id : 3
         * lawyer_id : 8
         * label : ["服务周到","技术专业"]
         * star : 5
         * content : 服务周到
         * create_time : 2020-10-07 17:09:18
         * head_img : http://api.suwenlawyer.com/uploads/images/none.png
         * user_nickname : nihao
         */

        private int id;
        private int consultations_id;
        private int lawyer_id;
        private int star;
        private String content;
        private String create_time;
        private String head_img;
        private String user_nickname;
        private List<String> label;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getConsultations_id() {
            return consultations_id;
        }

        public void setConsultations_id(int consultations_id) {
            this.consultations_id = consultations_id;
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
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }
}
