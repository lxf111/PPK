package com.uni.ppk.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 分享二维码：中间弹出
 */
public class ShareCodePopup extends PopupWindow {

    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;
    private View view;
    private Activity mContext;

    private OnShareCodeCallback mOnClickListener;
    private String mQrCode = "";

    public ShareCodePopup(Activity activity, OnShareCodeCallback mOnClickListener) {
        super(activity);
        this.mContext = activity;
        this.mOnClickListener = mOnClickListener;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_share_code, null);
        ButterKnife.bind(this, view);

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onCodeCallback(mQrCode);
                }
            }
        });
        tvCode.setText("推广码：" + MyApplication.mPreferenceProvider.getInvitationCode());

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
        getCode();
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

    /**
     * 获取code
     */
    private void getCode() {
        HttpUtils.recommendCode(mContext, new HashMap<>(), new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mQrCode = response;
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(response), ivCode, mContext, R.mipmap.ic_default_wide);
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

            }
        });
    }

    public interface OnShareCodeCallback {
        void onCodeCallback(String qrCode);
    }
}
