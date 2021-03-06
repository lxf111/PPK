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
    public static TipsPopup mTipsPopup;//???????????????

    @Override
    public void onCreate() {
        super.onCreate();
        //????????????????????????????????????????????????
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        mContext = this;
        mPreferenceProvider = new PreferenceProvider(this);
        WindowManager();
        DialogSettings.isUseBlur = false;                 //????????????????????????
//        DialogSettings.dialog_theme = THEME_LIGHT;
//        DialogSettings.tip_theme = THEME_LIGHT;
        DialogSettings.style = STYLE_IOS;
        DialogSettings.blurAlpha = 0;
        //??????
        UMengHelper.init(mContext);

        Context context = getApplicationContext();
        // ??????????????????
        String packageName = context.getPackageName();
        // ?????????????????????
        String processName = getProcessName(android.os.Process.myPid());
        // ???????????????????????????
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // ?????????Bugly
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
        //????????????
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        //??????
        String clientkey = "awd5db9he1zf5ifx"; // ??????????????????????????????
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientkey));

    }

    /**
     * ?????????????????????????????????
     *
     * @param pid ?????????
     * @return ?????????
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
     * ??????????????????Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * ??????????????????Activity???????????????Bundle??????
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
     * Fragment?????????
     */
    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }

    public static Context getContext() {
        return mContext;
    }

    static {
        //???????????????Header?????????

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader
            createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_F5F5F5, R.color.color_333333);//????????????????????????
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("????????? %s"));//???????????????Header???????????? ???????????????Header
            }
        });
        //???????????????Footer?????????
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //???????????????Footer???????????? BallPulseFooter
                layout.setPrimaryColorsId(R.color.color_F5F5F5, R.color.color_333333);//????????????????????????
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
            // ?????????
            path = media.getCutPath();
        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
            // ?????????,???????????????????????????,??????????????????????????????
            path = media.getCompressPath();
        } else {
            // ??????
            path = media.getPath();
        }
        path = PictureMimeType.isContent(path)
                && !media.isCut() && !media.isCompressed()
                ? MyApplication.returnUri(activity, path)
                : path;
        return path;
    }
}
