package com.uni.ppk.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.ScreenUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.NormalWebViewActivity;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.config.NormalWebViewConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by feng
 * on 2019/9/8 0008
 * 输入框的弹窗：中间弹出
 */
public class WornPopup extends PopupWindow {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.llyt_pop)
    LinearLayout llytPop;
    private View view;
    private Activity mContext;

    private OnWornCallback mOnWornCallback;

    public WornPopup(Activity activity, OnWornCallback mOnWornCallback) {
        super(activity);
        this.mContext = activity;
        this.mOnWornCallback = mOnWornCallback;
        init();
    }

    private void init() {
        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_worn, null);
        ButterKnife.bind(this, view);

        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnWornCallback != null) {
                    mOnWornCallback.cancel();
                }
            }
        });

        //确定
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnWornCallback != null) {
                    mOnWornCallback.submit();
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

    public void setTitle(String title) {
        title = tvTitle.getText().toString();
        SpannableString spannableString = new SpannableString(title);
        MyClickText myClickText = new MyClickText(mContext, 0);
        MyClickText myClickText2 = new MyClickText(mContext, 1);
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
//        spannableString.setSpan(foregroundColorSpan, title.indexOf("《用户协议》"),
//                title.indexOf("《用户协议》") + 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(foregroundColorSpan, title.indexOf("《隐私协议》"),
//                title.indexOf("《隐私协议》") + 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //传入一个MyClickText，并且继承ClickableSpan（必须这样写啊）
        spannableString.setSpan(myClickText, title.indexOf("《隐私政策》"), title.indexOf("《隐私政策》") + 6
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(myClickText2, title.indexOf("《用户协议》"), title.indexOf("《用户协议》") + 6
                , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(spannableString);
        tvTitle.setHighlightColor(mContext.getResources().getColor(R.color.transparent)); //设置点击后的颜色为透明
        tvTitle.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface OnWornCallback {
        void submit();

        void cancel();
    }

    private class MyClickText extends ClickableSpan {
        private Context context;
        private int type = 0;

        public MyClickText(Activity mainActivity, int type) {
            this.context = mainActivity;
            this.type = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor(context.getResources().getColor(R.color.theme));
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            if (type == 0) {
                //隐私协议
                getAgree("7");
            } else {
                //用户协议
                getAgree("1");
            }
        }
    }

    private void getAgree(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "" + type);
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                Bundle bundle = new Bundle();
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
                bundle.putString(NormalWebViewConfig.URL, "" + content);
                bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, true);
                MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
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

}
