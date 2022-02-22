package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2021/2/23
 * Time: 11:00
 */
public class ContractServiceBean implements Serializable {

    /**
     * update_time : 2021-02-19 19:20:09
     * create_time : 2021-02-18 17:42:23
     * price : 499.00
     * name : 婚姻家庭类合同
     * pid : 0
     * member_price : 0.00
     * id : 1
     * sort : 65535
     * type : 68
     * childs : [{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"婚姻财产协议","pid":1,"member_price":"0.00","id":21,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"同居协议","pid":1,"member_price":"0.00","id":22,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"离婚协议","pid":1,"member_price":"0.00","id":23,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"收养协议","pid":1,"member_price":"0.00","id":24,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"抚养协议","pid":1,"member_price":"0.00","id":25,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"财产赠予、转让协议","pid":1,"member_price":"0.00","id":26,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"遗嘱","pid":1,"member_price":"0.00","id":27,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"遗产分割协议","pid":1,"member_price":"0.00","id":28,"sort":65535,"type":68,"status":1},{"update_time":"2021-02-18 17:42:23","create_time":"2021-02-18 17:42:23","price":"499.00","name":"婚姻家庭其他法律文书","pid":1,"member_price":"0.00","id":29,"sort":65535,"type":68,"status":1}]
     * status : 1
     */

    private String update_time;
    private String create_time;
    private String price;
    private String name;
    private int pid;
    private String member_price;
    private int id;
    private int sort;
    private int type;
    private int status;
    private List<ChildsBean> childs;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getMember_price() {
        return member_price;
    }

    public void setMember_price(String member_price) {
        this.member_price = member_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ChildsBean> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildsBean> childs) {
        this.childs = childs;
    }

    public static class ChildsBean {
        /**
         * update_time : 2021-02-18 17:42:23
         * create_time : 2021-02-18 17:42:23
         * price : 499.00
         * name : 婚姻财产协议
         * pid : 1
         * member_price : 0.00
         * id : 21
         * sort : 65535
         * type : 68
         * status : 1
         */

        private String update_time;
        private String create_time;
        private String price;
        private String name;
        private int pid;
        private String member_price;
        private int id;
        private int sort;
        private int type;
        private int status;

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getMember_price() {
            return member_price;
        }

        public void setMember_price(String member_price) {
            this.member_price = member_price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
