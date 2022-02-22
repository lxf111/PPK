package com.uni.ppk.ui.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/15
 * Time: 18:16
 */
public class OtherInfoBean implements Serializable {

    /**
     * user_info : {"user_type":"1","user_id":"6","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","user_nickname":"用户46632","desc":"KKK旅途兔兔图图YY突突突JJ"}
     * list : [{"create_time":"2020-10-14 15:04:18","is_goods":0,"user_name":"用户46632","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","show_type_id":2,"goods_count":0,"show_type_name":"普通用户发布","type":1,"title":"拉粑粑","content":"就斤斤计较","pictures":["http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg","http://api.suwenlawyer.com/uploads/images/32/67978e4fceebec1c42c1528aebcab6.jpeg","http://api.suwenlawyer.com/uploads/images/52/12f79678eba364d96050b2965a84c9.jpeg"],"is_delete":0,"update_time":"2020-10-14 15:04:18","user_type":1,"user_id":6,"comments_count":0,"user_nickname":"用户46632","id":19,"is_report":0},{"reward":"0.00","is_answer":0,"create_time":"2020-10-10 14:58:56","is_goods":0,"user_name":"用户46632","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","show_type_id":3,"goods_count":1,"show_type_name":"0悬赏金","type":2,"title":"123","content":"3333","pictures":["http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg"],"is_delete":0,"update_time":"2020-10-10 14:58:56","user_type":1,"answer":{},"user_id":6,"comments_count":0,"user_nickname":"用户46632","id":15,"is_report":0,"is_pay":1,"order_sn":"LSS20201010145856514585"},{"reward":"108.00","is_answer":0,"create_time":"2020-10-09 10:52:23","is_goods":0,"user_name":"用户46632","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","show_type_id":4,"goods_count":0,"show_type_name":"悬赏","type":2,"title":"我发布的一个咨询","content":"发布咨询的内容道行道行","pictures":[],"is_delete":0,"update_time":"2020-10-09 10:52:23","user_type":1,"answer":{},"user_id":6,"comments_count":0,"user_nickname":"用户46632","id":14,"is_report":0,"is_pay":0,"order_sn":"LSS20201009105223875809"},{"reward":"0.00","is_answer":0,"create_time":"2020-10-09 10:48:35","is_goods":0,"user_name":"用户46632","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","show_type_id":3,"goods_count":0,"show_type_name":"0悬赏金","type":2,"title":"我发布的一个咨询","content":"发布咨询的内容道行道行","pictures":[],"is_delete":0,"update_time":"2020-10-09 10:48:35","user_type":1,"answer":{},"user_id":6,"comments_count":0,"user_nickname":"用户46632","id":13,"is_report":0,"is_pay":1,"order_sn":"LSS20201009104835733424"},{"reward":"100.00","is_answer":1,"create_time":"2020-09-24 16:33:07","is_goods":0,"user_name":"用户46632","head_img":"http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg","show_type_id":4,"goods_count":0,"show_type_name":"悬赏","type":2,"title":"App用户发布咨询","content":"content","pictures":["http://api.suwenlawyer.com/uploads/images/20190920/cb424395d05e4ca03bb5c5976f4aa600.jpg","http://api.suwenlawyer.com/uploads/images/lawfirm/20200907/d4618da4f580cd7fd8a443c9a9d3ef98.jpg","http://api.suwenlawyer.com/uploads/images/lawfirm/20200907/cefce9ebf6681b90965ae21db2b13e01.jpg"],"is_delete":0,"update_time":"2020-09-30 15:13:24","user_type":1,"answer":{"update_time":"2020-09-30 15:13:24","lawyer_id":2,"create_time":"2020-09-30 15:13:24","user_name":"用户45874","head_img":"http://api.suwenlawyer.com/uploads/images/none.png","user_nickname":"nihao","id":12,"associate_id":3,"content":"这是一个律师的回答","pictures":"http://api.suwenlawyer.com/uploads/images/none.png"},"user_id":6,"comments_count":0,"user_nickname":"用户46632","id":3,"is_report":0,"is_pay":1,"order_sn":"SW20200923204941492104"}]
     */

    private UserInfoBean user_info;
    private List<CommunityBean> list;

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public List<CommunityBean> getList() {
        return list;
    }

    public void setList(List<CommunityBean> list) {
        this.list = list;
    }

    public static class UserInfoBean {
        /**
         * user_type : 1
         * user_id : 6
         * head_img : http://api.suwenlawyer.com/uploads/images/e2/a7be6c310e9f5a51040e5d3175829d.jpeg
         * user_nickname : 用户46632
         * desc : KKK旅途兔兔图图YY突突突JJ
         */

        private String user_type;
        private String user_id;
        private String head_img;
        private String user_nickname;
        private String desc;

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
