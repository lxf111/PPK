package com.uni.ppk.ui.mine.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.mine.fragment.EnlargePhotoFragment;
import com.luck.picture.lib.widget.PreviewViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/7/4
 * Time: 8:27
 * 放大图片
 */
public class EnlargePhotoActivity extends BaseActivity {

    @BindView(R.id.preview_pager)
    PreviewViewPager previewPager;

    private int mIndex = 0;//点击的第几张图片下标
    private List<String> mPhotos = new ArrayList<>();

    private SimpleFragmentAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enlarge_photo;
    }

    @Override
    protected void initData() {

        mIndex = getIntent().getIntExtra(Constants.EXTRA_ENLARGE_INDEX, 0);
        mPhotos = getIntent().getStringArrayListExtra(Constants.EXTRA_ENLARGE_PHOTO);
        initTitle((mIndex + 1) + "/" + mPhotos.size());

        mAdapter = new SimpleFragmentAdapter(getSupportFragmentManager());
        previewPager.setAdapter(mAdapter);
        previewPager.setCurrentItem(mIndex);
        previewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTitle((position + 1) + "/" + mPhotos.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class SimpleFragmentAdapter extends FragmentPagerAdapter {

        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            EnlargePhotoFragment fragment = EnlargePhotoFragment.getInstance(mPhotos.get(position), mPhotos);
            return fragment;
        }

        @Override
        public int getCount() {
            return mPhotos.size();
        }
    }
}
