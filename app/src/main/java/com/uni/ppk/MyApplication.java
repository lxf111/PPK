package com.uni.ppk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.WindowManager;

import com.uni.commoncore.utils.PreferenceProvider;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.pop.TipsPopup;
import com.uni.ppk.ui.message.bean.MessageUserBean;
import com.uni.ppk.utils.EaseMobHelper;
import com.uni.ppk.utils.ImageLoader;
import com.uni.ppk.utils.SpSaveListUtils;
import com.uni.ppk.utils.UMengHelper;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.kongzue.dialog.util.DialogSettings;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.previewlibrary.ZoomMediaLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.kongzue.dialog.util.DialogSettings.STYLE.STYLE_IOS;


public class MyApplication extends Application {

    public static PreferenceProvider mPreferenceProvider;// preference Provider

    public static int Width, Height;
    public static Context mContext;
    public static TipsPopup mTipsPopup;//提醒的弹窗

    @Override
    public void onCreate() {
        super.onCreate();
        //使用自己的字号，禁止系统修改字号
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        mContext = this;
        mPreferenceProvider = new PreferenceProvider(this);
        WindowManager();
        DialogSettings.isUseBlur = false;                 //设置是否启用模糊
//        DialogSettings.dialog_theme = THEME_LIGHT;
//        DialogSettings.tip_theme = THEME_LIGHT;
        DialogSettings.style = STYLE_IOS;
        DialogSettings.blurAlpha = 0;
        //友盟
        UMengHelper.init(mContext);

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "bdc7e6aaae", true, strategy);

//        EaseMobHelper.init(mContext);
        EaseMobHelper.initOnlyIM(mContext);
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                SpSaveListUtils mSpUtils = new SpSaveListUtils(mContext, "lawyer_user");
                List<MessageUserBean> beans = mSpUtils.getDataList("lawyer_user");
                EaseUser user = new EaseUser(username);
                if (beans != null && beans.size() > 0) {
                    for (int i = 0; i < beans.size(); i++) {
                        if (username.equals(beans.get(i).getId())) {
                            user.setAvatar(beans.get(i).getHeader());
                            user.setNickname(beans.get(i).getName());
                        }
                    }
                }
                if (username.equals(MyApplication.mPreferenceProvider.getUId())) {
                    user.setAvatar(NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getPhoto()));
                    user.setNickname(MyApplication.mPreferenceProvider.getUserName());
                }
                if (username.equals(MyApplication.mPreferenceProvider.getKFAccount())) {
                    user.setAvatar(NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getKFHeader()));
                    user.setNickname(MyApplication.mPreferenceProvider.getKFName());
                }
                return user;
            }
        });
        //图片放大
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        //抖音
        String clientkey = "awd5db9he1zf5ifx"; // 需要到开发者网站申请
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientkey));

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static void WindowManager() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Width = wm.getDefaultDisplay().getWidth();
        Height = wm.getDefaultDisplay().getHeight();
    }

    public static int getWidth() {
        return Width;
    }

    public static int getHeight() {
        return Height;
    }

    /**
     * 通过类名启动Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param targetClass
     * @param extras
     */
    public static void openActivity(Context context, Class<?> targetClass,
                                    Bundle extras) {
        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * Fragment中无效
     */
    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }

    public static Context getContext() {
        return mContext;
    }

    static {
        //设置全局的Header构建器

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader
            createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_F5F5F5, R.color.color_333333);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setPrimaryColorsId(R.color.color_F5F5F5, R.color.color_333333);//全局设置主题颜色
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static String returnUri(Activity activity, String uriContent) {
        String myImageUrl = uriContent;
        Uri uri = Uri.parse(myImageUrl);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        return img_path;
    }

    public static String selectPhotoShow(Activity activity, LocalMedia media) {
        String path = "";
        if (media.isCut() && !media.isCompressed()) {
            // 裁剪过
            path = media.getCutPath();
        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
            path = media.getCompressPath();
        } else {
            // 原图
            path = media.getPath();
        }
        path = PictureMimeType.isContent(path)
                && !media.isCut() && !media.isCompressed()
                ? MyApplication.returnUri(activity, path)
                : path;
        return path;
    }
}
