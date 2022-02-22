package com.uni.ppk.ui.home.activity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.uni.commoncore.utils.DensityUtil;
import com.uni.commoncore.utils.ScreenUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/13
 * Time: 13:51
 */
public class VideoPlayActivity extends BaseActivity
        implements SuperPlayerView.OnSuperPlayerViewCallback, SuperPlayerView.onVideoListener
//        implements ITXVodPlayListener
{
    //    @BindView(R.id.video_view)
//    TCVideoView mTXCloudVideoView;
    @BindView(R.id.seekbar)
    SeekBar mSeekBar;
    @BindView(R.id.iv_large)
    ImageView ivLarge;
    @BindView(R.id.progress_time)
    TextView mProgressTime;
    @BindView(R.id.iv_conver)
    ImageView ivConver;
    @BindView(R.id.iv_start)
    ImageView ivStart;
    @BindView(R.id.rlyt_video)
    RelativeLayout rlytVideo;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_left_back)
    ImageView ivBack;
    @BindView(R.id.video_view2)
    SuperPlayerView videoView;
//    @BindView(R.id.seekbar)
//    SeekBar seekbar;

    private String mVideoUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initData() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mVideoUrl = getIntent().getStringExtra("videoUrl");
        videoView.setPlayerViewCallback(this);
        videoView.setOnVideoListener(this);
        initSuperVodGlobalSetting(false);

        setBrightness(-1);
        playVideoModel(mVideoUrl, "");
    }

    //解决屏幕亮度自动改变的问题
    public void setBrightness(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = f;
        getWindow().setAttributes(lp);
    }

    /**
     * @param
     * @return
     * @description 视频播放
     * @date: 2019/12/25 0025 16:10
     * @author: wangfeilong
     */
    private void playVideoModel(String videoURL, String title) {
//        pbLoading.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(videoURL)) {
            SuperPlayerModel model = new SuperPlayerModel();
            model.url = videoURL;
            model.title = title;
            videoView.playWithModel(model);
        } else {
            toast("获取视频地址出错");
//            pbLoading.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            videoView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            videoView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoView.release();
        if (videoView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            videoView.resetPlayer();
        }
    }

    /**
     * 初始化超级播放器全局配置
     */
    private void initSuperVodGlobalSetting(boolean isEnalbeFloatWindow) {
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = isEnalbeFloatWindow;
        // 设置悬浮窗的初始位置和宽高
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = ScreenUtils.getScreenWidth(mContext);
        rect.height = DensityUtil.dip2px(mContext, 238);
        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        prefs.playShiftDomain = "playtimeshift.live.myqcloud.com";//需要修改为自己的时移域名
        videoView.setPlayerViewCallback(this);
    }

    @Override
    public void onStartFullScreenPlay() {

    }

    @Override
    public void onStopFullScreenPlay() {

    }

    @Override
    public void onClickFloatCloseBtn() {

    }

    @Override
    public void onClickSmallReturnBtn() {

    }

    @Override
    public void onStartFloatWindowPlay() {

    }

    @Override
    public void onVideoBack() {

    }

    @Override
    public void onVideoShrink() {

    }

    @Override
    public void onVideoPlayStart(float duration) {
//        pbLoading.setVisibility(View.GONE);
//        ivVideo.setVisibility(View.GONE);
//        ivVideoStart.setVisibility(View.GONE);
        //如果是精讲视频，开启定时器，当定时器执行到 5分钟时，就执行接口
        long period = 0;
        if (duration > 3 * 60) {
            period = 180;
        } else {
            period = (long) duration;
        }
    }

    @Override
    public void onVidoePlayEnd() {

    }

    protected boolean needStatusBarDarkText() {
        return false;
    }

    protected int getStatusBarColor() {
        return R.color.transparent;
    }

}
