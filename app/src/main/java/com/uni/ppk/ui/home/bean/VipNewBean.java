package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2021/2/27
 * Time: 17:16
 */
public class VipNewBean implements Serializable {

    private List<VipListBean> company;
    private List<VipListBean> personal;

    public List<VipListBean> getCompany() {
        return company;
    }

    public void setCompany(List<VipListBean> company) {
        this.company = company;
    }

    public List<VipListBean> getPersonal() {
        return personal;
    }

    public void setPersonal(List<VipListBean> personal) {
        this.personal = personal;
    }

    public static class CompanyBean {
        /**
         * aid : 4
         * name : 企业会员套餐一
         * ios_pay : suwenuser.vip4
         * background : https://api.suwenlawyer.com/uploads/images/76/364ca79d2e5ca1291d03b65bc0b857.png
         * logo : https://api.suwenlawyer.com/uploads/images/none.png
         * price : 2999.00
         * ios_price : 2999.00
         * type : 2
         * service : [{"id":6,"vipid":4,"name":"全面企业日常法律问题免费咨询"},{"id":7,"vipid":4,"name":"全平台服务（发布委托诉讼、代写法律文书、代写合同等）享有5折优惠"},{"id":8,"vipid":4,"name":"免费下载平台合同模板、诉讼法律文书模板"}]
         */

        private int aid;
        private String name;
        private String ios_pay;
        private String background;
        private String logo;
        private String price;
        private String ios_price;
        private int type;
        private List<ServiceBean> service;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIos_pay() {
            return ios_pay;
        }

        public void setIos_pay(String ios_pay) {
            this.ios_pay = ios_pay;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ServiceBean> getService() {
            return service;
        }

        public void setService(List<ServiceBean> service) {
            this.service = service;
        }

        public static class ServiceBean {
            /**
             * id : 6
             * vipid : 4
             * name : 全面企业日常法律问题免费咨询
             */

            private int id;
            private int vipid;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVipid() {
                return vipid;
            }

            public void setVipid(int vipid) {
                this.vipid = vipid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class PersonalBean {
        /**
         * aid : 1
         * name : 个人会员套餐一
         * ios_pay : suwenuser.vip1
         * background : https://suwenlawyer.oss-cn-huhehaote.aliyuncs.com/uploads/images/44/3bef878df3f97c09d8ebd681cae497.png
         * logo : https://api.suwenlawyer.com/uploads/images/none.png
         * price : 199.00
         * ios_price : 199.00
         * type : 1
         * service : [{"id":4,"vipid":1,"name":"全年线上免费咨询"},{"id":27,"vipid":1,"name":"享有平台委托发布服务费5折优惠"}]
         */

        private int aid;
        private String name;
        private String ios_pay;
        private String background;
        private String logo;
        private String price;
        private String ios_price;
        private int type;
        private List<ServiceBeanX> service;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIos_pay() {
            return ios_pay;
        }

        public void setIos_pay(String ios_pay) {
            this.ios_pay = ios_pay;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ServiceBeanX> getService() {
            return service;
        }

        public void setService(List<ServiceBeanX> service) {
            this.service = service;
        }

        public static class ServiceBeanX {
            /**
             * id : 4
             * vipid : 1
             * name : 全年线上免费咨询
             */

            private int id;
            private int vipid;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVipid() {
                return vipid;
            }

            public void setVipid(int vipid) {
                this.vipid = vipid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
