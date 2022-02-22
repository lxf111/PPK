package com.uni.ppk.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.uni.commoncore.utils.ScreenUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 金额输入框
 */
public class InputMoneyPopup extends PopupWindow {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.llyt_pop)
    LinearLayout llytPop;

    private View view;
    private Activity mContext;

    public InputMoneyPopup(Activity activity) {
        super(activity);
        this.mContext = activity;
        init();
    }

    public void setMoney(String money) {
        edtContent.setText("" + money);
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_input_money, null);
        ButterKnife.bind(this, view);

        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //确定
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = edtContent.getText().toString().trim();
                if (StringUtils.isEmpty(money)) {
                    ToastUtils.show(mContext, "请输入金额");
                    return;
                }
                mOnMoneyCallback.submit(money);
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

    private OnMoneyCallback mOnMoneyCallback;

    public void setOnMoneyCallback(OnMoneyCallback mOnMoneyCallback) {
        this.mOnMoneyCallback = mOnMoneyCallback;
    }

    public interface OnMoneyCallback {
        void submit(String money);

    }
}
