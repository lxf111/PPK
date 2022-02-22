package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.LawyerCaseBean;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.ninegrid.NineGridTestLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 16:00
 */
public class LawyerCaseAdapter extends AFinalRecyclerViewAdapter<LawyerCaseBean> {

    public LawyerCaseAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new LawyerCaseViewHolder(mInflater.inflate(R.layout.item_lawyer_case, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((LawyerCaseViewHolder) holder).setContent(getItem(position), position);
    }

    protected class LawyerCaseViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rlv_photo)
        CustomRecyclerView rlvPhoto;
        @BindView(R.id.ninegridview)
        NineGridTestLayout ninegridview;

        public LawyerCaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(LawyerCaseBean mBean, int position) {
            tvTitle.setText("" + mBean.getTitle());
            tvContent.setText("" + mBean.getContent());
//            List<String> photo = new ArrayList<>();
//            if (!StringUtils.isEmpty(mBean.getThumb())) {
//                String sp[] = mBean.getThumb().split(",");
//                for (int i = 0; i < sp.length; i++) {
//                    photo.add(sp[i]);
//                }
//            }
            if (mBean.getImgs().size() > 0) {
                ninegridview.setUrlList(mBean.getImgs());
                ninegridview.setOnImageLoaderListener(new NineGridTestLayout.ImageLoaderListener() {
                    @Override
                    public void onLoadImgList(int i, List<String> urlList, List<View> imageViews) {
                        if (photoAndVideoOnClicke == null) {
                            return;
                        }
                        photoAndVideoOnClicke.photoOnClicke(mBean, i, imageViews);
                    }
                });
                ninegridview.setVisibility(View.VISIBLE);
            } else {
                ninegridview.setVisibility(View.GONE);
            }
        }
    }

    private PhotoAndVideoOnClicke photoAndVideoOnClicke;

    public void setPhotoAndVideoOnClicke(PhotoAndVideoOnClicke photoAndVideoOnClicke) {
        this.photoAndVideoOnClicke = photoAndVideoOnClicke;
    }

    public interface PhotoAndVideoOnClicke {
        void photoOnClicke(LawyerCaseBean dataBean, int position, List<View> imageViews);

        void headerOnClicke(LawyerCaseBean dataBean);
    }

}
