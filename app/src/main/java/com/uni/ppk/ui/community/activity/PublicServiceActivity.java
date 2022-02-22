package com.uni.ppk.ui.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.InputMoneyPopup;
import com.uni.ppk.pop.VideoWarnPopup;
import com.uni.ppk.ui.community.adapter.RewardAdapter;
import com.uni.ppk.ui.community.bean.RewardBean;
import com.uni.ppk.ui.home.activity.PayMoneyActivity;
import com.uni.ppk.ui.home.bean.UploadImageBean;
import com.uni.ppk.ui.mine.adapter.GridImageAdapter;
import com.uni.ppk.utils.ArithUtils;
import com.uni.ppk.utils.OrderAgreeUtils;
import com.uni.ppk.utils.PhotoSelectSingleUtile;
import com.uni.ppk.widget.CustomRecyclerView;
import com.uni.ppk.widget.FullyGridLayoutManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 15:35
 * 发布资讯
 */
public class PublicServiceActivity extends BaseActivity {
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
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_public)
    TextView tvPublic;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.rlv_photo)
    CustomRecyclerView rlvPhoto;
    @BindView(R.id.rlv_reward)
    CustomRecyclerView rlvReward;
    @BindView(R.id.edt_title)
    EditText edtTitle;

    private GridImageAdapter mPhotoAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private RewardAdapter mRewardAdapter;

    private String mPhoto = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_service;
    }

    @Override
    protected void initData() {
        initTitle("发布咨询");
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rlvPhoto.setLayoutManager(manager);
        mPhotoAdapter = new GridImageAdapter(mContext, onAddPicClickListener);
        mPhotoAdapter.setSelectMax(3);
        mPhotoAdapter.setList(mSelectList);
        rlvPhoto.setAdapter(mPhotoAdapter);

        rlvReward.setLayoutManager(new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRewardAdapter = new RewardAdapter(mContext);
        rlvReward.setAdapter(mRewardAdapter);
        mRewardAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<RewardBean>() {
            @Override
            public void onItemClick(View view, int position, RewardBean mBean) {
                if (mBean.isInput()) {
                    InputMoneyPopup moneyPopup = new InputMoneyPopup(mContext);
                    moneyPopup.setMoney(mBean.getReward());
                    moneyPopup.setOnMoneyCallback(new InputMoneyPopup.OnMoneyCallback() {
                        @Override
                        public void submit(String money) {
                            moneyPopup.dismiss();
                            mBean.setName(money);
                            mBean.setReward(money);
                            for (int i = 0; i < mRewardAdapter.getItemCount(); i++) {
                                mRewardAdapter.getList().get(i).setSelect(false);
                            }
                            if (mBean.isInput()) {
                                tvPrice.setText("¥" + ArithUtils.saveSecond(mBean.getName()));
                            } else {
                                tvPrice.setText("¥" + ArithUtils.saveSecond(mBean.getReward()));
                            }
                            mBean.setSelect(true);
                            mRewardAdapter.notifyDataSetChanged();
                        }
                    });
                    moneyPopup.showAtLocation(tvOpenVip, Gravity.CENTER, 0, 0);
                    return;
                }
                if (mBean.isSelect()) {
//                    mBean.setSelect(false);
                } else {
                    for (int i = 0; i < mRewardAdapter.getItemCount(); i++) {
                        mRewardAdapter.getList().get(i).setSelect(false);
                    }
                    if (mBean.isInput()) {
                        tvPrice.setText("¥" + ArithUtils.saveSecond(mBean.getName()));
                    } else {
                        tvPrice.setText("¥" + ArithUtils.saveSecond(mBean.getReward()));
                    }
                    mBean.setSelect(true);
                }
                mRewardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view, int position, RewardBean model) {

            }
        });

        //获取悬赏金额配置项
        getMoney();
    }

    private void getMoney() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.communityMoney(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<RewardBean> beans = JSONUtils.jsonString2Beans(response, RewardBean.class);
                if (beans != null && beans.size() > 0) {
                    RewardBean rewardBean = new RewardBean();
                    rewardBean.setInput(true);
                    rewardBean.setName("自定义金额");
                    beans.add(rewardBean);
                    beans.get(0).setSelect(true);
                    tvPrice.setText("¥" + ArithUtils.saveSecond(beans.get(0).getReward()));
                    mRewardAdapter.refreshList(beans);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
                finish();
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
                finish();
            }
        });
    }

    @OnClick({R.id.tv_open_vip, R.id.tv_public})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open_vip:
                OrderAgreeUtils.jumpVip(mContext);
                break;
            case R.id.tv_public:
                submit();
                break;
        }
    }

    private void submit() {
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();
        String money = "";
        if (StringUtils.isEmpty(title)) {
            ToastUtils.show(mContext, "请输入标题");
            return;
        }
        if (StringUtils.isEmpty(content)) {
            ToastUtils.show(mContext, "请输入问题描述");
            return;
        }
        for (int i = 0; i < mRewardAdapter.getItemCount(); i++) {
            if (mRewardAdapter.getItem(i).isSelect()) {
                money = "" + mRewardAdapter.getItem(i).getReward();
            }
        }
        if (StringUtils.isEmpty(money)) {
            ToastUtils.show(mContext, "请选择悬赏金额");
            return;
        }
        if (StringUtils.isEmpty(mPhoto) && mSelectList.size() > 0) {
            uploadImg();
            return;
        }
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("title", "" + title);
        orderInfo.put("content", "" + content);
        orderInfo.put("pictures", "" + mPhoto);
        orderInfo.put("reward", "" + money);
        orderInfo.put("body", "[]");
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "3");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(orderInfo));
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                String payStatus = JSONUtils.getNoteJson(response, "pay_status");
                RxBus.getInstance().post("refreshCommunity");
                if ("-1".equals(payStatus)) {
//                    ToastUtils.show(mContext, "发布成功");
//                    finish();
                    VideoWarnPopup videoWarnPopup = new VideoWarnPopup(mContext);
                    videoWarnPopup.setContent("图文咨询发布成功，请在我的——\n我的咨询进行查询");
                    videoWarnPopup.showAtLocation(tvOpenVip, Gravity.CENTER, 0, 0);
                    videoWarnPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            finish();
                        }
                    });
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
                bundle.putInt("jumpType", 6);
                MyApplication.openActivity(mContext, PayMoneyActivity.class, bundle);
                finish();
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, mContext.getResources().getString(R.string.service_error));
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PhotoSelectSingleUtile.selectPhoto(mContext, mSelectList, 3);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : mSelectList) {
                        Log.e("TAG", "图片集合---->" + JSONUtils.toJsonString(mSelectList));
                    }
                    mPhotoAdapter.setList(mSelectList);
                    mPhotoAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /**
     * 图片上传
     */
    public void uploadImg() {
        HttpUtils.uploadPhoto(mContext, mSelectList, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<UploadImageBean> imageBeans = JSONUtils.jsonString2Beans(response, UploadImageBean.class);
                if (imageBeans != null && imageBeans.size() > 0) {
                    for (int i = 0; i < imageBeans.size(); i++) {
                        if (i == 0) {
                            mPhoto = "" + imageBeans.get(i).getId();
                        } else {
                            mPhoto = mPhoto + "," + imageBeans.get(i).getId();
                        }
                    }
                    submit();
                } else {
                    ToastUtils.show(mContext, msg);
                }
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

}
