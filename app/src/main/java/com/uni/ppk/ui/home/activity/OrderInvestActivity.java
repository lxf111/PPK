package com.uni.ppk.ui.home.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.uni.commoncore.utils.AppManager;
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
import com.uni.ppk.pop.TipsPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.ui.home.bean.OrderBodyBean;
import com.uni.ppk.ui.home.bean.OrderPriceBean;
import com.uni.ppk.ui.home.bean.UploadImageBean;
import com.uni.ppk.ui.mine.adapter.GridImageAdapter;
import com.uni.ppk.utils.OrderAgreeUtils;
import com.uni.ppk.utils.PhotoSelectSingleUtile;
import com.uni.ppk.utils.TipsUtils;
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
 * 诉讼投资
 */
public class OrderInvestActivity extends BaseActivity {

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
    @BindView(R.id.tv_open_vip)
    TextView tvOpenVip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @BindView(R.id.tv_plaintiff)
    TextView tvPlaintiff;
    @BindView(R.id.edt_plaintiff)
    EditText edtPlaintiff;
    @BindView(R.id.tv_defendant)
    TextView tvDefendant;
    @BindView(R.id.edt_defendant)
    EditText edtDefendant;
    @BindView(R.id.tv_show_price)
    TextView tvShowPrice;
    @BindView(R.id.edt_show_price)
    EditText edtShowPrice;
    @BindView(R.id.tv_title_stage)
    TextView tvTitleStage;
    @BindView(R.id.tv_stage)
    TextView tvStage;
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
    private String mTitle = "";

    private GridImageAdapter mPhotoAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();
    private boolean isAgree = false;

    private String mProvince = "";
    private String mCity = "";
    private String mArea = "";
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
    private String mAgreeDefendant = "";
    private String mAgreePlaintiff = "";
    private String mAgreeStage = "";

    private String mDepositMoney = "";//定金档位默认第一个

    private String mPhoto = "";
    private String mCoverImg = "";//封面

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_invest;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra("title");
        mId = getIntent().getStringExtra("id");
        mClassifyId = getIntent().getStringExtra("classifyId");
        initTitle("" + mTitle);
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
        mSelectPopup = new CommonSelectPopup(mContext);
        getOrderPrice();
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
                            if (Constants.ORDER_JIEDUAN.equals(bean.getFields().get(i).getField())) {
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
                            } else if (Constants.ORDER_BEIGAO.equals(bean.getFields().get(i).getField())) {
                                //被告/被执行人/债务人
                                mAgreeDefendant = "" + bean.getFields().get(i).getTips();
                            } else if (Constants.ORDER_YUANGAO.equals(bean.getFields().get(i).getField())) {
                                //原告/申请执行人/债权人
                                mAgreePlaintiff = "" + bean.getFields().get(i).getTips();
                            } else if (Constants.ORDER_BIAODEJINE.equals(bean.getFields().get(i).getField())) {
                                //标的金额
                                mAgreeMoney = "" + bean.getFields().get(i).getTips();
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

    @OnClick({R.id.tv_open_vip, R.id.tv_pay, R.id.right_title, R.id.tv_title_address, R.id.tv_explain_title,
            R.id.tv_address, R.id.tv_title_stage, R.id.tv_stage, R.id.iv_agree, R.id.tv_agree_rule
            , R.id.tv_plaintiff, R.id.tv_defendant, R.id.tv_show_price})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //开通vip
            case R.id.tv_open_vip:
                OrderAgreeUtils.jumpVip(mContext);
                break;
            //案件所标金额
            case R.id.tv_show_price:
                OrderAgreeUtils.jumpAgree(mContext, tvShowPrice.getText().toString().trim(), mAgreeMoney, true);
                break;
            //被告（被告/被执行人/债务人）
            case R.id.tv_defendant:
                OrderAgreeUtils.jumpAgree(mContext, tvDefendant.getText().toString().trim(), mAgreeDefendant, true);
                break;
            //原告（原告/申请执行人/债权人）
            case R.id.tv_plaintiff:
                OrderAgreeUtils.jumpAgree(mContext, tvPlaintiff.getText().toString().trim(), mAgreePlaintiff, true);
                break;
            //支付
            case R.id.tv_pay:
//                bundle.putInt("type", 3);
//                MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
                if (!isAgree) {
                    ToastUtils.show(mContext, "请同意相关条款");
                    return;
                }
                if (Integer.parseInt(MyApplication.mPreferenceProvider.getLevel()) < 1) {
                    TipsUtils.show(mContext, tvPay, "", "此功能仅对会员开放，\n点击开通会员，了解会员权益", "取消", "开通会员", new TipsPopup.OnTipsCallback() {
                        @Override
                        public void submit() {
                            MyApplication.openActivity(mContext, VipListActivity.class);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    return;
                }
                submit();
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
            //案件阶段
            case R.id.tv_title_stage:
                OrderAgreeUtils.jumpAgree(mContext, tvTitleStage.getText().toString().trim()
                        , mAgreeStage, true);
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
                mSelectPopup.showAtLocation(tvStage, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_explain_title:
                OrderAgreeUtils.jumpAgree(mContext, tvExplainTitle.getText().toString().trim()
                        , mAgreeExplain, true);
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
            //客服
            case R.id.right_title:
                OrderAgreeUtils.jumpChat(mContext);
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
        String stage = tvStage.getText().toString().trim();
        String plaintiff = edtPlaintiff.getText().toString().trim();
        String defendant = edtDefendant.getText().toString().trim();
        String money = edtShowPrice.getText().toString().trim();
        if (StringUtils.isEmpty(plaintiff)) {
            ToastUtils.show(mContext, "请输入原告");
            return;
        }
        if (StringUtils.isEmpty(defendant)) {
            ToastUtils.show(mContext, "请输入被告");
            return;
        }
        if (StringUtils.isEmpty(money)) {
            ToastUtils.show(mContext, "请输入标的金额");
            return;
        }
        if (StringUtils.isEmpty(stage)) {
            ToastUtils.show(mContext, "请选择案件阶段");
            return;
        }
        if (StringUtils.isEmpty(mProvince)) {
            ToastUtils.show(mContext, "请选择地点");
            return;
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
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_BEIGAO, "被告/被执行人/债务人", defendant));
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_YUANGAO, "原告/申请执行人/债权人", plaintiff));
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_BIAODEJINE, "案件标的金额", money));
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_JIEDUAN, "案件处于阶段", stage));
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("province", "" + mProvince);
        orderInfo.put("title", "" + mTitle);
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
        HttpUtils.createOrder(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String orderNum = JSONUtils.getNoteJson(response, "order_sn");
                String orderMoney = JSONUtils.getNoteJson(response, "payable_money");
                String status = JSONUtils.getNoteJson(response, "pay_status");
                if ("1".equals(status)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 0);
                    MyApplication.openActivity(mContext, PaySuccessActivity.class, bundle);
                    finish();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("orderNum", "" + orderNum);
                bundle.putString("orderMoney", "" + orderMoney);
                AppManager.getInstance().finishActivity(OrderPromptActivity.class);
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
}
