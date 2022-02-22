package com.uni.ppk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class CustomListView extends ListView {
    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
