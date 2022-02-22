package com.uni.ppk.ui.community.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/12/18
 * Time: 10:21
 */
public class ReportTypeBean implements Serializable {

    /**
     * id : 5
     * title : 色情低俗
     * status : 1
     * type : 1
     * create_time : 2020-12-17 12:58:57
     * update_time : 2020-12-17 13:08:47
     */

    private int id;
    private String title;
    private int status;
    private int type;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
