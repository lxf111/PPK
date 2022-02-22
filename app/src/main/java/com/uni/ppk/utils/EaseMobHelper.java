package com.uni.ppk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.StyledDialogUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;

/**
 * Author:zhn
 * Time:2019/5/25 0025 14:18
 * <p>
 * 环信助手
 */
public class EaseMobHelper {

    //AppKey,获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
//    public static String APP_KEY = "1447210428068994#kefuchannelapp93290";
    public static String APP_KEY = "1113200908042886#suwenlawyer";

    //TenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
    private static String TENANT_ID = "135490";

    //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
    public static String SERVICE_IM_NUMBER = "kefuchannelimid_349358";

    /**
     * 1.1 初始化
     * 以客服模式初始化,可以同时使用即时通讯
     *
     * @param context
     */
//    public static void init(Context context) {
//        ChatClient.Options options = new ChatClient.Options();
//        options.setAppkey(APP_KEY);//必填项
//        options.setTenantId(TENANT_ID);//必填项
//        // Kefu SDK 初始化
//        if (!ChatClient.getInstance().init(context, options)) {
//            return;
//        }
//        // Kefu EaseUI的初始化
//        UIProvider.getInstance().init(context);
//        //后面可以设置其他属性
//    }

    /**
     * 1.2 初始化
     * 仅初始化即时通讯，不支持客服
     *
     * @param context
     */
    public static void initOnlyIM(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
//      options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        options.setAppKey(APP_KEY);
        options.setAutoLogin(false);
//        EMClient.getInstance().removeMultiDeviceListener(new EMMultiDeviceListener() {
//            @Override
//            public void onContactEvent(int event, String target, String ext) {
//
//            }
//
//            @Override
//            public void onGroupEvent(int event, String target, List<String> usernames) {
//
//            }
//        });
        //初始化
        EMClient.getInstance().init(context.getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(context.getApplicationContext(), options);
    }

    /**
     * 1.3  打开客服页面
     * <p>
     * 【前置条件 1.1 客服模式初始化】
     *
     * @param mContext
     * @param user_id  环信用户ID
     * @param psw      环信用户ID
     */
//    public static void callServiceIM(final Activity mContext, final String user_id, String psw) {
//        //检查登录状态
//        if (ChatClient.getInstance().isLoggedInBefore()) {
//            //打开客服页面
//            Intent intent = new IntentBuilder(mContext)
//                    .setServiceIMNumber(SERVICE_IM_NUMBER)
//                    .build();
//            mContext.startActivity(intent);
//        } else {
//            //未登录，需要登录后，再进入客服页面
//            ChatClient.getInstance().login(user_id, psw, new Callback() {
//                @Override
//                public void onSuccess() {
//                    //打开客服页面
//                    Intent intent = new IntentBuilder(mContext)
//                            .setServiceIMNumber(SERVICE_IM_NUMBER)
//                            .build();
//                    mContext.startActivity(intent);
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    mContext.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(mContext, "登陆失败，请重试！", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onProgress(int i, String s) {
//
//                }
//            });
//        }
//    }

    /**
     * 1.3  打开聊天页面
     *
     * @param mContext
     * @param cls
     * @param user_id
     */
    public static void callChatIM(Activity mContext, Class<?> cls, String name, String user_id, boolean isFriend) {
        if (MyApplication.mPreferenceProvider.getUId().equals(user_id)) {
            ToastUtils.show(mContext, "不能和自己聊天");
            return;
        }
        if (isLoggedIn()) {
            //已经登录，可以直接进入会话界面
            Intent chat = new Intent(mContext, cls);
            chat.putExtra(EaseConstant.EXTRA_USER_ID, user_id);  //对方账号
            chat.putExtra("isCheckFriend", isFriend);  //对方账号
            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
            chat.putExtra("name", name); //单聊模式
            chat.putExtra("myHeader", MyApplication.mPreferenceProvider.getPhoto()); //单聊模式
            chat.putExtra("myName", MyApplication.mPreferenceProvider.getUserName()); //单聊模式
            mContext.startActivity(chat);
        }else{
            StyledDialogUtils.getInstance().loading(mContext);
            EMClient.getInstance().login("" + MyApplication.mPreferenceProvider.getUId(),
                    "abc123456", new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            StyledDialogUtils.getInstance().dismissLoading();
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Intent chat = new Intent(mContext, cls);
                            chat.putExtra(EaseConstant.EXTRA_USER_ID, user_id);  //对方账号
                            chat.putExtra("isCheckFriend", isFriend);  //对方账号
                            chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
                            chat.putExtra("name", name); //单聊模式
                            chat.putExtra("myHeader", MyApplication.mPreferenceProvider.getPhoto()); //单聊模式
                            chat.putExtra("myName", MyApplication.mPreferenceProvider.getUserName()); //单聊模式
                            mContext.startActivity(chat);
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {
                            StyledDialogUtils.getInstance().dismissLoading();
                            LogUtils.e("TAG", "登录聊天服务器失败！");
                            mContext.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.show(mContext, "登录失效，请重新登录");
                                }
                            });
                        }
                    });
        }
    }

    /**
     * 1.3  打开聊天页面
     *
     * @param mContext
     * @param cls
     * @param user_id
     */
    public static void callChatGoodsIM(Activity mContext, Class<?> cls
            , String name, String user_id, boolean isFriend
            , String goodsName, String goodsPrice, String goodsType, String goodsImg, String goodsId) {
        if (MyApplication.mPreferenceProvider.getUId().equals(user_id)) {
            ToastUtils.show(mContext, "不能和自己聊天");
            return;
        }
        //已经登录，可以直接进入会话界面
        Intent chat = new Intent(mContext, cls);
        chat.putExtra(EaseConstant.EXTRA_USER_ID, user_id);  //对方账号
        chat.putExtra("isCheckFriend", isFriend);  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式
        chat.putExtra("name", name);
        chat.putExtra("goodsName", goodsName);
        chat.putExtra("goodsPrice", goodsPrice);
        chat.putExtra("goodsType", goodsType);
        chat.putExtra("goodsImg", goodsImg);
        chat.putExtra("goodsId", goodsId);
        mContext.startActivity(chat);
    }

    /**
     * 1.4 是否已登录
     *
     * @return
     */
    public static boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * 1.5 监听语音通话呼入
     *
     * @return
     */
//    public static CallReceiver initIncomingCallListener(Context context) {
//        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
//        CallReceiver callReceiver = new CallReceiver();
//        //register incoming call receiver
//        context.registerReceiver(callReceiver, callFilter);
//        return callReceiver;
//    }
//
//    /**
//     * 1.6 打开语音通话聊天页面
//     *
//     * @param mContext
//     * @param cls
//     * @param from_uid 当前拨号的用户
//     * @param pwd      当前用户的环信密码
//     * @param to_uid   呼叫的用户ID
//     */
//    public static void callVoiceChat(final Activity mContext, Class<?> cls, String from_uid, String pwd, final String to_uid) {
//        //检查登录状态
//        if (isLoggedIn()) {
//            mContext.startActivity(new Intent(mContext, VoiceCallActivity.class)
//                    .putExtra("username", to_uid)
//                    .putExtra("isComingCall", false)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        } else {
//            //未登录，需要登录后，再进入客服页面
//            EMClient.getInstance().login(from_uid, pwd, new EMCallBack() {
//                @Override
//                public void onSuccess() {
//                    //打开客服页面
//                    mContext.startActivity(new Intent(mContext, VoiceCallActivity.class)
//                            .putExtra("username", to_uid)
//                            .putExtra("isComingCall", false)
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    mContext.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(mContext, "登陆失败，请重试！",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onProgress(int i, String s) {
//
//                }
//            });
//        }
//    }

}
