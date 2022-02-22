package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/16
 * Time: 19:17
 */
public class AddressListBean implements Serializable {

    /**
     * pinyin : A
     * citys : [{"pinyin":"A","full_name":"河北省/保定市/安国市","level":"3","name":"安国市","id":"130683000000"},{"pinyin":"A","full_name":"内蒙古自治区/兴安盟/阿尔山市","level":"3","name":"阿尔山市","id":"152202000000"},{"pinyin":"A","full_name":"辽宁省/鞍山市","level":"2","name":"鞍山市","id":"210300000000"},{"pinyin":"A","full_name":"黑龙江省/绥化市/安达市","level":"3","name":"安达市","id":"231281000000"},{"pinyin":"A","full_name":"安徽省/安庆市","level":"2","name":"安庆市","id":"340800000000"},{"pinyin":"A","full_name":"山东省/潍坊市/安丘市","level":"3","name":"安丘市","id":"370784000000"},{"pinyin":"A","full_name":"河南省/安阳市","level":"2","name":"安阳市","id":"410500000000"},{"pinyin":"A","full_name":"湖北省/孝感市/安陆市","level":"3","name":"安陆市","id":"420982000000"},{"pinyin":"A","full_name":"贵州省/安顺市","level":"2","name":"安顺市","id":"520400000000"},{"pinyin":"A","full_name":"云南省/昆明市/安宁市","level":"3","name":"安宁市","id":"530181000000"},{"pinyin":"A","full_name":"陕西省/安康市","level":"2","name":"安康市","id":"610900000000"},{"pinyin":"A","full_name":"新疆维吾尔自治区/博尔塔拉蒙古自治州/阿拉山口市","level":"3","name":"阿拉山口市","id":"652702000000"},{"pinyin":"A","full_name":"新疆维吾尔自治区/阿克苏地区/阿克苏市","level":"3","name":"阿克苏市","id":"652901000000"},{"pinyin":"A","full_name":"新疆维吾尔自治区/克孜勒苏柯尔克孜自治州/阿图什市","level":"3","name":"阿图什市","id":"653001000000"},{"pinyin":"A","full_name":"新疆维吾尔自治区/阿勒泰地区/阿勒泰市","level":"3","name":"阿勒泰市","id":"654301000000"},{"pinyin":"A","full_name":"新疆维吾尔自治区/自治区直辖县级行政区划/阿拉尔市","level":"3","name":"阿拉尔市","id":"659002000000"}]
     */

    private String pinyin;
    private List<CitysBean> citys;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

    public static class CitysBean {
        /**
         * pinyin : A
         * full_name : 河北省/保定市/安国市
         * level : 3
         * name : 安国市
         * id : 130683000000
         */

        private String pinyin;
        private String full_name;
        private String level;
        private String name;
        private String id;

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

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
    }
}
