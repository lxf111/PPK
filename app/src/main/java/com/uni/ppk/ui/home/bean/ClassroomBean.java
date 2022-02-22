package com.uni.ppk.ui.home.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 14:34
 */
public class ClassroomBean implements Serializable {

    /**
     * id : 1
     * title : 寓法理和观点于故事中，贴近生活、贴近百姓
     * cover : http://api.suwenlawyer.com/uploads/images/types/20200921/9b21a0c803e2cfb93efab6b50a160ab0.jpg
     * price : 0.00
     * learning : 0
     * type : 1
     */

    private int id;
    private String title;
    private String cover;
    private String price;
    private int learning;
    private int type;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getLearning() {
        return learning;
    }

    public void setLearning(int learning) {
        this.learning = learning;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
