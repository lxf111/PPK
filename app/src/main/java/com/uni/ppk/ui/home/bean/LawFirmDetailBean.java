package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/9
 * Time: 15:12
 */
public class LawFirmDetailBean implements Serializable {

    /**
     * id : 2
     * name : 江南律师事务所
     * images : ["/uploads/images/lawfirm/20200907/d4618da4f580cd7fd8a443c9a9d3ef98.jpg","/uploads/images/lawfirm/20200907/cefce9ebf6681b90965ae21db2b13e01.jpg","/uploads/images/lawfirm/20200907/45a445467b9986537a062c11b7285b9e.jpg"]
     * description : 江南律师事务所
     * lawyers : [{"id":6,"law_firm_id":2,"user_name":"迷童","law_firm":"江南律师事务所","head_img":"http://api.suwenlawyer.com/static/admin/images/none.png","service":[{"name":"代写文书"},{"name":"案件诉讼(民事)"}]}]
     */

    private int id;
    private String name;
    private String video;
    private String description;
    private List<String> images;
    private List<HomeLawyerBean> lawyers;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<HomeLawyerBean> getLawyers() {
        return lawyers;
    }

    public void setLawyers(List<HomeLawyerBean> lawyers) {
        this.lawyers = lawyers;
    }

    public static class LawyersBean {
        /**
         * id : 6
         * law_firm_id : 2
         * user_name : 迷童
         * law_firm : 江南律师事务所
         * head_img : http://api.suwenlawyer.com/static/admin/images/none.png
         * service : [{"name":"代写文书"},{"name":"案件诉讼(民事)"}]
         */

        private int id;
        private int law_firm_id;
        private String user_name;
        private String law_firm;
        private String head_img;
        private List<ServiceBean> service;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLaw_firm_id() {
            return law_firm_id;
        }

        public void setLaw_firm_id(int law_firm_id) {
            this.law_firm_id = law_firm_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getLaw_firm() {
            return law_firm;
        }

        public void setLaw_firm(String law_firm) {
            this.law_firm = law_firm;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public List<ServiceBean> getService() {
            return service;
        }

        public void setService(List<ServiceBean> service) {
            this.service = service;
        }

        public static class ServiceBean {
            /**
             * name : 代写文书
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
