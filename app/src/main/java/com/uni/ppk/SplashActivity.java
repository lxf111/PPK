package com.uni.ppk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.ScreenUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.WornPopup;
import com.uni.ppk.ui.home.bean.HomeBannerBean;
import com.uni.ppk.widget.progressbar.CircleProgressbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {

    private ImageView mIvSplash;

    private CircleProgressbar mCircleProgressbar;

    private WornPopup mWornPopup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        mIvSplash = findViewById(R.id.iv_splash);
        mCircleProgressbar = findViewById(R.id.circle_progress_bar);

        initCircleProgressBar();

        //TODO 获取广告图片地址 和 时长
        String img_path = "";
        long time_length = 5000;
        if (!StringUtils.isEmpty(img_path)) {
            //显示广告图片
            Bitmap imageThumbnail = ImageUtils.getImageThumbnail(img_path,
                    ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext));
            if (imageThumbnail != null && mIvSplash != null) {
                mIvSplash.setImageBitmap(imageThumbnail);
            }
            //显示倒计时
            mCircleProgressbar.setVisibility(View.VISIBLE);
            mCircleProgressbar.setTimeMillis(time_length);
            mCircleProgressbar.reStart();
        } else {
            //没有广告直接跳走
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toMainPager();
                }
            }, 2000);
        }
    }

    //跳转首页
    private void toMainPager() {
        if (!"1".equals(MyApplication.mPreferenceProvider.getIsAgree())) {
            try {
                mWornPopup = new WornPopup(mContext, new WornPopup.OnWornCallback() {
                    @Override
                    public void submit() {
                        MyApplication.mPreferenceProvider.setIsAgree("1");
//                        if (LoginCheckUtils.checkUserIsLogin(mContext)) {
//                            Intent intent = new Intent(mContext, Splash2Activity.class);
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(mContext, Splash2Activity.class);
//                            startActivity(intent);
//                        }
//                        mWornPopup.dismiss();
                        getAdvert();
                    }

                    @Override
                    public void cancel() {
                        mWornPopup.dismiss();
                    }
                });
                mWornPopup.setTitle("");
                mWornPopup.showAtLocation(mIvSplash, Gravity.CENTER, 0, 0);
                mWornPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        finish();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
//                Intent intent = new Intent(mContext, Splash2Activity.class);
//                startActivity(intent);
                getAdvert();
            }
        } else {
//            if (LoginCheckUtils.checkUserIsLogin(mContext)) {
//                Intent intent = new Intent(mContext, Splash2Activity.class);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(mContext, Splash2Activity.class);
////                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
//            }
//            finish();
            getAdvert();
        }
    }

    private void getAdvert() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "" + 6);
        params.put("app_type", "" + 1);
        HttpUtils.homeBanner(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<HomeBannerBean> mBannerBeans = JSONUtils.jsonString2Beans(response, HomeBannerBean.class);
                if (mBannerBeans != null && mBannerBeans.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", mBannerBeans.get(0));
                    MyApplication.openActivity(mContext, Splash2Activity.class, bundle);
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onError(String msg, int code) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail(Call call, IOException e) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //初始化圆形进度条
    private void initCircleProgressBar() {
        mCircleProgressbar.setOutLineColor(Color.WHITE);
        mCircleProgressbar.setInCircleColor(Color.parseColor("#505559"));
        mCircleProgressbar.setProgressColor(Color.parseColor("#1BB079"));
        mCircleProgressbar.setProgressLineWidth(4);
        mCircleProgressbar.setProgressType(CircleProgressbar.ProgressType.COUNT);

        //倒计时结束
        mCircleProgressbar.setCountdownProgressListener(1, new CircleProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if (what == 1 && progress == 100) {
                    toMainPager();
                }
            }
        });

        //跳过
        mCircleProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgressbar.stop();
                toMainPager();
            }
        });
    }

    @Override
    protected boolean needStatusBarDarkText() {
        return true;
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }
}
