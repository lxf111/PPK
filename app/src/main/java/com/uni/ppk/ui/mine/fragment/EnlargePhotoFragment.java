package com.uni.ppk.ui.mine.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uni.commoncore.utils.StyledDialogUtils;
import com.uni.commoncore.utils.ThreadPoolUtils;
import com.uni.ppk.R;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.config.Constants;
import com.uni.ppk.utils.DownloadPicThread;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kongzue.dialog.v3.TipDialog;
import com.luck.picture.lib.PicturePreviewActivity;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoViewAttacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/7/4
 * Time: 8:30
 * 图片放大
 */
public class EnlargePhotoFragment extends LazyBaseFragments {

    public static final String PATH = "path";
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private List<String> mPhotos = new ArrayList<>();

    public static EnlargePhotoFragment getInstance(String path, List<String> mPhotos) {
        EnlargePhotoFragment fragment = new EnlargePhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PATH, path);
        bundle.putSerializable(Constants.EXTRA_ENLARGE_PHOTO, (Serializable) mPhotos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_enlager_photo, null);
        return mRootView;
    }

    @Override
    public void initView() {
        mPhotos = getArguments().getStringArrayList(Constants.EXTRA_ENLARGE_PHOTO);
        String path = getArguments().getString(PATH);

        final PhotoViewAttacher mAttacher = new PhotoViewAttacher(ivImage);
        Glide.with(getActivity())
                .asBitmap()
                .load(NetUrlUtils.createPhotoUrl(path))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(new SimpleTarget<Bitmap>(480, 800) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivImage.setImageBitmap(resource);
                        mAttacher.update();
                    }
                });
        mAttacher.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (getActivity() instanceof PicturePreviewActivity) {
                    getActivity().finish();
//                    getActivity().overridePendingTransition(0, R.anim.toast_out);
                } else {
                    getActivity().finish();
//                    getActivity().overridePendingTransition(0, R.anim.toast_out);
                }
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StyledDialogUtils.getInstance().loading(mContext);
                ThreadPoolUtils.newInstance().execute(new DownloadPicThread(mContext, NetUrlUtils.createPhotoUrl(path),
                        new DownloadPicThread.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {
                                mContext.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StyledDialogUtils.getInstance().dismissLoading();
                                        TipDialog.show((AppCompatActivity) mContext, "保存成功", TipDialog.TYPE.SUCCESS);
                                    }
                                });
                            }

                            @Override
                            public void onDownloadFailed() {
                                mContext.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StyledDialogUtils.getInstance().dismissLoading();
                                        TipDialog.show((AppCompatActivity) mContext, "保存失败", TipDialog.TYPE.ERROR);
                                    }
                                });
                            }
                        }));
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void loadData() {

    }
}
