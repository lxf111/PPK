package com.uni.ppk.ui.home.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.DensityUtil;
import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.ScreenUtils;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.pop.VideoWarnPopup;
import com.uni.ppk.ui.community.adapter.EvaluateAdapter;
import com.uni.ppk.ui.community.bean.EvaluateBean;
import com.uni.ppk.ui.home.bean.VideoDetailBean;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.widget.CustomRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 14:55
 * ????????????
 */
public class VideoDetailActivity extends BaseActivity implements SuperPlayerView.OnSuperPlayerViewCallback, SuperPlayerView.onVideoListener {

    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.video_view2)
    SuperPlayerView videoView;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.rlyt_video2)
    RelativeLayout rlytVideo2;
    @BindView(R.id.tv_video_title)
    TextView tvTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.llyt_price)
    LinearLayout llytPrice;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.llyt_content)
    LinearLayout llytContent;
    @BindView(R.id.iv_video_start)
    ImageView ivVideoStart;
    @BindView(R.id.iv_back_new)
    ImageView ivBackNew;
    @BindView(R.id.rlv_list)
    CustomRecyclerView rlvList;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.rlyt_bottom)
    RelativeLayout rlytBottom;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private VideoWarnPopup mWarnPopup;//??????????????????

    private String mVideoUrl = "http://oss.jsqingque.com/files/ESwDZGR2snCt5QYPhMcWitpTPfNxPaD4.mp4";

    private String mId = "";
    private VideoDetailBean mDetailBean;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//rxbus????????????

    private EvaluateAdapter mAdapter;

    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initData() {
        mId = getIntent().getStringExtra("id");
        statusBar.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);

        ViewGroup.LayoutParams lp = rlytVideo2.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int) (MyApplication.getWidth() * 0.8);
        rlytVideo2.setLayoutParams(lp);

        getDetail();
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String type) {
                        if ("purchaseVideo".equals(type)) {
                            mWarnPopup = new VideoWarnPopup(mContext);
                            mWarnPopup.showAtLocation(tvContent, Gravity.CENTER, 0, 0);
                            getDetail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        videoView.setPlayerViewCallback(this);
        videoView.setOnVideoListener(this);
        initSuperVodGlobalSetting(false);

        ivVideoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvPay.getVisibility() == View.VISIBLE && "????????????".equals(tvPay.getText().toString().trim())) {
                    submit();
                    return;
                }
                playVideoModel(mVideoUrl, "");
            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvPay.getVisibility() == View.VISIBLE && "????????????".equals(tvPay.getText().toString().trim())) {
                    submit();
                    return;
                }
                playVideoModel(mVideoUrl, "");
            }
        });
        setBrightness(-1);

        //????????????
        rlvList.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new EvaluateAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setmJumpType(2);
        initRefreshLayout();

        edtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //???????????????
                    SoftInputUtils.hideSoftInput(mContext);
                    comment();
                    return true;
                }
                return false;
            }
        });
    }

    //???????????????????????????????????????
    public void setBrightness(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = f;
        getWindow().setAttributes(lp);
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            getComment();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getComment();
        });
    }

    private void getComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("page", "" + mPage);
        params.put("page_size", "" + Constants.PAGE_SIZE);
        HttpUtils.courseCommentList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<EvaluateBean> listBeans = JSONUtils.jsonString2Beans(response, EvaluateBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mAdapter.refreshList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        mAdapter.clear();
                    }
                } else {
                    if (listBeans != null && listBeans.size() > 0) {
                        refreshLayout.finishLoadMore();
                        mAdapter.appendToList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    //??????
    private void comment() {
        String content = edtContent.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(mContext, "?????????????????????");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", "" + mId);
        params.put("user_type", "" + Constants.USER_TYPE);
        params.put("pid", "" + 0);
        params.put("content", "" + content);
        HttpUtils.courseCommunityComment(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                edtContent.setText("");
                mPage = 1;
                RxBus.getInstance().post("refreshCommunity");
                getComment();
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

    /**
     * @param
     * @return
     * @description ????????????
     * @date: 2019/12/25 0025 16:10
     * @author: wangfeilong
     */
    private void playVideoModel(String videoURL, String title) {
        pbLoading.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(videoURL)) {
            SuperPlayerModel model = new SuperPlayerModel();
            model.url = videoURL;
            model.title = title;
            videoView.playWithModel(model);
        } else {
            toast("????????????????????????");
            pbLoading.setVisibility(View.GONE);
            videoView.setVisibility(View.GONE);
        }
    }

    private void submit() {
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("class_id", "" + mId);
        orderInfo.put("body", "[]");
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "4");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(orderInfo));
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                Bundle bundle = new Bundle();
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
                bundle.putInt("jumpType", 4);
                AppManager.getInstance().finishActivity(OrderPromptActivity.class);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
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

    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("class_id", "" + mId);
        HttpUtils.courseDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mDetailBean = JSONUtils.jsonString2Bean(response, VideoDetailBean.class);
                if (mDetailBean != null) {
                    mVideoUrl = mDetailBean.getAddress();
                    tvName.setText("???????????????" + mDetailBean.getTeacher_name());
                    tvNumber.setText("" + ArithUtils.showNumber(mDetailBean.getViewers()) + "?????????");
                    tvContent.setText("" + mDetailBean.getDescription());
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDetailBean.getCover()), ivVideo, mContext, R.mipmap.ic_default_wide);
                    tvTitle.setText("" + mDetailBean.getTitle());
                    try {
                        //1?????????2?????????
                        if (mDetailBean.getType() == 1) {
                            llytPrice.setVisibility(View.GONE);
                            tvPay.setVisibility(View.GONE);
                        } else {
                            if (Double.parseDouble(mDetailBean.getPrice()) == 0) {
                                llytPrice.setVisibility(View.GONE);
                                tvPay.setVisibility(View.GONE);
                            } else {
                                tvPay.setVisibility(View.VISIBLE);
                                llytPrice.setVisibility(View.VISIBLE);
                                tvPrice.setText("" + ArithUtils.saveSecond(mDetailBean.getPrice()));
                                if (mDetailBean.getPay_status() == 1) {
                                    //???????????????
                                    tvPay.setText("?????????????????????");
                                    tvPay.setEnabled(false);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        llytPrice.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
                finish();
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
                finish();
            }
        });
    }

    @OnClick({R.id.tv_pay, R.id.iv_back_new, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pay:
                submit();
                break;
            case R.id.iv_back_new:
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    videoView.setPorTrait();
                } else {
                    onBackPressed();
                }
                break;
            //??????
            case R.id.tv_submit:
                //???????????????
                SoftInputUtils.hideSoftInput(mContext);
                comment();
                break;
        }
    }

    protected boolean needStatusBarDarkText() {
        return false;
    }

    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPage = 1;
        getComment();
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
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        videoView.release();
        if (videoView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            videoView.resetPlayer();
        }
    }

    /**
     * ????????????????????????????????????
     */
    private void initSuperVodGlobalSetting(boolean isEnalbeFloatWindow) {
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // ?????????????????????
        prefs.enableFloatWindow = isEnalbeFloatWindow;
        // ???????????????????????????????????????
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = ScreenUtils.getScreenWidth(mContext);
        rect.height = DensityUtil.dip2px(mContext, 238);
        prefs.floatViewRect = rect;
        // ???????????????????????????
        prefs.maxCacheItem = 5;
        // ???????????????????????????
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        prefs.playShiftDomain = "playtimeshift.live.myqcloud.com";//????????????????????????????????????
        videoView.setPlayerViewCallback(this);
    }

    @Override
    public void onStartFullScreenPlay() {
        //??????
        llytContent.setVisibility(View.GONE);
        tvPay.setVisibility(View.GONE);
        ViewGroup.LayoutParams lp = rlytVideo2.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = MyApplication.getWidth();
        rlytVideo2.setLayoutParams(lp);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                , MyApplication.getWidth());
        ivVideo.setLayoutParams(layoutParams);
//        ivVideo.setLayoutParams(lp);
    }

    @Override
    public void onStopFullScreenPlay() {
        //??????
        llytContent.setVisibility(View.VISIBLE);
        if (mDetailBean.getType() == 1) {
            llytPrice.setVisibility(View.GONE);
            tvPay.setVisibility(View.GONE);
        } else {
            if (Double.parseDouble(mDetailBean.getPrice()) == 0) {
                llytPrice.setVisibility(View.GONE);
                tvPay.setVisibility(View.GONE);
            } else {
                tvPay.setVisibility(View.VISIBLE);
                llytPrice.setVisibility(View.VISIBLE);
                tvPrice.setText("" + ArithUtils.saveSecond(mDetailBean.getPrice()));
                if (mDetailBean.getPay_status() == 1) {
                    //???????????????
                    tvPay.setText("?????????????????????");
                    tvPay.setEnabled(false);
                }
            }
        }

        //????????????
        //???????????????????????????
        StatusBarUtils.setStatusBarColor(mContext, R.color.transparent);
        //?????????????????????????????????
        StatusBarUtils.setAndroidNativeLightStatusBar(mContext, needStatusBarDarkText());
        int mStatusBarHeight = StatusBarUtils.getStatusBarHeight(mContext);
        statusBar.getLayoutParams().height = mStatusBarHeight;

        ViewGroup.LayoutParams lp = rlytVideo2.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int) (MyApplication.getWidth() * 0.8);
        rlytVideo2.setLayoutParams(lp);
//        ivVideo.setLayoutParams(lp);
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
        pbLoading.setVisibility(View.GONE);
        ivVideo.setVisibility(View.GONE);
        ivVideoStart.setVisibility(View.GONE);
        //??????????????????????????????????????????????????????????????? 5???????????????????????????
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

}
