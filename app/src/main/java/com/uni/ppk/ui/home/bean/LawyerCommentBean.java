package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 16:15
 */
public class LawyerCommentBean implements Serializable {

    /**
     * id : 1
     * order_id : 1
     * user_id : 1
     * lawyer_id : 6
     * label : ["服务周到","技术专业"]
     * star : 5
     * content : 服务周到
     * create_time : 2020-10-07 17:09:18
     * user : {"id":1,"user_name":"用户45874","user_nickname":"用户45874","head_img":"http://api.suwenlawyer.com/static/admin/images/none.png"}
     */

    private int id;
    private int order_id;
    private int user_id;
    private int lawyer_id;
    private int star;
    private String content;
    private String create_time;
    private UserBean user;
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
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public static class UserBean {
        /**
         * id : 1
         * user_name : 用户45874
         * user_nickname : 用户45874
         * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
         */

        private int id;
        private String user_name;
        private String user_nickname;
        private String head_img;

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

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }
    }
}
