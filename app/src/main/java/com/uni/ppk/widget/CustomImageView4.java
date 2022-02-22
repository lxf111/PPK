package com.uni.ppk.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/24
 * Time: 18:00
 * <p>
 * 宽高比例1:1
 */
public class CustomImageView4 extends AppCompatImageView {
    public CustomImageView4(Context context) {
        super(context);
    }

    public CustomImageView4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Math.round(width * 0.4);
        setMeasuredDimension(width, height);
    }
}
