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

import com.uni.commoncore.utils.ScreenUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 输入框的弹窗：中间弹出
 */
public class TipsPopup extends PopupWindow {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.llyt_pop)
    LinearLayout llytPop;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private View view;
    private Activity mContext;

    private OnTipsCallback mOnTipsCallback;
    private boolean isCancel;//是否按钮取消

    public TipsPopup(Activity activity) {
        super(activity);
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_tips, null);
        ButterKnife.bind(this, view);

        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancel) {
                    dismiss();
                }
                if (mOnTipsCallback != null) {
                    mOnTipsCallback.cancel();
                }
            }
        });

        //确定
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancel) {
                    dismiss();
                }
                if (mOnTipsCallback != null) {
                    mOnTipsCallback.submit();
                }
            }
        });

        // 导入布局
        this.setContentView(view);
        this.setClippingEnabled(false);
        // 设置动画效果
        setAnimationStyle(R.style.AppTheme);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(ScreenUtils.getScreenHeight(mContext)
                + ScreenUtils.getStatusHeight(mContext)
                + ScreenUtils.getNavigationBarHeight(mContext));
        // 设置可触
        setFocusable(true);
        final ColorDrawable dw = new ColorDrawable(0x0000000);
        setBackgroundDrawable(dw);

        // 单击屏幕关闭弹窗
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.llyt_pop).getTop();
                int bottom = view.findViewById(R.id.llyt_pop).getBottom();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height || y > bottom) {
//                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setButton(String cancel, String submit) {
        tvCancel.setText(cancel);
        tvSubmit.setText(submit);
    }

    public void setTitle(String title, String content) {
        if (!StringUtils.isEmpty(title)) {
            tvTitle.setText("" + title);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        tvContent.setText("" + content);
    }

    public void setmOnTipsCallback(OnTipsCallback mOnTipsCallback, boolean isCancel) {
        this.mOnTipsCallback = mOnTipsCallback;
        this.isCancel = isCancel;
    }

    public interface OnTipsCallback {
        void submit();

        void cancel();
    }
}
