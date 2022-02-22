package com.uni.ppk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.ui.login.LoginActivity;
import com.uni.ppk.ui.login.bean.LoginBean;
import com.uni.ppk.ui.mine.bean.PersonDataBean;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.kongzue.dialog.v3.MessageDialog;

//import com.hyphenate.chat.EMClient;

/**
 * 用户是否登录检测工具类
 * Created by Administrator on 2017/11/28.
 */
public class LoginCheckUtils {
    private static final String TAG = "LoginCheckUtils";

    //验证是否登录的异步回调
    public interface CheckCallBack {
        /**
         * 检查结果
         *
         * @param flag 是否登录
         *             true：已登录；false：未登录
         */
        void onCheckResult(boolean flag);
    }

    /**
     * 检查用户是否登录
     *
     * @param context
     * @return
     */
    public static boolean checkUserIsLogin(Context context) {
        String uid = MyApplication.mPreferenceProvider.getUId();
        String token = MyApplication.mPreferenceProvider.getToken();
        if (!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(token)) {
            return true;
        }
        return false;
    }

    /**
     * 检查用户是否登录
     *
     * @param activity
     * @param callBack
     * @return
     */
    public static void checkUserIsLogin(Activity activity, CheckCallBack callBack) {
        String uid = MyApplication.mPreferenceProvider.getUId();
        String token = MyApplication.mPreferenceProvider.getToken();
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(token)) {
            //清除用户信息
            clearUserInfo(activity);
            //验证结果回调
            callBack.onCheckResult(false);
            //显示验证弹窗
            showLoginDialog(activity, false);
            return;
        }
       /* BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
//                .url(NetUrlUtils.GET_USER_INFO)
                .post().build().enqueue(activity, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                String user = JSONUtils.getNoteJson(result, "user");
                //更新当前的登录用户信息
                if (!StringUtils.isEmpty(user)) {
                    UserInfoBean userBean = JSONUtils.jsonString2Bean(user, UserInfoBean.class);
                    if (userBean != null) {
                        LoginCheckUtils.updateUserInfo(userBean);
                    }
                }
                callBack.onCheckResult(true);
            }

            @Override
            public void onError(int code, String msg) {
                //清除用户信息
                clearUserInfo(activity);
                //验证结果回调
                callBack.onCheckResult(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //清除用户信息
                clearUserInfo(activity);
                //验证结果回调
                callBack.onCheckResult(false);
                //显示验证弹窗
                showLoginDialog(activity, false);
            }
        });*/
    }

    /**
     * 提示用户登录对话框
     *
     * @param context         //上下文
     * @param isClearUserInfo //是否清除用户信息
     */
    public static void showLoginDialog(Activity context, boolean isClearUserInfo) {
        try {
            MessageDialog.show((AppCompatActivity) context, "温馨提示", "您还没有登录，请先登录！", "确定", "取消")
                    .setOnOkButtonClickListener((baseDialog, v) -> {
                        if (isClearUserInfo) LoginCheckUtils.clearUserInfo(context);
                        //TODO  调转对应页面
                        context.startActivity(new Intent(context, LoginActivity.class));
                        return false;
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
            MyApplication.mContext.startActivity(new Intent(MyApplication.mContext, LoginActivity.class));
        }
    }

    //检查是否登录
    public static boolean checkLoginShowDialog(Activity mContext) {
        if (!LoginCheckUtils.checkUserIsLogin(mContext)) {
            LoginCheckUtils.showLoginDialog(mContext, false);
            return false;

        }
        return true;
    }

    /**
     * 更新会员等级
     */
    public static void updateLevel(String level) {
        MyApplication.mPreferenceProvider.setLevel("" + level);
    }

    /**
     * 更新用户信息
     *
     * @param infoBean 用户信息
     */
    public static void updateUserInfo(PersonDataBean infoBean) {
        MyApplication.mPreferenceProvider.setInvitationCode("" + infoBean.getInvite_code());
        MyApplication.mPreferenceProvider.setPhoto("" + infoBean.getHead_img());
        MyApplication.mPreferenceProvider.setUserName("" + infoBean.getUser_nickname());
    }

    //保存登录信息
    public static void saveLoginInfo(LoginBean bean) {
        MyApplication.mPreferenceProvider.setToken("" + bean.getUser_token());
        MyApplication.mPreferenceProvider.setUId("" + bean.getId());
        MyApplication.mPreferenceProvider.setUserName("" + bean.getUser_nickname());
        MyApplication.mPreferenceProvider.setMobile("" + bean.getMobile());
        MyApplication.mPreferenceProvider.setPhoto("" + bean.getHead_img());
        MyApplication.mPreferenceProvider.setLevel("" + bean.getUser_vip());
        EMClient.getInstance().login("" + MyApplication.mPreferenceProvider.getUId(),
                "abc123456", new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        LogUtils.e("TAG", "登录聊天服务器成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        LogUtils.e("TAG", "登录聊天服务器失败！");
                        registerIm();
                    }
                });
//        login();
    }

    private static void login() {
//        ChatClient.getInstance().login(MyApplication.mPreferenceProvider.getUId()
//                , "abc123456", new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.e("TAG", "onSuccess");
//                    }
//
//                    @Override
//                    public void onError(int code, String error) {
//                        Log.e("TAG", "onError=" + error);
//                        register();
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//                        Log.e("TAG", "onProgress=" + status);
//                    }
//                });
    }

    private static void register() {
//        ChatClient.getInstance().register("" + MyApplication.mPreferenceProvider.getUId()
//                , "abc123456", new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        login();
//                    }
//
//                    @Override
//                    public void onError(int code, String error) {
//
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//                });
    }

    //注册环信
    public static void registerIm() {
        try {
            EMClient.getInstance().createAccount("" + MyApplication.mPreferenceProvider.getUId(), "abc123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空登录信息和授权信息
    public static void clearUserInfo(Activity activity) {
        MyApplication.mPreferenceProvider.setToken("");
        MyApplication.mPreferenceProvider.setUId("");
        MyApplication.mPreferenceProvider.setUserName("");
        MyApplication.mPreferenceProvider.setUserType("");
        MyApplication.mPreferenceProvider.setMobile("");
        MyApplication.mPreferenceProvider.setPhoto("");
        MyApplication.mPreferenceProvider.setShopId("");
        MyApplication.mPreferenceProvider.setIdNumber("");
        MyApplication.mPreferenceProvider.setRealName("");
        MyApplication.mPreferenceProvider.setIMUserName("");
        MyApplication.mPreferenceProvider.setInvitationCode("");
        MyApplication.mPreferenceProvider.setLevel("0");
        MyApplication.mPreferenceProvider.setLastTime("");
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                LogUtils.e("TAG", "退出聊天服务器成功");
            }

            @Override
            public void onError(int code, String error) {
                LogUtils.e("TAG", "退出聊天服务器失败");
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
        clearAuthLogin(activity);
//        mSpUtils = new SpSaveListUtils(activity, mSpTag);
//        mSpUtils.clearList(mSpTag);
    }

    //保存历史记录工具类
    private static SpSaveListUtils mSpUtils;
    //历史记录类型
    private static String mSpTag = "lawyer_user";

    //清除授权信息  例如qq 微信  等第三方授权
    private static void clearAuthLogin(Activity activity) {

    }
}
