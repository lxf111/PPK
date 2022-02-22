package com.uni.ppk.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.uni.commoncore.utils.PlatformUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.StyledDialogUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.NetUrlUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 微信，朋友圈分享：底部弹出
 */
public class SharePopup extends PopupWindow {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_circle)
    TextView tvCircle;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;
    private View view;
    private Activity mContext;

    private String mImg = "";//需要分享的图片

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public SharePopup(Activity activity) {
        super(activity);
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_share, null);
        ButterKnife.bind(this, view);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PlatformUtils.isWeixinAvilible(mContext)) {
                    ToastUtils.show(mContext, "请安装微信");
                    return;
                }
                if (!StringUtils.isEmpty(mImg)) {
                    shareToPlatform(SHARE_MEDIA.WEIXIN);
                } else {
                    shareLinkPlatform(SHARE_MEDIA.WEIXIN);
                }
            }
        });
        tvCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PlatformUtils.isWeixinAvilible(mContext)) {
                    ToastUtils.show(mContext, "请安装微信");
                    return;
                }
                if (!StringUtils.isEmpty(mImg)) {
                    shareToPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    shareLinkPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
            }
        });

        // 导入布局
        this.setContentView(view);

        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);

        setWidth(WindowManager.LayoutParams.MATCH_PARENT);

        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置可触
        setFocusable(true);

        final ColorDrawable dw = new ColorDrawable(0x0000000);

        setBackgroundDrawable(dw);

        // 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.ll_pop).getTop();
                int bottom = view.findViewById(R.id.ll_pop).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowManager.LayoutParams layoutParams = mContext.getWindow().getAttributes();
        layoutParams.alpha = 0.7f;
        mContext.getWindow().setAttributes(layoutParams);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams layoutParams = mContext.getWindow().getAttributes();
        layoutParams.alpha = 1f;
        mContext.getWindow().setAttributes(layoutParams);
    }

    private void shareLinkPlatform(SHARE_MEDIA Platform) {
        UMImage umImage = new UMImage(mContext, R.mipmap.ic_logo);
        //注册来源分别为0、1、2，备注： 0：Android；1：iOS ；2：小程序；
        UMWeb web = new UMWeb("" + NetUrlUtils.QR_CODE_SHARE + MyApplication.mPreferenceProvider.getInvitationCode());
        web.setDescription("推荐给好友");
        web.setThumb(umImage);
        web.setTitle(mContext.getResources().getString(R.string.app_name));
        new ShareAction(mContext)
                .setPlatform(Platform)//传入平台
                .withMedia(web)
                .setCallback(new UMShareListener() {//回调监听器
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Toast.makeText(mContext, "分享取消", Toast.LENGTH_SHORT).show();
                    }
                }).share();
    }

    //分享到某平台
    private void shareToPlatform(SHARE_MEDIA Platform) {
        StyledDialogUtils.getInstance().loading(mContext);
        UMImage umImage = new UMImage(mContext, NetUrlUtils.createPhotoUrl(mImg));
        umImage.setTitle(mContext.getResources().getString(R.string.app_name));//标题
        umImage.setThumb(new UMImage(mContext, NetUrlUtils.createPhotoUrl(mImg)));  //缩略图
        umImage.setDescription("推荐好友");//描述
        //注册来源分别为0、1、2，备注： 0：Android；1：iOS ；2：小程序；
        new ShareAction(mContext)
                .setPlatform(Platform)//传入平台
                .withMedia(umImage)
                .setCallback(new UMShareListener() {//回调监听器
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
                        StyledDialogUtils.getInstance().dismissLoading();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
                        StyledDialogUtils.getInstance().dismissLoading();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Toast.makeText(mContext, "分享取消", Toast.LENGTH_SHORT).show();
                        StyledDialogUtils.getInstance().dismissLoading();
                    }
                }).share();
    }
}
