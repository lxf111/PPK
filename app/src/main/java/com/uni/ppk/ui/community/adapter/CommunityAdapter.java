package com.uni.ppk.ui.community.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.community.bean.CommunityBean;
import com.uni.ppk.ui.home.adapter.PhotoAdapter;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.ninegrid.NineGridTestLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 10:53
 */
public class CommunityAdapter extends AFinalRecyclerViewAdapter<CommunityBean> {

    public CommunityAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new CommunityViewHolder(mInflater.inflate(R.layout.item_community, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((CommunityViewHolder) holder).setContent(getItem(position), position);
    }

    private boolean isMine = false;//是否为我的发布

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    protected class CommunityViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.iv_header)
        CircleImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rlv_photo)
        CustomRecyclerView rlvPhoto;
        @BindView(R.id.tv_report)
        TextView tvReport;
        @BindView(R.id.iv_comment)
        ImageView ivComment;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.iv_praise)
        ImageView ivPraise;
        @BindView(R.id.tv_praise_num)
        TextView tvPraiseNum;
        @BindView(R.id.iv_reply_header)
        CircleImageView ivReplyHeader;
        @BindView(R.id.tv_reply_name)
        TextView tvReplyName;
        @BindView(R.id.tv_reply_label)
        TextView tvReplyLabel;
        @BindView(R.id.tv_reply_time)
        TextView tvReplyTime;
        @BindView(R.id.llyt_reply)
        LinearLayout llytReply;
        @BindView(R.id.tv_reply_content)
        TextView tvReplyContent;
        @BindView(R.id.rlyt_reply)
        RelativeLayout rlytReply;
        @BindView(R.id.ninegridview)
        NineGridTestLayout ninegridview;
        @BindView(R.id.llyt_button)
        LinearLayout llytButton;

        public CommunityViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(CommunityBean mBean, int position) {

            //1男 2女
            if (mBean.getSex() == 1) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_sex_man);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvName.setCompoundDrawables(null, null, drawable, null);
            } else if (mBean.getSex() == 2) {
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_sex_woman);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvName.setCompoundDrawables(null, null, drawable, null);
            } else {
                tvName.setCompoundDrawables(null, null, null, null);
            }

            if (mBean.getAnswer() != null && mBean.getIs_answer() == 1) {
                rlytReply.setVisibility(View.VISIBLE);
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getAnswer().getHead_img()), ivReplyHeader, mContext, R.mipmap.ic_default_header);
                tvReplyName.setText("" + mBean.getAnswer().getUser_nickname());
                tvReplyTime.setText("" + mBean.getAnswer().getUpdate_time());
                tvReplyContent.setText("" + mBean.getAnswer().getContent());
            } else {
                rlytReply.setVisibility(View.GONE);
            }

            if (isMine) {
                llytButton.setVisibility(View.GONE);
                rlytReply.setVisibility(View.GONE);
                tvLabel.setVisibility(View.GONE);
            } else {
                tvLabel.setVisibility(View.VISIBLE);
                tvLabel.setText("" + mBean.getShow_type_name());
                tvLabel.setBackgroundResource(R.drawable.shape_theme_radius25);
                if (mBean.getShow_type_id() == 1) {
                    //官方
                    tvLabel.setBackgroundResource(R.drawable.shape_25radius_ff7779);
                } else if (mBean.getShow_type_id() == 3) {

                } else if (mBean.getShow_type_id() == 4) {

                } else {
                    tvLabel.setVisibility(View.GONE);
                }
                //回复
//                if (mBean.getAnswer() != null && mBean.getIs_answer() == 1) {
//                    rlytReply.setVisibility(View.VISIBLE);
//                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getAnswer().getHead_img()), ivReplyHeader, mContext, R.mipmap.ic_default_header);
//                    tvReplyName.setText("" + mBean.getAnswer().getUser_nickname());
//                    tvReplyTime.setText("" + mBean.getAnswer().getCreate_time());
//                    tvReplyContent.setText("" + mBean.getAnswer().getContent());
//                } else {
//                    rlytReply.setVisibility(View.GONE);
//                }
            }
            tvTitle.setText("" + mBean.getTitle());
            tvContent.setText("" + mBean.getContent());
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
            tvName.setText("" + mBean.getUser_nickname());
            tvTime.setText("" + mBean.getCreate_time());
            //show_type_id 1官方发布 2普通发布 3：0悬赏 4悬赏

            tvCommentNum.setText("" + mBean.getComments_count());
            tvPraiseNum.setText("" + mBean.getGoods_count());

            if (mBean.getIs_goods() == 1) {
                //已点赞
                ivPraise.setImageResource(R.mipmap.ic_community_praised);
            } else {
                //未点赞
                ivPraise.setImageResource(R.mipmap.ic_community_praise);
            }

            rlvPhoto.setLayoutManager(new GridLayoutManager(mContext, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            PhotoAdapter mAdapter = new PhotoAdapter(mActivity);
            rlvPhoto.setAdapter(mAdapter);

            if (mBean.getPictures().size() > 0) {
                ninegridview.setUrlList(mBean.getPictures());
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

            tvReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (photoAndVideoOnClicke != null) {
                        photoAndVideoOnClicke.report(position, mBean);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position, mBean);
                    }
                }
            });

        }
    }

    private PhotoAndVideoOnClicke photoAndVideoOnClicke;

    public void setPhotoAndVideoOnClicke(PhotoAndVideoOnClicke photoAndVideoOnClicke) {
        this.photoAndVideoOnClicke = photoAndVideoOnClicke;
    }

    public interface PhotoAndVideoOnClicke {
        void photoOnClicke(CommunityBean dataBean, int position, List<View> imageViews);

        void headerOnClicke(CommunityBean dataBean);

        void report(int position, CommunityBean dataBean);
    }
}
