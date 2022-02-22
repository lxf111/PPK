package com.uni.ppk.ui.home.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:13
 */
public class HomeBannerBean implements Serializable {

    /**
     * link_type : 1
     * app_type : 1
     * thumb : https://suwenlawyer.oss-cn-huhehaote.aliyuncs.com/uploads/images/0a/5609168c48357b2bb3420acde7c262.jpg
     * width : 0
     * name : 执行案件
     * href :
     * id : 22
     * height : 0
     * types_id : 7
     * jump : {"son_id":0,"pname":"法律套餐","son_name":"","pid":7}
     */

    private int link_type;
    private int app_type;
    private String thumb;
    private int width;
    private String name;
    private String href;
    private int id;
    private int height;
    private int types_id;
    private JumpBean jump;

    public int getLink_type() {
        return link_type;
    }

    public void setLink_type(int link_type) {
        this.link_type = link_type;
    }

    public int getApp_type() {
        return app_type;
    }

    public void setApp_type(int app_type) {
        this.app_type = app_type;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTypes_id() {
        return types_id;
    }

    public void setTypes_id(int types_id) {
        this.types_id = types_id;
    }

    public JumpBean getJump() {
        return jump;
    }

    public void setJump(JumpBean jump) {
        this.jump = jump;
    }

    public static class JumpBean implements Serializable{
        /**
         * son_id : 0
         * pname : 法律套餐
         * son_name :
         * pid : 7
         */

        private int son_id;
        private String pname;
        private String son_name;
        private int pid;

        public int getSon_id() {
            return son_id;
        }

        public void setSon_id(int son_id) {
            this.son_id = son_id;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getSon_name() {
            return son_name;
        }

        public void setSon_name(String son_name) {
            this.son_name = son_name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
