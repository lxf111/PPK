package com.uni.ppk.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.OpenAuthTask;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.StyledDialogUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MainActivity;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.http.BaseCallBack;
import com.uni.ppk.http.BaseOkHttpClient;
import com.uni.ppk.ui.login.bean.DouYinBean;
import com.uni.ppk.ui.login.bean.LoginBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.utils.TimerUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Create by wanghk on 2019-05-28.
 * Describe:密码登录
 */
public class PwdLoginActivity extends BaseActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_86)
    TextView tv86;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.rlyt_code)
    RelativeLayout rlytCode;
    @BindView(R.id.iv_watch)
    ImageView ivWatch;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.rlyt_pwd)
    RelativeLayout rlytPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_code_login)
    TextView tvCodeLogin;
    @BindView(R.id.tv_register_account)
    TextView tvRegisterAccount;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.iv_wx_login)
    ImageView ivWxLogin;

    private boolean isWatchPwd = false;//密码不可见

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//rxbus取消订阅

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pwd_login;
    }

    @Override
    protected void initData() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
        RxBus.getInstance().toObservable(DouYinBean.class)
                .subscribe(new Observer<DouYinBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DouYinBean s) {
                        if (s != null) {
                            douYinLogin(s.getAuthCode());
//                            thirdLogin(s.getAuthCode(), "3", "", "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void douYinLogin(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_key", "awd5db9he1zf5ifx");
        params.put("client_secret", "98ad0595c6ab219241c618e390a1e03e");
        params.put("code", "" + code);
        params.put("grant_type", "authorization_code");
        BaseOkHttpClient.Builder builder = BaseOkHttpClient.newBuilder();
        builder.get();
        builder.url("https://open.douyin.com/oauth/access_token/")
                .params(params)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String o, String msg) {
                if (StringUtils.isEmpty(o)) {
                    ToastUtils.show(mContext, "获取授权失败");
                    return;
                }
                thirdLogin(JSONUtils.getNoteJson(o, "access_token"), "3", "", "");
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, "获取授权失败");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.show(mContext, "" + getString(R.string.service_error));
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_code_login, R.id.tv_code, R.id.iv_watch
            , R.id.tv_login, R.id.tv_register_account, R.id.tv_forget
            , R.id.iv_wx_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code_login:
                onBackPressed();
                break;
            case R.id.tv_code:
                sendMessage();
                break;
            //查看密码
            case R.id.iv_watch:
                if (!isWatchPwd) {
                    isWatchPwd = true;
                    ivWatch.setImageResource(R.mipmap.ic_login_watch_pwd);
                    //从密码不可见模式变为密码可见模式
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isWatchPwd = false;
                    ivWatch.setImageResource(R.mipmap.ic_login_no_watch_pwd);
                    //从密码可见模式变为密码不可见模式
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_register_account:
                MyApplication.openActivity(mContext, RegisterActivity.class);
                break;
            case R.id.tv_forget:
                MyApplication.openActivity(mContext, RetrievePasswordActivity.class);
                break;
            //微信登录
            case R.id.iv_wx_login:
//                if (!PlatformUtils.isWeixinAvilible(mContext)) {
//                    ToastUtils.show(mContext, "请安装微信");
//                    return;
//                }
//                loginByThirdPlatform(SHARE_MEDIA.WEIXIN);
                login();
                break;
        }
    }

    private void douYinLogin() {
        DouYinOpenApi douyinOpenApi = DouYinOpenApiFactory.create(this);

        Authorization.Request request = new Authorization.Request();
        request.scope = "user_info";                          // 用户授权时必选权限
        request.state = "ww";                                 // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        request.callerLocalEntry = "com.benben.suwenuser.douyin.DouYinEntryActivity";
        douyinOpenApi.authorize(request);
    }

    //登录
    private void login() {
        String phone = edtPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("phone", "" + phone);
        MyApplication.openActivity(mContext, CodeActivity.class, bundle);
//        if (StringUtils.isEmpty(password)) {
//            toast(getString(R.string.password_not_null));
//            return;
//        }
//        if (password.length() < 6) {
//            ToastUtils.show(mContext, "密码至少六位");
//            return;
//        }
//        Map<String, Object> params = new HashMap<>();
//        params.put("mobile", "" + phone);
//        params.put("password", "" + password);
//        HttpUtils.login(mContext, params, new MyCallBack() {
//            @Override
//            public void onSuccess(String response, String msg) {
//                LoginBean bean = JSONUtils.parserObject(response, "userinfo", LoginBean.class);
//                if (bean != null) {
//                    LoginCheckUtils.saveLoginInfo(bean);
//                    MyApplication.openActivity(mContext, MainActivity.class);
//                    finish();
//                } else {
//                    ToastUtils.show(mContext, msg);
//                }
//            }
//
//            @Override
//            public void onError(String msg, int code) {
//                ToastUtils.show(mContext, msg);
//            }
//
//            @Override
//            public void onFail(Call call, IOException e) {
//                ToastUtils.show(mContext, getString(R.string.service_error));
//            }
//        });
    }

    //验证码登录
    private void codeLogin() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();

        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(code)) {
            ToastUtils.show(mContext, "请输入验证码");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "" + phone);
        params.put("type", "3");
        params.put("code", "" + code);
        HttpUtils.codeLogin(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LoginBean bean = JSONUtils.parserObject(response, "userinfo", LoginBean.class);
                if (bean != null) {
                    LoginCheckUtils.saveLoginInfo(bean);
                    MyApplication.openActivity(mContext, MainActivity.class);
                    finish();
                } else {
                    ToastUtils.show(mContext, msg);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    //发送短信
    private void sendMessage() {
        String phone = edtPhone.getText().toString().trim();
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "" + phone);
        params.put("type", "3");
        HttpUtils.sendMessage(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                TimerUtil timerUtil = new TimerUtil(tvCode);
                timerUtil.timers();
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    /**
     * 微信登录
     *
     * @param platform
     */
    private void loginByThirdPlatform(SHARE_MEDIA platform) {
        StyledDialogUtils.getInstance().loading(mContext);
        UMAuthListener authListener = new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param share_media 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            /**
             * 授权成功的回调
             * @param share_media
             * @param i
             * @param map
             */
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                StyledDialogUtils.getInstance().dismissLoading();
                if (share_media == SHARE_MEDIA.QQ) {
//                    thirdLogin(map.get("openid"), "2");
                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    mWxId = map.get("openid");
                    thirdLogin(map.get("unionid"), "1", map.get("iconurl"), map.get("name"));
                }
                LogUtils.e("TAG", "三方登录----" + JSONUtils.toJsonString(map));
            }

            /**
             *授权失败的回调
             * @param share_media
             * @param i
             * @param throwable
             */
            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtils.e("TAG", throwable.getLocalizedMessage());
                toast("授权失败");
                StyledDialogUtils.getInstance().dismissLoading();
            }

            /**
             *  授权取消的回调
             * @param share_media
             * @param i
             */
            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                toast("授权取消");
                StyledDialogUtils.getInstance().dismissLoading();
            }
        };
        //三方授权
        UMShareAPI.get(mContext).getPlatformInfo(mContext, platform, authListener);
    }

    private String mWxId = "";

    private void thirdLogin(String openId, String type, String header, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "" + type);
        //type【登录类型1：微信，2：QQ，3：抖音，4：支付宝】
        switch (type) {
            case "1":
                params.put("wx_unionid", "" + openId);
                break;
            case "2":
                params.put("qq_unionid", "" + openId);
                break;
            case "3":
                params.put("dy_unionid", "" + openId);
                break;
            case "4":
                params.put("alipay_unionid", "" + openId);
                break;
        }
        HttpUtils.thirdLogin(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LoginBean bean = JSONUtils.parserObject(response, "userinfo", LoginBean.class);
                if (bean != null) {
                    LoginCheckUtils.saveLoginInfo(bean);
                    MyApplication.openActivity(mContext, MainActivity.class);
                    finish();
                } else {
                    ToastUtils.show(mContext, msg);
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (code == -999) {
                    Bundle bundle = new Bundle();
                    bundle.putString("openId", "" + openId);
                    bundle.putString("type", "" + type);
                    bundle.putString("wxId", "" + mWxId);
                    bundle.putString("header", "" + header);
                    bundle.putString("nickName", "" + name);
                    MyApplication.openActivity(mContext, BindPhoneActivity.class, bundle);
                } else {
                    ToastUtils.show(mContext, msg);
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    /**
     * 通用跳转授权业务的回调方法。
     * 此方法在支付宝 SDK 中为弱引用，故你的 App 需要以成员变量等方式保持对 Callback 的强引用，
     * 以免对象被回收。
     * 以局部变量保持对 Callback 的引用是不可行的。
     */
    final OpenAuthTask.Callback openAuthCallback = new OpenAuthTask.Callback() {
        @Override
        public void onResult(int resultCode, String memo, Bundle bundle) {
            if (resultCode == OpenAuthTask.OK) {
                String authCode = bundle.getString("auth_code");
                LogUtils.e("TAG", "authCode=" + authCode);
                if (StringUtils.isEmpty(authCode)) {
                    return;
                }
                getAliLogin(authCode);
//                showAlert(PayDemoActivity.this, String.format("业务成功，结果码: %s\n结果信息: %s\n结果数据: %s", resultCode, memo, bundleToString(bundle)));
            } else {
                LogUtils.e("TAG", "业务失败，结果码:" + resultCode);
                ToastUtils.show(mContext, "取消支付宝登录");
//                showAlert(PayDemoActivity.this, String.format("业务失败，结果码: %s\n结果信息: %s\n结果数据: %s", resultCode, memo, bundleToString(bundle)));
            }
        }
    };

    /**
     * 通用跳转授权业务 Demo
     */
    public void openAuthScheme() {
        // 传递给支付宝应用的业务参数
        final Map<String, String> bizParams = new HashMap<>();
        bizParams.put("url",
                "https://authweb.alipay.com/auth?auth_type=PURE_OAUTH_SDK&app_id=2021001193682120&scope=auth_user&state=init");

        // 支付宝回跳到你的应用时使用的 Intent Scheme。请设置为不和其它应用冲突的值。
        // 请不要像 Demo 这样设置为 __alipaysdkdemo__!
        // 如果不设置，将无法使用 H5 中间页的方法(OpenAuthTask.execute() 的最后一个参数)回跳至
        // 你的应用。
        //
        // 注意！参见 AndroidManifest 中 <AlipayResultActivity> 的 android:scheme，此两处
        // 必须设置为相同的值。
        final String scheme = "suwenuser";

        // 唤起授权业务
        final OpenAuthTask task = new OpenAuthTask(this);
        task.execute(
                scheme, // Intent Scheme
                OpenAuthTask.BizType.AccountAuth, // 业务类型
                bizParams, // 业务参数
                openAuthCallback, // 业务结果回调。注意：此回调必须被你的应用保持强引用
                true); // 是否需要在用户未安装支付宝 App 时，使用 H5 中间页中转。建议设置为 true。
    }

    //支付宝登录
    private void getAliLogin(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", "" + code);
        HttpUtils.aliLogin(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String userId = JSONUtils.getNoteJson(response, "user_id");
                if (!StringUtils.isEmpty(userId)) {
                    thirdLogin(userId, "4", "", "");
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

}
