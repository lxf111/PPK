package com.uni.ppk.ui.message.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 16:20
 */
public class MessageBean implements Serializable {

    /**
     * id : 75
     * msg_type : 1
     * title : 订单取消通知
     * content : 您的订单：LSS20201027172211689061;应付款400.00;因超时已自动取消
     * create_time : 2020-10-29 09:16:58
     */

    private int id;
    private int msg_type;
    private String title;
    private String content;
    private String create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
