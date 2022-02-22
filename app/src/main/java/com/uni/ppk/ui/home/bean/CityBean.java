package com.uni.ppk.ui.home.bean;

/**
 * @Description:  定位  城市热门
 * @Author: longyu
 * @CreateDate: 2020/1/20 0020$ 10:40$
 * @Version: 1.0
 */
public class CityBean {

    public String id;

    public String name;
    /**
     * id : 110000000000
     * level : 1
     * full_name : 北京市
     */

    private String level;
    private String full_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

}
