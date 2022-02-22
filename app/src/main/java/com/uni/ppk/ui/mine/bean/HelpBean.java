package com.uni.ppk.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 17:49
 */
public class HelpBean implements Serializable {

    /**
     * id : 1
     * category_id : 2
     * title : 如何下单
     * img_url : http://api.suwenlawyer.com/static/admin/images/none.png
     * synopsis : 这里介绍怎么下单
     * click_count : 0
     * fabulous : 0
     * is_recommend : 0
     * add_time : 2020-09-24 10:19:25
     * update_time : 2020-09-24 10:19:25
     * status : 1
     * sort : 0
     * name : 帮助中心
     * cat_img : http://api.suwenlawyer.com/static/admin/images/none.png
     */

    private int id;
    private int category_id;
    private String title;
    private String img_url;
    private String synopsis;
    private int click_count;
    private int fabulous;
    private int is_recommend;
    private String add_time;
    private String update_time;
    private int status;
    private int sort;
    private String name;
    private String cat_img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getClick_count() {
        return click_count;
    }

    public void setClick_count(int click_count) {
        this.click_count = click_count;
    }

    public int getFabulous() {
        return fabulous;
    }

    public void setFabulous(int fabulous) {
        this.fabulous = fabulous;
    }

    public int getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(int is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }
}
