package com.uni.ppk.ui.home.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.ppk.MainActivity;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.LabelAdapter;
import com.uni.ppk.ui.home.bean.LabelBean;
import com.uni.ppk.ui.home.bean.LawyerDetailBean;
import com.uni.ppk.widget.CustomImageView85;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/18
 * Time: 14:06
 * 选择成功
 */
public class SelectSuccessActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.iv_img)
    CustomImageView85 ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rlv_label)
    CustomRecyclerView rlvLabel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_back)
    TextView tvBack;

    private LawyerDetailBean mDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_success;
    }

    @Override
    protected void initData() {
        mDetailBean = (LawyerDetailBean) getIntent().getSerializableExtra("bean");
        initTitle("");
        if (mDetailBean != null) {
            ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDetailBean.getHead_img()), ivImg, mContext, R.mipmap.ic_default_header);
            tvName.setText("" + mDetailBean.getUser_name());
            tvAddress.setText(mDetailBean.getCity() + " | " + mDetailBean.getLaw_firm());
            tvPhone.setText("联系电话：" + mDetailBean.getMobile());
            rlvLabel.setLayoutManager(new FlowLayoutManager() {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            LabelAdapter mLabelAdapter = new LabelAdapter(mContext);
            rlvLabel.setAdapter(mLabelAdapter);
            List<LabelBean> labelBeans = new ArrayList<>();
            for (int i = 0; i < mDetailBean.getService().size(); i++) {
                if (i < 2) {
                    LabelBean labelBean = new LabelBean();
                    labelBean.setName(mDetailBean.getService().get(i).getName());
                    labelBeans.add(labelBean);
                }
            }
            mLabelAdapter.refreshList(labelBeans);
        }
    }

    @OnClick({R.id.tv_phone, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                break;
            case R.id.tv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.openActivity(mContext, MainActivity.class);
    }
}
