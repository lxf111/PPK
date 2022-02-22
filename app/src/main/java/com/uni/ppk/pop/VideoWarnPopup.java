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

import com.uni.ppk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 微信，朋友圈分享：底部弹出
 */
public class VideoWarnPopup extends PopupWindow {


    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_pop)
    LinearLayout llPop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private View view;
    private Activity mContext;

    public VideoWarnPopup(Activity activity) {
        super(activity);
        this.mContext = activity;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_video_warn, null);
        ButterKnife.bind(this, view);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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

    public void setContent(String content) {
        tvContent.setText("" + content);
    }

    public void setTitle(String title) {
        tvTitle.setText("" + title);
    }

}
