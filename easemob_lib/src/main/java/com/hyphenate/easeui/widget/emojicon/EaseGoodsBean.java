package com.hyphenate.easeui.widget.emojicon;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/3/25
 * Time: 16:52
 */
public class EaseGoodsBean implements Serializable {

    /**
     * em_goods_thumb : files/20200114/4028891b6fa23e94016fa23e944a0000.jpg
     * em_goods_price : 299.00
     * em_goods_name : 测试111111
     * em_goods_type : 0
     * em_goods_id : 586b53010de54eb3815470870a30e446
     */

    private String em_goods_thumb;
    private String em_goods_price;
    private String em_goods_name;
    private String em_goods_type;
    private String em_goods_id;

    public String getEm_goods_thumb() {
        return em_goods_thumb;
    }

    public void setEm_goods_thumb(String em_goods_thumb) {
        this.em_goods_thumb = em_goods_thumb;
    }

    public String getEm_goods_price() {
        return em_goods_price;
    }

    public void setEm_goods_price(String em_goods_price) {
        this.em_goods_price = em_goods_price;
    }

    public String getEm_goods_name() {
        return em_goods_name;
    }

    public void setEm_goods_name(String em_goods_name) {
        this.em_goods_name = em_goods_name;
    }

    public String getEm_goods_type() {
        return em_goods_type;
    }

    public void setEm_goods_type(String em_goods_type) {
        this.em_goods_type = em_goods_type;
    }

    public String getEm_goods_id() {
        return em_goods_id;
    }

    public void setEm_goods_id(String em_goods_id) {
        this.em_goods_id = em_goods_id;
    }
}
