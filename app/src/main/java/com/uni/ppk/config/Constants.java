package com.uni.ppk.config;

/**
 * 功能:配置的APPid
 */
public class Constants {

    //微信id和secret
    public static final String WX_APP_KEY = "wxb686f5dd4e432c4e";
    public static final String WX_SECRET = "f71bd3058209e47f7711858254415e61";
    //百度地图
    public static final String BAIDU_MAP_APP_KEY = "5G5B3uFURwVzVtb72QnmxRglFPsEfYDdaaa";
    //图标url前缀
    public static final String IMAGE_BASE_URL = "http://ysbh5.zjxtaq.com:8081/img/server/";
    //极光IM Appkey secret
    public static final String IM_APP_KEY = "055d08738efbdef8d01bd317";
    public static final String IM_SECRET = "57dcf91b3e9226784f4dec38";

    public static final String APPID = "80175391";
    public static final int TYPE_QQ = 1;
    public static final int TYPE_WEIXIN = 2;
    public static final String APPSECRET = "RUYcTVLIqPmtrbbgcwMxOAzBZpocJSaiaaa";
    //图片后缀
    public static final String BITMAP_SUFFIX = ".ysb";
    public static final int TO_DIARY_REQUEST = 100;
    public static final String TEST_IMG_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568629394607&di=3fea98f999f71b938e0c782012094734&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F20%2F20190120172148_eZEY4.jpeg";

    //common type
    public static final String EXTRA_KEY_COMMON_TYPE = "common_type";
    //跳转类型
    public static final String EXTRA_KEY_ENTER_TYPE = "enter_type";
    //common user id
    public static final String EXTRA_KEY_USER_ID = "user_id";
    //订单id
    public static final String EXTRA_KEY_ORDER_ID = "order_id";
    //活动id
    public static final String EXTRA_KEY_ACTIVE_ID = "active_id";
    //商品id
    public static final String EXTRA_KEY_GOODS_ID = "goods_id";
    //request code
    public static final int ADD_DIARY_REQUEST_CODE = 100;
    public static final int ADD_COMMENT_REQUEST_CODE = 101;
    public static final int ADDRESS_REQUEST_CODE = 102;
    public static final int COUPON_REQUEST_CODE = 103;
    public static final int CONFIRM_ORDER_REQUEST_CODE = 104;
    public static final int ORDER_DETAILS_REQUEST_CODE = 105;
    //result code
    public static final int RESULT_CODE_OK = 200;
    /*socket 连接常量定义*/
    //广播服务
    public static final String EVENT_BROADCASTINGLISTENER = "broadcastingListen";
    //心跳包
    public static final String EVENT_HEARTBEAT = "heartbeat";
    //握手包
    public static final String EVENT_HANDSHAKE = "conn";
    //自己发送的消息事件
    public static final String EVENT_BROAD_CAST = "broadcast";
    /*END*/

    //图片放大传值的键
    public static String EXTRA_ENLARGE_PHOTO = "enlarge_photo";
    public static String EXTRA_ENLARGE_INDEX = "enlarge_index";

    //首页按钮跳转
    public static String JUMP_TO_KTV = "yhksp10000";
    public static String JUMP_TO_FOOD = "yhksp10001";
    public static String JUMP_TO_SUPERMARKET = "yhksp10002";
    public static String JUMP_TO_ENTERTAINMENT = "yhksp10003";
    public static String JUMP_TO_CINEMA = "yhksp10004";
    public static String JUMP_TO_DISCOUNT_FRAGMENT = "jump_discount_fragment";

    //刷新奶茶店铺商品
    public static final int REFRESH_TEA_SHOP_GOODS_INFO = 3005;
    //3006 家政服务弹窗  3007 新消息提醒弹窗
    //刷新用户主页信息
    public static final int REFRESH_USER_DETAILS_INFO = 3008;

    public static final int ENTRY_TYPE_SHOP = 1;    //商户中心入口
    public static final int ENTRY_TYPE_WINDOW_ORDER_MANAGE = 101;  ///橱窗订单管理入口
    public static final int ENTRY_TYPE_WINDOW_CUSTOMER_MANAGE = 102;  ///橱窗客户管理入口

    public static final String DATA_KEY = "data_key";

    public static String PAGE_SIZE = "10";

    public static String USER_TYPE = "1";//1用户端 2律师端

    //固定键值
    //法律顾问套餐价
    public static String ORDER_ADVERISER_AREA = "arealist";
    //默认价格
    public static String ORDER_DEFAULT_PRICE = "default";
    //悬赏金额
    public static String ORDER_REWARDS = "rewards";
    //套餐内容
    public static String ORDER_TAOCAN = "arealist_title";
    //套餐id
    public static String ORDER_TAOCANID = "arealist_id";
    //合同类型
    public static String ORDER_HETONGLEIXING = "class_type";
    //合同项的id
    public static String ORDER_HETONGLEIXING_ID = "contract_id";
    //合同项
    public static String ORDER_HETONGXIANG = "class_name";
    //被告/被执行人/债务人
    public static String ORDER_BEIGAO = "defendant";
    //原告
    public static String ORDER_YUANGAO = "plaintiff";
    //标的金额
    public static String ORDER_BIAODEJINE = "money";
    //案件处于阶段
    public static String ORDER_JIEDUAN="stage";
    //诉讼案号
    public static String ORDER_ANHAO="crime";
    //是否会见
    public static String ORDER_HUIJIAN="is_with";
    //定金档位
    public static String ORDER_DANGWEI="earnest";
    //本地价格
    public static String ORDER_BENDIJIAGE="local_money";
    //最高补贴
    public static String ORDER_BUTIE="subsidy";
    //翻译页数
    public static String ORDER_PAGE="number";
    //翻译类型id
    public static String ORDER_TYPE_ID="file_id";
    public static String ORDER_TXT_FIRST="txt_first";
    public static String ORDER_TXT_SECOND="txt_second";

}
