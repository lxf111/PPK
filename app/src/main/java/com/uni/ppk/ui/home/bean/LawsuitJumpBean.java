package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/8
 * Time: 9:46
 */
public class LawsuitJumpBean implements Serializable {
    private Map<String, Object> orderInfo;
    private List<OrderBodyBean> bodyBeans;

    public List<OrderBodyBean> getBodyBeans() {
        return bodyBeans;
    }

    public void setBodyBeans(List<OrderBodyBean> bodyBeans) {
        this.bodyBeans = bodyBeans;
    }

    public Map<String, Object> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Map<String, Object> orderInfo) {
        this.orderInfo = orderInfo;
    }
}
