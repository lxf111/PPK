package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 17:26
 */
public class MyCourseBean implements Serializable {

    /**
     * id : 3
     * class_id : 2
     * user_id : 6
     * create_time : 2020-09-07 15:03:07
     * update_time : 2020-09-07 15:03:07
     * subject : {"id":2,"title":"寓法理和观点于故事中，贴近生活、贴近百姓","cover":"193","description":"关注中国的立法进程，寓法理和个性思考案例中，以名家讲座的方式，将现实生活中普遍存在的法律问题展现在普通百姓面前。","teacher_name":"李夏明","address":"192","type":1,"price":"0.00","viewers":0,"learning":0,"status":1,"sort":1,"create_time":"2020-09-21 11:55:27","update_time":"2020-09-21 12:06:28"}
     */

    private int id;
    private int class_id;
    private int user_id;
    private String create_time;
    private String update_time;
    private SubjectBean subject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public static class SubjectBean {
        /**
         * id : 2
         * title : 寓法理和观点于故事中，贴近生活、贴近百姓
         * cover : 193
         * description : 关注中国的立法进程，寓法理和个性思考案例中，以名家讲座的方式，将现实生活中普遍存在的法律问题展现在普通百姓面前。
         * teacher_name : 李夏明
         * address : 192
         * type : 1
         * price : 0.00
         * viewers : 0
         * learning : 0
         * status : 1
         * sort : 1
         * create_time : 2020-09-21 11:55:27
         * update_time : 2020-09-21 12:06:28
         */

        private int id;
        private String title;
        private String cover;
        private String description;
        private String teacher_name;
        private String address;
        private int type;
        private String price;
        private int viewers;
        private int learning;
        private int status;
        private int sort;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getViewers() {
            return viewers;
        }

        public void setViewers(int viewers) {
            this.viewers = viewers;
        }

        public int getLearning() {
            return learning;
        }

        public void setLearning(int learning) {
            this.learning = learning;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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
}
