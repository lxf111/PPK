package com.uni.ppk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import com.uni.commoncore.utils.AppUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ThreadPoolUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.adapter.MainViewPagerAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.receiver.ExampleUtil;
import com.uni.ppk.ui.community.CommunityFragment;
import com.uni.ppk.ui.community.activity.PublicServiceActivity;
import com.uni.ppk.ui.home.fragment.HomeFragment;
import com.uni.ppk.ui.message.MessageFragment;
import com.uni.ppk.ui.mine.bean.VersionBean;
import com.uni.ppk.ui.mine.fragment.MineFragment;
import com.uni.ppk.ui.service.ServiceFragment;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.utils.OrderAgreeUtils;
import com.uni.ppk.widget.NoScrollViewPager;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.zjn.updateapputils.util.CheckVersionRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.rb_main_home)
    RadioButton rbMainHome;
    @BindView(R.id.rb_main_discount)
    RadioButton rbMainDiscount;
    @BindView(R.id.rb_main_tea)
    RadioButton rbMainTea;
    @BindView(R.id.rb_main_service)
    RadioButton rbMainService;
    @BindView(R.id.rb_main_mine)
    RadioButton rbMainMine;
    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.status_bar_view)
    View statusBarView;

    private FragmentManager mFragmentManager;

    private long mPressedTime = 0;//??????????????????
    private int mStatusBarHeight;

    private MessageFragment teaFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//rxbus????????????

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        //?????? ??????????????????
//        rbMainService.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.CAMERA
                    , Manifest.permission.READ_LOGS
                    , Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.SET_DEBUG_APP
                    , Manifest.permission.SYSTEM_ALERT_WINDOW
                    , Manifest.permission.GET_ACCOUNTS
                    , Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
        ArrayList<LazyBaseFragments> lazyBaseFragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        CommunityFragment discountFragment = new CommunityFragment();
        ServiceFragment serviceFragment = new ServiceFragment();
        teaFragment = new MessageFragment();
        MineFragment mineFragment = new MineFragment();
        lazyBaseFragments.add(homeFragment);
        lazyBaseFragments.add(discountFragment);
        lazyBaseFragments.add(serviceFragment);
        lazyBaseFragments.add(teaFragment);
        lazyBaseFragments.add(mineFragment);
        vpMain.setAdapter(new MainViewPagerAdapter(mFragmentManager, lazyBaseFragments));
        StatusBarUtils.setAndroidNativeLightStatusBar(mContext, true);

        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        if ("jumpCommunity".equals(s)) {
                            vpMain.setCurrentItem(1);
                            //?????????????????????????????????
                            StatusBarUtils.setAndroidNativeLightStatusBar(mContext, true);
                            rbMainDiscount.setChecked(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {
            //????????????????????????????????????????????????
//            checkVersionUpdate();
        }
        rbMainTea.setEnabled(false);
        if (!StringUtils.isEmpty(MyApplication.mPreferenceProvider.getToken())) {
//            if (EaseMobHelper.isLoggedIn()) {
//                return;
//            }
//            ChatClient.getInstance().login("" + MyApplication.mPreferenceProvider.getUId()
//                    , "abc123456" , new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            EMClient.getInstance().groupManager().loadAllGroups();
//                            EMClient.getInstance().chatManager().loadAllConversations();
//                            LogUtils.e("TAG", "??????????????????????????????");
//                        }
//
//                        @Override
//                        public void onError(int code, String error) {
//                            LogUtils.e("TAG", "??????????????????????????????");
//                        }
//
//                        @Override
//                        public void onProgress(int progress, String status) {
//
//                        }
//                    });
            EMClient.getInstance().login("" + MyApplication.mPreferenceProvider.getUId(),
                    "abc123456", new EMCallBack() {//??????
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            LogUtils.e("TAG", "??????????????????????????????");
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String message) {
                            LogUtils.e("TAG", "??????????????????????????????");
                        }
                    });
        }

        //?????????????????????????????????listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        chectNotice();
        setAlias();

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(this)) {
//                //???????????????????????????
//                ToastUtils.show(mContext, "????????????????????????");
////                getOverlayPermission();
//            } else {
//                if (FloatWindow.get() != null) {
//                    FloatWindow.get().show();
//                }
//            }
//        }

//        if (FloatWindow.get() == null) {
//            View view = LayoutInflater.from(this).inflate(R.layout.view_audio_float, null);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        if (!LoginCheckUtils.checkUserIsLogin(mContext)) {
//                            ToastUtils.show(mContext, "????????????????????????");
//                            return;
//                        }
////                        if (ChatClient.getInstance().isLoggedInBefore()) {
////                            VisitorInfo info = ContentFactory.createVisitorInfo(null);
////                            String name = MyApplication.mPreferenceProvider.getUserName();
////                            if (StringUtils.isEmpty(name)) {
////                                name = MyApplication.mPreferenceProvider.getMobile();
////                            }
////                            info.nickName("" + name)
////                                    .name("" + name)
////                                    .qq("")
////                                    .companyName("")
////                                    .description("")
////                                    .phone("" + MyApplication.mPreferenceProvider.getMobile())
////                                    .email("");
////                            LogUtils.e("TAG", "info=" + JSONUtils.toJsonString(info));
////                            //?????????????????????????????????????????????
////                            Intent intent = new IntentBuilder(mContext).setServiceIMNumber(EaseMobHelper.SERVICE_IM_NUMBER) //???????????????kefu.easemob.com????????????????????? > ???????????? > ??????APP????????????????????????IM????????????
////                                    .build();
////                            intent.putExtra(Config.EXTRA_SHOW_NICK, false);
////                            intent.putExtra(Config.EXTRA_VISITOR_INFO, info);
////                            intent.putExtra("headIm", "" + NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getPhoto()));
////                            startActivity(intent);
////                        } else {
////                            //???????????????????????????????????????????????????
////                            toast("???????????????");
////                        }
////                        EaseMobHelper.callServiceIM(mContext
////                                ,MyApplication.mPreferenceProvider.getUId()
////                                ,"abc123456");
//                        OrderAgreeUtils.jumpChat(mContext);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            int width = DensityUtil.dp2px(50);
//            FloatWindow
//                    .with(MyApplication.getContext())
//                    .setView(view)
//                    .setWidth(width)            //??????????????????
//                    .setHeight(DensityUtil.dp2px(50))
//                    .setX(MyApplication.getWidth() - DensityUtil.dp2px(50))    //????????????????????????
//                    .setY(MyApplication.getHeight() - DensityUtil.dp2px(200))
//                    .setDesktopShow(true)//??????????????????
//                    .setMoveType(MoveType.slide)
//                    .setPermissionListener(new PermissionListener() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onFail() {
//
//                        }
//                    })
//                    .setFilter(true, BaseActivity.class)
//                    .build();
//        }
//        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    /**
     * ??????app??????????????????
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        /**
         * ??????Android??????????????????????????????App
         */
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * App???????????????
     */
    public boolean isForeground = false;

    @Override
    protected void onPause() {
        super.onPause();
        if (isForeground) {
            //????????????????????????
            isForeground = false;
            try {
                FloatWindow.get().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isForeground) {
            //????????????????????????
            isForeground = true;
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {

                } else {
                    if (FloatWindow.get() != null) {
                        FloatWindow.get().show();
                    }
                }
            }
        }
    }

    /**
     * ??????Acitity??????
     */
    private int activityAount = 0;

    /**
     * Activity ?????????????????????????????????app?????????????????????
     */
    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activityAount == 0) {
                //app????????????
                isForeground = true;
            }
            activityAount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityAount--;
            if (activityAount == 0) {
                isForeground = false;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

    //?????????????????????
    @TargetApi(Build.VERSION_CODES.M)
    private void getOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 0);
    }

    @OnClick({R.id.rb_main_home, R.id.rb_main_discount, R.id.rb_main_tea, R.id.iv_question, R.id.rb_main_service, R.id.rb_main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_main_home:
                vpMain.setCurrentItem(0);
                //?????????????????????????????????
                StatusBarUtils.setAndroidNativeLightStatusBar(mContext, true);
                break;
            case R.id.rb_main_discount:
                vpMain.setCurrentItem(1);
                //?????????????????????????????????
                StatusBarUtils.setAndroidNativeLightStatusBar(mContext, true);
                break;
            case R.id.rb_main_tea:
            case R.id.iv_question:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, PublicServiceActivity.class);
//                vpMain.setCurrentItem(2);
                break;
            case R.id.rb_main_service:
                teaFragment.refreshView();
                vpMain.setCurrentItem(3);
                //?????????????????????????????????
                StatusBarUtils.setAndroidNativeLightStatusBar(mContext, true);
                break;
            case R.id.rb_main_mine:
                vpMain.setCurrentItem(4);
                //?????????????????????????????????
                StatusBarUtils.setAndroidNativeLightStatusBar(mContext, false);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//???????????????????????????
        if ((mNowTime - mPressedTime) > 2000) {//???????????????????????????
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//????????????
            try {
                FloatWindow.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.finish();
            super.onBackPressed();
//            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //????????????????????? ??????????????????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.e(TAG, Arrays.toString(grantResults) + "*******" + grantResults.length);
        if (requestCode == 123) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        //??????
                        RxBus.getInstance().post("refreshLocation");
                    }
                }
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        //????????????
//                        checkVersionUpdate();
                    }
                }
            }
        }
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    /**
     * ????????????
     */
    private void checkVersionUpdate() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.updateVersion(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                VersionBean mBean = JSONUtils.parserObject(response, "version", VersionBean.class);
                if (mBean == null) {
                    return;
                }
                if (mBean.getVercode() == null) {
                    return;
                }
                if (AppUtils.getVersionCode(mContext) >= Double.parseDouble(mBean.getVercode())) {
                    return;
                }
                if ("1".equals(mBean.getForce())) {
                    //?????????????????? 0?????? 1??????
                    CheckVersionRunnable runnable = CheckVersionRunnable.from(mContext)
//                                    .setApkPath(SystemDir.DIR_UPDATE_APK)//??????????????????
                            .setDownLoadUrl(mBean.getAddress())
                            .setHandleQzgx(false)
                            .setServerUpLoadLocalVersion("" + (AppUtils.getVersionCode(mContext) + 1))
                            .setServerVersion("" + (AppUtils.getVersionCode(mContext) + 2))
                            .setUpdateMsg(mBean.getContent())
                            .isUseCostomDialog(true)
                            .setHandleQzgx(false)
                            .setNotifyTitle(getResources().getString(R.string.app_name))
                            .setVersionShow(mBean.getContent());
                    //????????????????????????
                    ThreadPoolUtils.newInstance().execute(runnable);
                } else {
                    CheckVersionRunnable runnable = CheckVersionRunnable.from(mContext)
//                                    .setApkPath(SystemDir.DIR_UPDATE_APK)//??????????????????
                            .setHandleQzgx(true)
                            .setDownLoadUrl(mBean.getAddress())
                            .setServerUpLoadLocalVersion("" + (AppUtils.getVersionCode(mContext) - 1))
                            .setServerVersion("" + (AppUtils.getVersionCode(mContext) + 2))
                            .setUpdateMsg(mBean.getContent())
                            .isUseCostomDialog(true)
                            .setHandleQzgx(false)
                            .setNotifyTitle(getResources().getString(R.string.app_name))
                            .setVersionShow(mBean.getContent());
                    //????????????????????????
                    ThreadPoolUtils.newInstance().execute(runnable);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, mContext.getResources().getString(R.string.service_error));
            }
        });
    }

    //??????ConnectionListener??????
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            Log.e("TAG", "onConnected");
        }

        @Override
        public void onDisconnected(final int error) {
            Log.e("TAG", "onDisconnected=" + error);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // ???????????????????????????

                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // ?????????????????????????????????
//                        ToastUtils.show(mContext,"???????????????????????????");
//                        LoginCheckUtils.exit();
//                        LoginCheckUtils.showTokenLogin(mContext);
//                        AppManager.getInstance().finishAllActivity();
//                        MyApplication.openActivity(mContext, LoginActivity.class);
//                        finish();
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //???????????????????????????

                        } else {
                            //?????????????????????????????????????????????

                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            FloatWindow.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * ????????????????????????
     */
    private void chectNotice() {
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();
        if (!isEnabled) {
            //???????????????
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("??????")
                    .setMessage("???????????????????????????????????????")
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", mContext.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", mContext.getPackageName());
                                intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                            }
                            startActivity(intent);

                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }
    }

    // ???????????? JPush Example ?????????????????? Activity ????????????????????? App ?????????????????????????????????????????????????????????????????????
    private void setAlias() {
        String alias = MyApplication.mPreferenceProvider.getUId();
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            return;
        }
        // ?????? Handler ?????????????????????
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    // ??????????????? SharePreference ??????????????????????????????????????????????????????????????????????????????????????????
                    LogUtils.e("TAG", "??????*****" + alias);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // ?????? 60 ???????????? Handler ????????????
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            LogUtils.e("TAG", "??????*****" + logs);
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // ?????? JPush ????????????????????????
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
            }
        }
    };
}
