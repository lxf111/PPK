package com.uni.ppk.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.HomeLawyerAdapter;
import com.uni.ppk.ui.home.bean.BannerDetailBean;
import com.uni.ppk.ui.home.bean.HomeLawyerBean;
import com.uni.ppk.ui.home.bean.LawFirmDetailBean;
import com.uni.ppk.widget.CustomRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 9:22
 * 律所详情
 */
public class LawFirmActivity extends BaseActivity {
    @BindView(R.id.banner_home)
    Banner bannerHome;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rlv_list)
    CustomRecyclerView rlvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.iv_video_img)
    ImageView ivVideoImg;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;

    private int mPage = 1;
    private HomeLawyerAdapter mAdapter;//律师适配器

    private String mId = "";//律师id
    private String mVideoUrl = "";

    private List<BannerDetailBean> mBannerDetailBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_law_firm;
    }

    @Override
    protected void initData() {
        ViewGroup.LayoutParams lp = bannerHome.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int) (MyApplication.getWidth() * 0.56);
        bannerHome.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2 = ivVideoImg.getLayoutParams();
        lp2.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp2.height = (int) (MyApplication.getWidth() * 0.56);
        ivVideoImg.setLayoutParams(lp2);

        mId = getIntent().getStringExtra("id");

        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
        rlvList.setLayoutManager(new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new HomeLawyerAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<HomeLawyerBean>() {
            @Override
            public void onItemClick(View view, int position, HomeLawyerBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, LawyerDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, HomeLawyerBean model) {

            }
        });
        //初始化轮播图
        initBanner();

        initRefreshLayout();
        getData();

    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("lawfirm_id", "" + mId);
        HttpUtils.homeLawFirmDetail(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LawFirmDetailBean bean = JSONUtils.jsonString2Bean(response, LawFirmDetailBean.class);
                refreshLayout.finishRefresh();
                if (bean != null) {
                    tvTitle.setText("" + bean.getName());
                    tvContent.setText("" + bean.getDescription());
                    mBannerDetailBeans.clear();
                    if (!StringUtils.isEmpty(bean.getVideo())) {
//                        BannerDetailBean detailBean = new BannerDetailBean();
//                        detailBean.setUrl(bean.getVideo());
//                        detailBean.setVideo(true);
//                        mBannerDetailBeans.add(detailBean);
                        mVideoUrl = bean.getVideo();
                        Message message = new Message();
                        message.what = 0;
                        message.obj = bean.getVideo();
                        handler.sendMessage(message);
                    }
                    if (bean.getImages() != null && bean.getImages().size() > 0) {
                        for (int i = 0; i < bean.getImages().size(); i++) {
                            BannerDetailBean detailBean = new BannerDetailBean();
                            detailBean.setUrl(bean.getImages().get(i));
                            mBannerDetailBeans.add(detailBean);
                        }
                    }
                    if (mBannerDetailBeans != null && mBannerDetailBeans.size() > 0) {
                        bannerHome.setImages(mBannerDetailBeans);
                        bannerHome.start();
                    }
                    if (bean.getLawyers() != null && bean.getLawyers().size() > 0) {
                        mAdapter.refreshList(bean.getLawyers());
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    private void initRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            getData();
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData();
        });
    }

    @OnClick({R.id.iv_back, R.id.rl_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_video:
                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra("videoUrl", "" + NetUrlUtils.createPhotoUrl(mVideoUrl));
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean needStatusBarDarkText() {
        return false;
    }

    //初始化轮播图
    private void initBanner() {
        bannerHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object obj, ImageView imageView) {
                BannerDetailBean detailBean = (BannerDetailBean) obj;
                if (detailBean.isVideo()) {
                    imageView.setImageBitmap(createVideoThumbnail(NetUrlUtils.createPhotoUrl(detailBean.getUrl())
                            , MediaStore.Images.Thumbnails.MINI_KIND));
                } else {
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(detailBean.getUrl())
                            , imageView, mContext, R.mipmap.ic_default_wide);
                }
            }
        });
        bannerHome.setBannerStyle(BannerConfig.CENTER);
        bannerHome.setDelayTime(3000);
        bannerHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (mBannerDetailBeans.get(position).isVideo()) {
//                    ivVideo.setVisibility(View.VISIBLE);
//                } else {
//                    ivVideo.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //banner设置方法全部调用完毕时最后调用
        bannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mBannerDetailBeans.get(position).isVideo()) {
                    Intent intent = new Intent(mContext, VideoPlayActivity.class);
                    intent.putExtra("videoUrl", "" + mBannerDetailBeans.get(position).getUrl());
                    startActivity(intent);
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ivVideoImg.setImageBitmap(createVideoThumbnail(NetUrlUtils.createPhotoUrl((String) msg.obj)
                            , MediaStore.Images.Thumbnails.MINI_KIND));
//                        ImageUtils.getPic(NetUrlUtils.createPhotoUrl(bean.getVideo()), ivVideoImg, mContext, R.mipmap.ic_default_wide);
                    rlVideo.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {
            return null;
        }

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }
}
