package com.uni.ppk.ui.home.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/7
 * Time: 9:42
 */
public class OrderPriceBean implements Serializable {

    private List<FieldsBean> fields;
    private List<PricesBean> prices;
    private List<GuaranteeBean> guarantee;


    /**
     * tips : {"province":"代写文书地区的提示","content":"代写文书描述的提示"}
     */

    private TipsBean tips;
    private List<LinkageBeanX> linkage;

    public List<FieldsBean> getFields() {
        return fields;
    }

    public void setFields(List<FieldsBean> fields) {
        this.fields = fields;
    }

    public List<PricesBean> getPrices() {
        return prices;
    }

    public void setPrices(List<PricesBean> prices) {
        this.prices = prices;
    }

    public List<GuaranteeBean> getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(List<GuaranteeBean> guarantee) {
        this.guarantee = guarantee;
    }

    public TipsBean getTips() {
        return tips;
    }

    public void setTips(TipsBean tips) {
        this.tips = tips;
    }

    public List<LinkageBeanX> getLinkage() {
        return linkage;
    }

    public void setLinkage(List<LinkageBeanX> linkage) {
        this.linkage = linkage;
    }

    public static class FieldsBean implements Serializable{
        /**
         * types_id : 46
         * type : select
         * field : is_with
         * name : 是否已会见
         * value : require
         * tips :
         * is_require : 1
         * is_hidden : 0
         * option : ["是","否"]
         */

        private int types_id;
        private String type;
        private String field;
        private String name;
        private String value;
        private String tips;
        private int is_require;
        private int is_hidden;
        private List<String> option;

        public int getTypes_id() {
            return types_id;
        }

        public void setTypes_id(int types_id) {
            this.types_id = types_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public int getIs_require() {
            return is_require;
        }

        public void setIs_require(int is_require) {
            this.is_require = is_require;
        }

        public int getIs_hidden() {
            return is_hidden;
        }

        public void setIs_hidden(int is_hidden) {
            this.is_hidden = is_hidden;
        }

        public List<String> getOption() {
            return option;
        }

        public void setOption(List<String> option) {
            this.option = option;
        }
    }

    public static class PricesBean implements Serializable{
        /**
         * type : default
         * field :
         * price : 3000.00
         * member_price : 2000.00
         * rule : []
         */

        private String type;
        private String field;
        private String price;
        private String member_price;
        private List<RuleBean> rule;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMember_price() {
            return member_price;
        }

        public void setMember_price(String member_price) {
            this.member_price = member_price;
        }

        public List<RuleBean> getRule() {
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public static class RuleBean implements Serializable{
            private String price;
            private String count;
            private String province;
            private String city;
            private String district;
            private String area_id;
            private String lists;
            private String min;
            private String max;

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getLists() {
                return lists;
            }

            public void setLists(String lists) {
                this.lists = lists;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }

    }

    public static class GuaranteeBean implements Serializable{
        /**
         * id : 1
         * name : 诉问服务保障，保障客户合法权益不受侵害1
         */

        private int id;
        private String name;

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
    }

    public static class TipsBean implements Serializable{
        /**
         * province : 代写文书地区的提示
         * content : 代写文书描述的提示
         */

        private String province;
        private String content;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class LinkageBeanX implements Serializable{
        /**
         * linkage : [{"title":"婚姻家庭","child":[{"title":"婚姻财产协议","child":[]},{"title":"同居协议","child":[]},{"title":"离婚协议","child":[]},{"title":"收养协议","child":[]},{"title":"抚养协议","child":[]},{"title":"财产赠予、转让协议","child":[]},{"title":"遗嘱","child":[]},{"title":"遗产分割协议","child":[]},{"title":"婚姻家庭其他法律文书","child":[]}]},{"title":"房地产","child":[{"title":"房屋买卖协议","child":[]},{"title":"二手房买卖协议","child":[]},{"title":"房屋租赁协议","child":[]},{"title":"场地店铺租赁协议","child":[]},{"title":"装修合同","child":[]},{"title":"拆迁补偿协议","child":[]},{"title":"物业服务协议","child":[]},{"title":"业主委员会设立运作文书","child":[]},{"title":"房地产其他法律文书","child":[]}]},{"title":"日常民商事","child":[{"title":"服务合同","child":[]},{"title":"买卖合同","child":[]},{"title":"运输合同","child":[]},{"title":"维权声明","child":[]},{"title":"日常民商事其他法律文书","child":[]}]},{"title":"劳动劳务","child":[{"title":"劳动合同","child":[]},{"title":"劳务合同","child":[]},{"title":"员工保密协议","child":[]},{"title":"竞业禁止协议","child":[]},{"title":"考勤制度","child":[]},{"title":"招聘、培训、离职管理办法","child":[]},{"title":"员工手册","child":[]},{"title":"劳动劳务其他法律文书","child":[]}]},{"title":"知识产权","child":[{"title":"著作权协议","child":[]},{"title":"专利权协议","child":[]},{"title":"商标权协议","child":[]},{"title":"专有技术协议","child":[]},{"title":"商业秘密协议","child":[]},{"title":"影音书画作品权利协议","child":[]},{"title":"计算机软件协议","child":[]},{"title":"域名协议","child":[]},{"title":"知识产权其他法律文书","child":[]}]},{"title":"财产及借贷","child":[{"title":"财产/资产赠予协议","child":[]},{"title":"财产/资产转让协议","child":[]},{"title":"借款合同","child":[]},{"title":"借条借据","child":[]},{"title":"保证/抵押/质押合同","child":[]},{"title":"融资租赁合同","child":[]},{"title":"债权债务转让合同","child":[]},{"title":"催款函","child":[]},{"title":"投资理财协议","child":[]},{"title":"信托合同","child":[]},{"title":"保险合同","child":[]},{"title":"财产及借贷其他法律文书","child":[]}]},{"title":"公司日常经营","child":[{"title":"销售协议","child":[]},{"title":"采购协议","child":[]},{"title":"加工承揽协议","child":[]},{"title":"建设工程协议","child":[]},{"title":"交通运输协议","child":[]},{"title":"仓储保管协议","child":[]},{"title":"物资租赁协议","child":[]},{"title":"服务协议","child":[]},{"title":"承包经营协议","child":[]},{"title":"许可加盟协议","child":[]},{"title":"招投标协议","child":[]},{"title":"招商引资协议","child":[]},{"title":"项目合作协议","child":[]},{"title":"中介服务协议","child":[]},{"title":"广告协议","child":[]},{"title":"水电气热能源协议","child":[]},{"title":"会议通知、议程、决议","child":[]},{"title":"规章制度协议","child":[]},{"title":"工商类文书","child":[]},{"title":"税务类文书","child":[]},{"title":"公司日常经营其他法律文书","child":[]}]},{"title":"公司设立、股权管理及并购","child":[{"title":"出资、增资协议","child":[]},{"title":"合伙协议","child":[]},{"title":"公司章程","child":[]},{"title":"股权转让协议","child":[]},{"title":"股权代持协议","child":[]},{"title":"股权激励协议","child":[]},{"title":"收购重组协议","child":[]},{"title":"公司设立、终止文书","child":[]},{"title":"公司发行股票、发行债券文书","child":[]},{"title":"公司设立、股权管理及并购其他法律文书","child":[]}]},{"title":"争议解决诉讼仲裁","child":[{"title":"和解、赔偿、补偿文书","child":[]},{"title":"民事诉讼文书","child":[]},{"title":"刑事诉讼文书","child":[]},{"title":"行政诉讼、复议文书","child":[]},{"title":"劳动仲裁文书","child":[]},{"title":"民商事仲裁文书","child":[]},{"title":"争议解决诉讼仲裁其他法律文书","child":[]}]}]
         * title : 合同类型联动
         * fields : class_type,class_name
         */

        private String title;
        private String fields;
        private List<LinkageBean> linkage;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFields() {
            return fields;
        }

        public void setFields(String fields) {
            this.fields = fields;
        }

        public List<LinkageBean> getLinkage() {
            return linkage;
        }

        public void setLinkage(List<LinkageBean> linkage) {
            this.linkage = linkage;
        }

        public static class LinkageBean implements Serializable{
            /**
             * title : 婚姻家庭
             * child : [{"title":"婚姻财产协议","child":[]},{"title":"同居协议","child":[]},{"title":"离婚协议","child":[]},{"title":"收养协议","child":[]},{"title":"抚养协议","child":[]},{"title":"财产赠予、转让协议","child":[]},{"title":"遗嘱","child":[]},{"title":"遗产分割协议","child":[]},{"title":"婚姻家庭其他法律文书","child":[]}]
             */

            private String title;
            private List<ChildBean> child;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean implements Serializable{
                /**
                 * title : 婚姻财产协议
                 * child : []
                 */

                private String title;
                private List<?> child;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<?> getChild() {
                    return child;
                }

                public void setChild(List<?> child) {
                    this.child = child;
                }
            }
        }
    }
}
