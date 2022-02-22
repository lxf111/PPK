package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 10:55
 */
public class BookTitleBean implements Serializable {

    /**
     * cid : 0
     * cname : 无
     * data : [{"id":15,"pid":1,"cid":0,"image":null,"name":"民事起诉状","cname":null},{"id":16,"pid":1,"cid":0,"image":null,"name":"民事答辩状","cname":null},{"id":17,"pid":1,"cid":0,"image":null,"name":"执行申请书","cname":null},{"id":18,"pid":1,"cid":0,"image":null,"name":"民事上诉状","cname":null},{"id":19,"pid":1,"cid":0,"image":null,"name":"再审申请书","cname":null},{"id":20,"pid":1,"cid":0,"image":null,"name":"反诉状","cname":null},{"id":21,"pid":1,"cid":0,"image":null,"name":"撤诉申请书","cname":null}]
     */

    private int cid;
    private String cname;
    private List<OrderClassifyBean> data;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<OrderClassifyBean> getData() {
        return data;
    }

    public void setData(List<OrderClassifyBean> data) {
        this.data = data;
    }

}
