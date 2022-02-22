package com.uni.ppk.widget.ninegrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.uni.commoncore.utils.ImageUtils;

import java.util.List;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/12
 */
public class NineGridTestLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;
    Context mContext;

    public NineGridTestLayout(Context context) {
        super(context);
        mContext = context;
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
//        ImageLoader.getLoader().loadAd(mContext, url, imageView);
        imageViews.add(imageView);
        ImageUtils.getPic(url,imageView,mContext);
        /* 获取ImageView的实际宽度* */
        int w = imageView.getWidth();
        int h = imageView.getHeight();

        int newW;
        int newH;
        if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
            newW = parentWidth / 2;
            newH = newW * 5 / 3;
        } else if (h < w) {//h:w = 2:3
            newW = parentWidth * 2 / 3;
            newH = newW * 2 / 3;
        } else {//newH:h = newW :w
            newW = parentWidth / 2;
            newH = h * newW / w;
        }
        setOneImageLayoutParams(imageView, newW, newH);
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        imageViews.add(imageView);
        ImageUtils.getPic(url,imageView,mContext);
    }

    @Override
    protected void onClickImage(int i, String url, List<String> urlList, RatioImageView imageView) {
        if (imageLoaderListener != null)
            imageLoaderListener.onLoadImgList(i, urlList , imageViews);
    }

    ImageLoaderListener imageLoaderListener;

    public void setOnImageLoaderListener(ImageLoaderListener imageLoaderListener) {
        this.imageLoaderListener = imageLoaderListener;
    }

    public interface ImageLoaderListener {
        void onLoadImgList(int i, List<String> urlList, List<View> imageViews);
    }
}
