package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2021/1/8
 * Time: 15:40
 */
public class TxtTypeBean implements Serializable {

    /**
     * id : 1
     * name : 房屋合同
     * texts : [{"id":1,"typeid":1,"name":"案件诉讼文本下载"}]
     */

    private int id;
    private String name;
    private List<TextsBean> texts;

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

    public List<TextsBean> getTexts() {
        return texts;
    }

    public void setTexts(List<TextsBean> texts) {
        this.texts = texts;
    }

    public static class TextsBean {
        /**
         * id : 1
         * typeid : 1
         * name : 案件诉讼文本下载
         */

        private int id;
        private int typeid;
        private String name;
        private String file_id;

        public String getFile_id() {
            return file_id;
        }

        public void setFile_id(String file_id) {
            this.file_id = file_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
