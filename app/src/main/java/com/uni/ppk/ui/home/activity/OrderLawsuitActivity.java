package com.uni.ppk.ui.home.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.Constants;
import com.uni.ppk.pop.AddressSelectPopup;
import com.uni.ppk.pop.CommonSelectPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.ui.home.bean.LawsuitJumpBean;
import com.uni.ppk.ui.home.bean.OrderBodyBean;
import com.uni.ppk.ui.home.bean.OrderPriceBean;
import com.uni.ppk.ui.home.bean.UploadImageBean;
import com.uni.ppk.ui.mine.adapter.GridImageAdapter;
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
 * Date: 2020/9/10
 * Time: 18:01
 * 案件诉讼
 */
public class OrderLawsuitActivity extends BaseActivity {
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
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @BindView(R.id.tv_title_address)
    TextView tvTitleAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_explain_title)
    TextView tvExplainTitle;
    @BindView(R.id.edt_explain)
    EditText edtExplain;
    @BindView(R.id.rlv_photo)
    CustomRecyclerView rlvPhoto;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.iv_agree)
    ImageView ivAgree;
    @BindView(R.id.tv_agree_rule)
    TextView tvAgreeRule;
    @BindView(R.id.tv_normal_price)
    TextView tvNormalPrice;
    @BindView(R.id.tv_vip_price)
    TextView tvVipPrice;
    @BindView(R.id.tv_ensure)
    TextView tvEnsure;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.edt_charge)
    EditText edtCharge;
    @BindView(R.id.tv_title_meeting)
    TextView tvTitleMeeting;
    @BindView(R.id.tv_meeting)
    TextView tvMeeting;
    @BindView(R.id.tv_title_stage)
    TextView tvTitleStage;
    @BindView(R.id.tv_stage)
    TextView tvStage;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.view_meeting)
    View viewMeeting;
    @BindView(R.id.view_stage)
    View viewStage;

    private String mTitle = "";

    private GridImageAdapter mPhotoAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();
    private boolean isAgree = false;

    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";
    private String mClassifyTitle = "";
    private String mId = "";//一级分类id
    private String mClassifyId = "";//二级分类id
    private AddressSelectPopup mAddressPopup;//地址选择

    private CommonSelectPopup mSelectPopup;//单选弹窗
    private List<CommonSelectBean> mMeetingList = new ArrayList<>();//是否会见
    private List<CommonSelectBean> mStageList = new ArrayList<>();//案件所属阶段

    //协议 标问号的标题协议内容
    private String mAgreeMoney = "";
    private String mAgreeAddress = "";
    private String mAgreeExplain = "";
    private String mAgreeChange = "";
    private String mAgreeMeeting = "";
    private String mAgreeStage = "";

    private String mDepositMoney = "";//定金档位默认第一个

    private String mPhoto = "";
    private String mCoverImg = "";//封面

    private String mLocalPrice = "";//最高价
    private String mSubsidyPrice = "";//补贴价

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_lawsuit;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra("title");
        mClassifyTitle = getIntent().getStringExtra("classifyTitle");
        mId = getIntent().getStringExtra("id");
        mClassifyId = getIntent().getStringExtra("classifyId");
        initTitle("" + mClassifyTitle);
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
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_order_custom);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(drawable, null, null, null);

        if ("2".equals(mId)) {
            //民事
            tvMeeting.setVisibility(View.GONE);
            tvTitleMeeting.setVisibility(View.GONE);
            tvStage.setVisibility(View.GONE);
            tvTitleStage.setVisibility(View.GONE);
            viewMeeting.setVisibility(View.GONE);
            viewStage.setVisibility(View.GONE);
            tvCharge.setText("案件标的金额");
            edtCharge.setHint("请填写案件标的金额（必须填写真实金额）");
            edtCharge.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            //刑事

        }
        getOrderPrice();
        mSelectPopup = new CommonSelectPopup(mContext);
        OrderAgreeUtils.showVipTxt(tvOpenVip);
        edtExplain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void getOrderPrice() {
        Map<String, Object> params = new HashMap<>();
        params.put("son_id", "" + mClassifyId);
        params.put("typeid", "" + mId);
        HttpUtils.orderPrice(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                OrderPriceBean bean = JSONUtils.jsonString2Bean(response, OrderPriceBean.class);
                if (bean != null) {
                    if (bean.getPrices() != null && bean.getPrices().size() > 0) {
                        for (int i = 0; i < bean.getPrices().size(); i++) {
                            //获取会员价跟平台价
                            if (Constants.ORDER_DEFAULT_PRICE.equals(bean.getPrices().get(i).getType())) {
                                tvNormalPrice.setText("¥" + bean.getPrices().get(i).getPrice());
                                tvVipPrice.setText("¥" + bean.getPrices().get(i).getMember_price());
                            }
                        }
                    }
                    String ensure = "";//服务保障
                    if (bean.getGuarantee() != null && bean.getGuarantee().size() > 0) {
                        for (int i = 0; i < bean.getGuarantee().size(); i++) {
                            if (StringUtils.isEmpty(ensure)) {
                                ensure = bean.getGuarantee().get(i).getName();
                            } else {
                                ensure = ensure + "\n" + bean.getGuarantee().get(i).getName();
                            }
                        }
                    }
                    tvEnsure.setText("" + ensure);
                    //弹窗配置项
                    if (bean.getFields() != null && bean.getFields().size() > 0) {
                        for (int i = 0; i < bean.getFields().size(); i++) {
                            if (Constants.ORDER_HUIJIAN.equals(bean.getFields().get(i).getField())) {
                                //是否会见
                                if (bean.getFields().get(i).getOption() != null &&
                                        bean.getFields().get(i).getOption().size() > 0) {
                                    for (int j = 0; j < bean.getFields().get(i).getOption().size(); j++) {
                                        CommonSelectBean selectBean = new CommonSelectBean();
                                        selectBean.setName(bean.getFields().get(i).getOption().get(j));
                                        mMeetingList.add(selectBean);
                                    }
                                }
                                mAgreeMeeting = "" + bean.getFields().get(i).getTips();
                            } else if (Constants.ORDER_JIEDUAN.equals(bean.getFields().get(i).getField())) {
                                //案件所属阶段
                                if (bean.getFields().get(i).getOption() != null &&
                                        bean.getFields().get(i).getOption().size() > 0) {
                                    for (int j = 0; j < bean.getFields().get(i).getOption().size(); j++) {
                                        CommonSelectBean selectBean = new CommonSelectBean();
                                        selectBean.setName(bean.getFields().get(i).getOption().get(j));
                                        mStageList.add(selectBean);
                                    }
                                }
                                mAgreeStage = "" + bean.getFields().get(i).getTips();
                            } else if (Constants.ORDER_DANGWEI.equals(bean.getFields().get(i).getField())) {
                                //定金档位
                                if (bean.getFields().get(i).getOption() != null &&
                                        bean.getFields().get(i).getOption().size() > 0) {
                                    mDepositMoney = "" + bean.getFields().get(i).getOption().get(0);
                                }
                            } else if (Constants.ORDER_BIAODEJINE.equals(bean.getFields().get(i).getField())) {
                                //标的金额
                                mAgreeMoney = "" + bean.getFields().get(i).getTips();
                            } else if (Constants.ORDER_BENDIJIAGE.equals(bean.getFields().get(i).getField())) {
                                //最高价格
                                mLocalPrice = "" + bean.getFields().get(i).getTips();
                            } else if (Constants.ORDER_BUTIE.equals(bean.getFields().get(i).getField())) {
                                //补贴价格
                                mSubsidyPrice = "" + bean.getFields().get(i).getTips();
                            }
                        }
                    }
                    if (bean.getTips() != null) {
                        mAgreeAddress = "" + bean.getTips().getProvince();
                        mAgreeExplain = "" + bean.getTips().getContent();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

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
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
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
                            mCoverImg = "" + imageBeans.get(i).getId();
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

    @OnClick({R.id.tv_open_vip, R.id.right_title, R.id.tv_pay, R.id.tv_explain_title, R.id.tv_charge, R.id.tv_title_address, R.id.tv_address, R.id.tv_title_meeting, R.id.tv_meeting, R.id.tv_title_stage, R.id.tv_stage, R.id.iv_agree, R.id.tv_agree_rule})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //客服
            case R.id.right_title:
                OrderAgreeUtils.jumpChat(mContext);
                break;
            //开通vip
            case R.id.tv_open_vip:
                OrderAgreeUtils.jumpVip(mContext);
                break;
            //支付
            case R.id.tv_pay:
                if (!isAgree) {
                    ToastUtils.show(mContext, "请同意相关条款");
                    return;
                }
                submit();
                break;
            //案件罪名
            case R.id.tv_charge:
                if ("2".equals(mId)) {
                    OrderAgreeUtils.jumpAgree(mContext, tvCharge.getText().toString().trim(), mAgreeMoney, true);
                } else {
                    OrderAgreeUtils.jumpAgree(mContext, tvCharge.getText().toString().trim(), mAgreeChange, true);
                }
                break;
            //案件位置
            case R.id.tv_title_address:
                OrderAgreeUtils.jumpAgree(mContext, tvTitleAddress.getText().toString().trim(), mAgreeAddress, true);
                break;
            //位置
            case R.id.tv_address:
                mAddressPopup = new AddressSelectPopup(mContext);
                mAddressPopup.setmAddressCallback(new AddressSelectPopup.OnAddressCallback() {
                    @Override
                    public void callback(String province, String city, String area) {
                        tvAddress.setText("" + province + city + area);
                        mProvince = province;
                        mCity = city;
                        mArea = area;
                    }
                });
                mAddressPopup.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
                break;
            //是否会见
            case R.id.tv_title_meeting:
                OrderAgreeUtils.jumpAgree(mContext, tvTitleMeeting.getText().toString().trim(), mAgreeMeeting, true);
                break;
            //是否会见
            case R.id.tv_meeting:
                mSelectPopup.setmSelectBeans(mMeetingList);
                mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                    @Override
                    public void onItemClick(View view, int position, CommonSelectBean model) {
                        tvMeeting.setText("" + model.getName());
                        mSelectPopup.dismiss();
                    }

                    @Override
                    public void onItemLongClick(View view, int position, CommonSelectBean model) {

                    }
                });
                mSelectPopup.showAtLocation(tvMeeting, Gravity.BOTTOM, 0, 0);
                break;
            //案件阶段
            case R.id.tv_title_stage:
                OrderAgreeUtils.jumpAgree(mContext, tvTitleStage.getText().toString().trim(), mAgreeStage, true);
                break;
            //描述
            case R.id.tv_explain_title:
                OrderAgreeUtils.jumpAgree(mContext, tvExplainTitle.getText().toString().trim(), mAgreeExplain, true);
                break;
            //案件阶段
            case R.id.tv_stage:
                mSelectPopup.setmSelectBeans(mStageList);
                mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                    @Override
                    public void onItemClick(View view, int position, CommonSelectBean model) {
                        tvStage.setText("" + model.getName());
                        mSelectPopup.dismiss();
                    }

                    @Override
                    public void onItemLongClick(View view, int position, CommonSelectBean model) {

                    }
                });
                mSelectPopup.showAtLocation(tvMeeting, Gravity.BOTTOM, 0, 0);
                break;
            //是否同意
            case R.id.iv_agree:
                if (isAgree) {
                    isAgree = false;
                    ivAgree.setImageResource(R.mipmap.ic_order_agree);
                } else {
                    isAgree = true;
                    ivAgree.setImageResource(R.mipmap.ic_agree_select);
                }
                break;
            //规则
            case R.id.tv_agree_rule:
                OrderAgreeUtils.getRule(mContext);
                break;
        }
    }

    //提交
    private void submit() {
        String explain = edtExplain.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String meeting = tvMeeting.getText().toString().trim();
        String stage = tvStage.getText().toString().trim();
        String change = edtCharge.getText().toString().trim();
        if (StringUtils.isEmpty(mProvince)) {
            ToastUtils.show(mContext, "请选择地点");
            return;
        }
        if ("46".equals(mId)) {
            //刑事
            if (StringUtils.isEmpty(change)) {
                ToastUtils.show(mContext, "请输入涉嫌罪名");
                return;
            }
            if (StringUtils.isEmpty(meeting)) {
                ToastUtils.show(mContext, "请选择是否已经会见");
                return;
            }
            if (StringUtils.isEmpty(stage)) {
                ToastUtils.show(mContext, "请选择案件所处阶段");
                return;
            }
        } else {
            if (StringUtils.isEmpty(change)) {
                ToastUtils.show(mContext, "请输入案件所标金额");
                return;
            }
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
//        if (StringUtils.isEmpty(email)) {
//            ToastUtils.show(mContext, "请输入邮箱");
//            return;
//        }
//        if (!InputCheckUtil.isEmail(email)) {
//            ToastUtils.show(mContext, "请输入正确的邮箱");
//            return;
//        }
        if (StringUtils.isEmpty(mPhoto) && mSelectList.size() > 0) {
            uploadImg();
            return;
        }
        List<OrderBodyBean> bodyBeans = new ArrayList<>();
        if ("2".equals(mId)) {
            bodyBeans.add(new OrderBodyBean(Constants.ORDER_BIAODEJINE, "案件标的金额", change));
        } else {
            bodyBeans.add(new OrderBodyBean(Constants.ORDER_ANHAO, "涉嫌罪名", change));
            bodyBeans.add(new OrderBodyBean(Constants.ORDER_HUIJIAN, "是否已经会见", meeting));
            bodyBeans.add(new OrderBodyBean(Constants.ORDER_JIEDUAN, "案件处于阶段", stage));
        }
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_DANGWEI, "定金档位", mDepositMoney));
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("province", "" + mProvince);
        orderInfo.put("title", "" + mTitle + "-" + mClassifyTitle);
        orderInfo.put("city", "" + mCity);
        orderInfo.put("district", "" + mArea);
        orderInfo.put("phone", "" + phone);
        orderInfo.put("atlas", "" + mPhoto);
        orderInfo.put("thumb", "" + mCoverImg);
        orderInfo.put("email", "" + email);
        orderInfo.put("top_id", "" + mId);
        orderInfo.put("content", "" + explain);
        orderInfo.put("son_id", "" + mClassifyId);
        orderInfo.put("body", JSONUtils.toJsonString(bodyBeans));
        Map<String, Object> params = new HashMap<>();
        params.put("order_type", "1");
        params.put("remark", "");
        params.put("order_info", "" + JSONUtils.toJsonString(orderInfo));
        HttpUtils.getLawsuitPrice(mContext, orderInfo, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LawsuitJumpBean jumpBean = new LawsuitJumpBean();
                jumpBean.setOrderInfo(orderInfo);
                jumpBean.setBodyBeans(bodyBeans);
                Bundle bundle = new Bundle();
                bundle.putString("response", "" + response);
                bundle.putString("localPrice", "" + mLocalPrice);
                bundle.putString("subsidyPrice", "" + mSubsidyPrice);
                bundle.putSerializable("bean", jumpBean);
                if ("2".equals(mId)) {
                    bundle.putString("signPrice", "" + change);
                }
                MyApplication.openActivity(mContext, CostEstimatingActivity.class, bundle);
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
}