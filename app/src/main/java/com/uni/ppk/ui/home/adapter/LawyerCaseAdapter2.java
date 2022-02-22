package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.home.bean.LawyerCaseBean2;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.ninegrid.NineGridTestLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/10
 * Time: 16:00
 */
public class LawyerCaseAdapter2 extends AFinalRecyclerViewAdapter<LawyerCaseBean2> {

    public LawyerCaseAdapter2(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new LawyerCaseViewHolder(mInflater.inflate(R.layout.item_lawyer_case2, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((LawyerCaseViewHolder) holder).setContent(getItem(position), position);
    }

    protected class LawyerCaseViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rlv_photo)
        CustomRecyclerView rlvPhoto;
        @BindView(R.id.ninegridview)
        NineGridTestLayout ninegridview;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public LawyerCaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(LawyerCaseBean2 mBean, int position) {
            tvTitle.setText("" + mBean.getTitle());
            tvContent.setText("" + mBean.getContent());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
            tvTime.setText("" + mBean.getCreate_time());
            tvName.setText("" + mBean.getUser_name());
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (photoAndVideoOnClicke != null) {
                        photoAndVideoOnClicke.deleteCase(position, mBean);
                    }
                }
            });

            List<String> photo = new ArrayList<>();
            if (mBean.getImgs() != null && mBean.getImgs().size() > 0) {
                for (int i = 0; i < mBean.getImgs().size(); i++) {
                    photo.add(mBean.getImgs().get(i));
                }
            }
            if (photo.size() > 0) {
                ninegridview.setUrlList(photo);
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
        void photoOnClicke(LawyerCaseBean2 dataBean, int position, List<View> imageViews);

        void headerOnClicke(LawyerCaseBean2 dataBean);

        void deleteCase(int position, LawyerCaseBean2 bean);
    }
}
