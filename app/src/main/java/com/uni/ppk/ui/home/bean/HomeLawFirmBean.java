package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/9
 * Time: 16:23
 */
public class HomeLawFirmBean implements Serializable {

    /**
     * id : 2
     * name : 江南律师事务所
     * image : /uploads/images/lawfirm/20200907/cefce9ebf6681b90965ae21db2b13e01.jpg
     * service : [{"id":1,"lawfirm_id":2,"name":"婚姻家事"},{"id":2,"lawfirm_id":2,"name":"劳动纠纷"},{"id":3,"lawfirm_id":2,"name":"综合法律服务"}]
     */

    private int id;
    private String name;
    private String image;
    private List<LabelBean> service;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<LabelBean> getService() {
        return service;
    }

    public void setService(List<LabelBean> service) {
        this.service = service;
    }

}
