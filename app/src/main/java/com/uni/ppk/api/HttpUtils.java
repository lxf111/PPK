package com.uni.ppk.api;

import android.app.Activity;

import com.uni.commoncore.utils.StyledDialogUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.http.BaseCallBack;
import com.uni.ppk.http.BaseOkHttpClient;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/6
 * Time: 11:45
 */
public class HttpUtils {

    /**
     * 密码登录
     */
    public static void login(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5c78dbfd977cf", callBack, true);
    }

    /**
     * 验证码登录
     */
    public static void codeLogin(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5c78dca45ebc1", callBack, true);
    }

    /**
     * 抖音登录
     */
    public static void douyinLogin(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "https://open.douyin.com/oauth/access_token/", callBack, true);
    }

    /**
     * 发送短信
     * type【type。类型：int(11) unsigned】，1=注册(未注册手机号),2=忘记密码(已经注册的手机号),3=短信登录,4修改手机号5绑定手机号
     */
    public static void sendMessage(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        params.put("is_test", "1");
        getData(mContent, params, "5b5bdc44796e8", callBack, true);
    }

    /**
     * 注册
     */
    public static void register(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5cad9f63e4f94", callBack, true);
    }

    /**
     * 忘记密码
     */
    public static void forget(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5caeeba9866aa", callBack, true);
    }

    /**
     * 三方登录
     */
    public static void thirdLogin(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d7660a421e69", callBack, true);
    }

    /**
     * 支付宝登录
     */
    public static void aliLogin(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f9645df46567", callBack, true);
    }

    /**
     * 绑定手机号
     */
    public static void bindPhone(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d7757d28d076", callBack, true);
    }

    /**
     * 一级分类
     */
    public static void firstClassify(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f69482c61b97", callBack, false);
    }

    /**
     * 二级分类
     */
    public static void secondClassify(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f69601887d87", callBack, false);
    }

    /**
     * 文本下载类型
     */
    public static void newTxtDown(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5ff69e9db73a2", callBack, false);
    }

    /**
     * 下单过渡页
     */
    public static void prompt(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f695595b8201", callBack, false);
    }

    /**
     * 获取下单价格
     */
    public static void orderPrice(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f714e1bd19d5", callBack, false);
    }

    /**
     * 获取对方的头像和昵称
     */
    public static void getChatInfo(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5fb39156244bb", callBack, false, false);
    }

    /**
     * 获取诉讼案件报价
     */
    public static void getLawsuitPrice(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f7d7915c7de9", callBack, true);
    }

    /**
     * 律师费计算工具
     */
    public static void toolLawyer(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8e567566222", callBack, true);
    }

    /**
     * 诉讼费计算工具w
     */
    public static void toolLawsuit(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "/5f8e772214e34", callBack, true);
    }

    /**
     * 案件类型 诉讼案件类型列表：lawsuit，律师律师费案件类型列表：types
     */
    public static void toolCaseType(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8e7d06487bb", callBack, false);
    }

    /**
     * 城市列表
     */
    public static void addressList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f55a7e5c3ae5", callBack, true);
    }

    /**
     * 热门城市
     */
    public static void addressHot(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f56e6308acfe", callBack, true);
    }

    /**
     * 工具获取省份
     */
    public static void toolAddress(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8e7ce1da09d", callBack, false);
    }

    /**
     * 获取协议 用户协议1，关于我们3，相关条款4，会员规则6，隐私政策7
     */
    public static void getAgree(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d63befcb25d9", callBack, true);
    }

    /**
     * 下单
     */
    public static void createOrder(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d784b976769e", callBack, true);
    }

    /**
     * 当地律协价格
     */
    public static void localPrice(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "60c1be5f7be5c", callBack, true);
    }

    /**
     * 获取套餐
     */
    public static void getAreaMeal(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "604acad30489a", callBack, false);
    }

    /**
     * 提现申请
     */
    public static void applyWithdraw(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5ce25d5e1ffb8", callBack, true);
    }

    /**
     * 版本更新
     */
    public static void updateVersion(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d67b2acdd34d", callBack, true);
    }

    /**
     * 城市列表
     */
    public static void cityList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f55ac0ff17b6", callBack, true);
    }

    /**
     * 首页律师列表
     */
    public static void homeLawyerList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f56fb4d74a0e", callBack, false);
    }

    /**
     * 律师详情
     */
    public static void lawyerDetail(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f59df305aca4", callBack, false);
    }

    /**
     * 律师评价
     */
    public static void lawyerEvaluate(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f7d8dfed890d", callBack, false);
    }

    /**
     * 首页律所列表
     */
    public static void homeLawFirmList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f56fb1f27801", callBack, false);
    }

    /**
     * 找律师列表
     */
    public static void searchLawyerList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6efb97b67f8", callBack, false);
    }

    /**
     * 律所详情
     */
    public static void homeLawFirmDetail(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f5725ca57d69", callBack, false);
    }

    /**
     * 合同服务联动
     */
    public static void contractService(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "6033971512c9b", callBack, false);
    }

    /**
     * 首页轮播图
     */
    public static void homeBanner(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5c94aa1a043e7", callBack, true);
    }

    /**
     * 首页跑马灯
     */
    public static void homeLamp(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f7e648e9b848", callBack, false);
    }

    /**
     * 课程列表
     */
    public static void courseList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f68251a6c66b", callBack, false);
    }

    /**
     * 我的课程列表
     */
    public static void courseMine(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6867a8edc91", callBack, false);
    }

    /**
     * 课程详情
     */
    public static void courseDetail(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6843bc19760", callBack, false);
    }

    /*********************************社群**********************************************/
    /**
     * 发布社群的悬赏金额列表
     */
    public static void communityMoney(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f73f82e9a231", callBack, true);
    }

    /**
     * 举报类型  社群举报：1，咨询举报：2
     */
    public static void reportType(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5fdb07146821b", callBack, false);
    }

    /**
     * 发布免费的社群
     */
    public static void communityFire(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6c4ebf9990d", callBack, true);
    }

    /**
     * 社群列表
     */
    public static void communityList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6da2cbb7485", callBack, true);
    }

    /**
     * 社群列表
     */
    public static void mineCommunity(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6f02e507951", callBack, true);
    }

    /**
     * 社群详情
     */
    public static void communityDetail(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6ee936664c3", callBack, true);
    }

    /**
     * 社群评论列表
     */
    public static void commentList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f817c955ae65", callBack, true);
    }

    /**
     * 课程评论列表
     */
    public static void courseCommentList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5fdb2d944b354", callBack, false);
    }

    /**
     * 社群评论的回复列表
     */
    public static void reposeList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f71a32c26c56", callBack, true);
    }

    /**
     * 社群评论的回复列表
     */
    public static void courseReposeList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5fdc376cea626", callBack, false);
    }

    /**
     * 他人主页信息
     */
    public static void otherInfo(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f732a138b6fb", callBack, true);
    }

    /**
     * 回复评论
     * user_type:用户类型1:普通用户 2:律师用户
     */
    public static void communityComment(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6c6c5a3de26", callBack, true);
    }

    /**
     * 回复评论
     * user_type:用户类型1:普通用户 2:律师用户
     */
    public static void courseCommunityComment(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5fdb2cb69acbc", callBack, true);
    }

    /**
     * 点赞
     * type 操作类型 1:点赞/取消点赞 2收藏/取消收藏
     */
    public static void communityPraise(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6c63be3c290", callBack, true);
    }

    /**
     * 举报
     */
    public static void communityReport(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f732705eab91", callBack, true);
    }

    /**
     * 获取系统消息列表
     * type 返回数据类型1-用户未读消息数 2-用户未读消息列表 3-用户所有消息列表
     */
    public static void messageList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8e500628d68", callBack, false);
    }

    /****************************个人中心************************/
    /**
     * 获取个人信息
     */
    public static void mineData(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5c78c4772da97", callBack, true);
    }

    /**
     * 修改个人信息
     */
    public static void updateData(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5cb54af125f1c", callBack, true);
    }

    /**
     * 收益明细
     */
    public static void profitList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f72d2d40caab", callBack, false);
    }

    /**
     * 修改手机号
     */
    public static void updatePhone(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d5f4c28b8636", callBack, true);
    }

    /**
     * 帮助中心
     * 帮助中心2，接单攻略5
     */
    public static void helpList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d648c8d37977", callBack, true);
    }

    /**
     * 获取文档详情
     */
    public static void helpDetail(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d64a46459991", callBack, true);
    }

    /**
     * 意见反馈
     */
    public static void feedback(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5cc3f28296cf0", callBack, true);
    }

    /**
     * 申请发票设置信息
     */
    public static void applyTicket(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6c65161737c", callBack, true);
    }

    /**
     * 获取我设置的发票信息
     */
    public static void getTicket(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8570994042d", callBack, false);
    }

    /**
     * 我的订单列表
     */
    public static void orderList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6eec36a2407", callBack, false);
    }

    /**
     * 服务订单详情
     */
    public static void orderDetail(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6ef2473a5fb", callBack, false);
    }

    /**
     * 获取vip列表
     */
    public static void vipList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d9610bdb631b", callBack, false);
    }

    /**
     * 更多案例
     */
    public static void moreCase(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8fb334efb3c", callBack, true);
    }

    /**
     * 我的vip列表
     */
    public static void getMineVip(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f72935b8a06d", callBack, false);
    }

    /**
     * 支付宝支付
     */
    public static void alipay(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d7a088403825", callBack, true);
    }

    /**
     * 微信支付
     */
    public static void wxPay(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d7868c138418", callBack, true);
    }

    /**
     * 我的收益
     */
    public static void profitData(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f72d2a681fcd", callBack, true);
    }

    /**
     * 直接推荐
     */
    public static void recommendDirect(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5d8cacff766f8", callBack, false);
    }

    /**
     * 间接推荐
     */
    public static void recommendIndirect(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f729a6396758", callBack, false);
    }

    /**
     * 推广码
     */
    public static void recommendCode(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f7bdbecc79ec", callBack, false);
    }

    /**
     * 提现
     */
    public static void withdraw(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5ce25d5e1ffb8", callBack, true);
    }

    /**
     * 获取可提现余额
     */
    public static void withdrawBalance(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5cc45274d6be9", callBack, false);
    }

    /**
     * 对咨询订单进行评价
     */
    public static void communityEvaluate(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f88078174b00", callBack, true);
    }

    /**
     * 选定律师
     */
    public static void selectLawyer(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6ef9045a981", callBack, true);
    }

    /**
     * 平台客服
     */
    public static void platformCustom(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5fb50321cd398", callBack, false);
    }

    /**
     * 对订单进行评价
     */
    public static void orderEvaluate(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f7d869c70c9b", callBack, true);
    }

    /**
     * 获取评价标签
     */
    public static void evaluateLabel(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f59e97f68b32", callBack, false);
    }

    /**
     * 我的咨询列表
     * type 0:未回复 1:已回复
     */
    public static void serviceList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f6efd7c4cde8", callBack, true);
    }

    /**
     * 获取天数计算器
     */
    public static void toolDay(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8d5dfcd4254", callBack, true);
    }

    /**
     * 利息计算
     */
    public static void toolRate(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8ecd782f611", callBack, true);
    }

    /**
     * 利率列表
     */
    public static void toolRateList(Activity mContent, Map<String, Object> params, MyCallBack callBack) {
        getData(mContent, params, "5f8ece16c5261", callBack, false);
    }


    public static void getData(Activity mContent, Map<String, Object> params, String cmd, MyCallBack callBack
            , boolean isPost) {
        StyledDialogUtils.getInstance().loading(mContent);
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        if (params.isEmpty()) {
            params.put("aaa", "a");
        }
        if (isPost) {
            builder.post();
        } else {
            builder.get();
        }
        builder.url(NetUrlUtils.BASEURL + cmd)
                .params(params)
                .build().enqueue(mContent, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String o, String msg) {
                StyledDialogUtils.getInstance().dismissLoading();
                callBack.onSuccess(o, msg);
            }

            @Override
            public void onError(int code, String msg) {
                StyledDialogUtils.getInstance().dismissLoading();
                callBack.onError(msg, code);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                StyledDialogUtils.getInstance().dismissLoading();
                callBack.onFail(call, e);
            }
        });
    }

    public static void getData(Activity mContent, Map<String, Object> params, String cmd, MyCallBack callBack
            , boolean isPost, boolean noLoading) {
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        if (params.isEmpty()) {
            params.put("aaa", "a");
        }
        if (isPost) {
            builder.post();
        } else {
            builder.get();
        }
        builder.url(NetUrlUtils.BASEURL + cmd)
                .params(params)
                .build().enqueue(mContent, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String o, String msg) {
                callBack.onSuccess(o, msg);
            }

            @Override
            public void onError(int code, String msg) {
                callBack.onError(msg, code);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFail(call, e);
            }
        });
    }

    //图片上传
    public static void uploadPhoto(Activity mContent, List<LocalMedia> mSelectList, MyCallBack callBack) {
        StyledDialogUtils.getInstance().loading(mContent);
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        for (int i = 0; i < mSelectList.size(); i++) {
            builder.addFile("file[]", "" + new File(MyApplication.selectPhotoShow(mContent, mSelectList.get(i))).getName()
                    , new File(MyApplication.selectPhotoShow(mContent, mSelectList.get(i))));
        }
        builder.url(NetUrlUtils.BASEURL + "5d5fa8984f0c2")
                .post()
                .build().enqueue(mContent, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String o, String msg) {
                StyledDialogUtils.getInstance().dismissLoading();
                callBack.onSuccess(o, msg);
            }

            @Override
            public void onError(int code, String msg) {
                StyledDialogUtils.getInstance().dismissLoading();
                callBack.onError(msg, code);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                StyledDialogUtils.getInstance().dismissLoading();
                callBack.onFail(call, e);
            }
        });
    }

}
