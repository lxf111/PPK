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
import com.uni.ppk.pop.CommonSelectPopup;
import com.uni.ppk.pop.bean.CommonSelectBean;
import com.uni.ppk.ui.home.bean.OrderBodyBean;
import com.uni.ppk.ui.home.bean.OrderPriceBean;
import com.uni.ppk.ui.home.bean.TxtTypeBean;
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
 * Date: 2020/9/10
 * Time: 18:01
 * 文件翻译
 */
public class OrderTranslateActivity extends BaseActivity {
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
    @BindView(R.id.iv_reduce)
    ImageView ivReduce;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.tv_title_contact)
    TextView tvTitleContact;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_title_second)
    TextView tvTitleSecond;
    @BindView(R.id.tv_second)
    TextView tvSecond;

    private String mTitle = "";

    private GridImageAdapter mPhotoAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();
    private boolean isAgree = false;

    private String mId = "";//一级分类id
    private String mClassifyId = "";//二级分类id

    //协议 标问号的标题协议内容
    private String mAgreeExplain = "";

    private OrderPriceBean mPriceBean;//价格

    private String mPhoto = "";
    private String mCoverImg = "";//封面

    private String mVipPrice = "";//vip的单页价格
    private String mNormalPrice = "";//非会员的单页价格
    private String mMinPage = "";//最小页数
    private String mMaxPage = "";//最大页数

    private CommonSelectPopup mSelectPopup;//选择弹窗
    private String mAgreeType = "";
    private String mAgreeSecond = "";
    private List<CommonSelectBean> mContactList = new ArrayList<>();//合同类型
    private Map<Integer, List<CommonSelectBean>> mSecondMap = new HashMap<>();//第二层
    private int mContactPosition = 0;//选择合同类型的下标
    private String mTxtTypeId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_translate;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra("title");
        mId = getIntent().getStringExtra("id");
        mClassifyId = getIntent().getStringExtra("classifyId");

        mSelectPopup = new CommonSelectPopup(mContext);

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
        getOrderPrice();
        getTxtType();
        OrderAgreeUtils.showVipTxt(tvOpenVip);
        edtExplain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @OnClick({R.id.tv_open_vip, R.id.right_title, R.id.tv_title_contact, R.id.tv_contact, R.id.tv_title_second, R.id.tv_second, R.id.iv_add, R.id.iv_reduce, R.id.tv_pay, R.id.tv_title_address, R.id.tv_address, R.id.tv_explain_title, R.id.iv_agree, R.id.tv_agree_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //客服
            case R.id.right_title:
                OrderAgreeUtils.jumpChat(mContext);
                break;
            case R.id.tv_open_vip:
                OrderAgreeUtils.jumpVip(mContext);
                break;
            case R.id.tv_pay:
                if (!isAgree) {
                    ToastUtils.show(mContext, "请同意相关条款");
                    return;
                }
                if (mPriceBean == null) {
                    getOrderPrice();
                    ToastUtils.show(mContext, getString(R.string.service_error));
                    return;
                }
                submit();
                break;
            case R.id.tv_title_address:
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_explain_title:
                OrderAgreeUtils.jumpAgree(mContext, tvExplainTitle.getText().toString().trim(), mAgreeExplain, true);
                break;
            case R.id.iv_agree:
                if (isAgree) {
                    isAgree = false;
                    ivAgree.setImageResource(R.mipmap.ic_order_agree);
                } else {
                    isAgree = true;
                    ivAgree.setImageResource(R.mipmap.ic_agree_select);
                }
                break;
            case R.id.iv_add:
                int number = Integer.parseInt(tvNumber.getText().toString().trim());
                if (number >= Integer.parseInt(mMaxPage)) {
                    return;
                }
                number++;
                tvNumber.setText("" + number);
                tvNormalPrice.setText("¥" + ArithUtils.saveSecond(ArithUtils.mul("" + number, mNormalPrice)));
                tvVipPrice.setText("¥" + ArithUtils.saveSecond(ArithUtils.mul("" + number, mVipPrice)));
                break;
            case R.id.iv_reduce:
                int numberReduce = Integer.parseInt(tvNumber.getText().toString().trim());
                if (numberReduce <= Integer.parseInt(mMinPage)) {
                    return;
                }
                numberReduce--;
                tvNumber.setText("" + numberReduce);
                tvNormalPrice.setText("¥" + ArithUtils.saveSecond(ArithUtils.mul("" + numberReduce, mNormalPrice)));
                tvVipPrice.setText("¥" + ArithUtils.saveSecond(ArithUtils.mul("" + numberReduce, mVipPrice)));
                break;
            case R.id.tv_agree_rule:
                OrderAgreeUtils.getRule(mContext);
                break;
            //合同类型
            case R.id.tv_title_contact:
//                OrderAgreeUtils.jumpAgree(mContext, tvTitleContact.getText().toString().trim(), mAgreeType, true);
                break;
            //合同类型
            case R.id.tv_contact:
                mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                    @Override
                    public void onItemClick(View view, int position, CommonSelectBean model) {
                        mSelectPopup.dismiss();
                        mContactPosition = position;
                        if (!model.getName().equals(tvContact.getText().toString().trim())) {
                            tvSecond.setText("");
                        }
                        tvContact.setText("" + model.getName());
                    }

                    @Override
                    public void onItemLongClick(View view, int position, CommonSelectBean model) {

                    }
                });
                mSelectPopup.setmSelectBeans(mContactList);
                mSelectPopup.setTitle("请选择合同类型");
                mSelectPopup.showAtLocation(tvPay, Gravity.BOTTOM, 0, 0);
                break;
            //合同项
            case R.id.tv_title_second:
//                OrderAgreeUtils.jumpAgree(mContext, tvTitleSecond.getText().toString().trim(), mAgreeSecond, true);
                break;
            //合同项
            case R.id.tv_second:
                if (StringUtils.isEmpty(tvContact.getText().toString().trim())) {
                    ToastUtils.show(mContext, "请选择合同类型");
                    return;
                }
                mSelectPopup.setmSelectBeans(mSecondMap.get(mContactPosition));
                mSelectPopup.setTitle("请选择合同列表");
                mSelectPopup.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CommonSelectBean>() {
                    @Override
                    public void onItemClick(View view, int position, CommonSelectBean model) {
                        mSelectPopup.dismiss();
                        tvSecond.setText("" + model.getName());
                        mTxtTypeId = model.getId();
                    }

                    @Override
                    public void onItemLongClick(View view, int position, CommonSelectBean model) {

                    }
                });
                mSelectPopup.showAtLocation(tvPay, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private void submit() {
        String explain = edtExplain.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (StringUtils.isEmpty(mTxtTypeId)) {
            ToastUtils.show(mContext, "请选择合同项");
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
//        bodyBeans.add(new OrderBodyBean(Constants.ORDER_PAGE, "翻译页数", tvNumber.getText().toString().trim()));
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_TXT_SECOND, "合同项", tvSecond.getText().toString().trim()));
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_TYPE_ID, "类型ID", mTxtTypeId));
        bodyBeans.add(new OrderBodyBean(Constants.ORDER_TXT_FIRST, "合同类型", tvContact.getText().toString().trim()));
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("title", "" + mTitle);
        orderInfo.put("phone", "" + phone);
        orderInfo.put("email", "" + email);
        orderInfo.put("top_id", "" + mId);
        orderInfo.put("content", "" + explain);
        orderInfo.put("atlas", "" + mPhoto);
        orderInfo.put("thumb", "" + mCoverImg);
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

    private void getOrderPrice() {
        Map<String, Object> params = new HashMap<>();
        params.put("son_id", "" + mClassifyId);
        params.put("typeid", "" + mId);
        HttpUtils.orderPrice(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                OrderPriceBean bean = JSONUtils.jsonString2Bean(response, OrderPriceBean.class);
                if (bean != null) {
                    mPriceBean = bean;
                    if (bean.getPrices() != null && bean.getPrices().size() > 0) {
                        for (int i = 0; i < bean.getPrices().size(); i++) {
                            //获取会员价跟平台价
                            if (Constants.ORDER_DEFAULT_PRICE.equals(bean.getPrices().get(i).getType())) {
                                tvNormalPrice.setText("¥" + ArithUtils.saveSecond(bean.getPrices().get(i).getPrice()));
                                tvVipPrice.setText("¥" + ArithUtils.saveSecond(bean.getPrices().get(i).getMember_price()));
                                mVipPrice = bean.getPrices().get(i).getMember_price();
                                mNormalPrice = bean.getPrices().get(i).getPrice();
                                if (bean.getPrices().get(i).getRule() != null
                                        && bean.getPrices().get(i).getRule().size() > 0) {
                                    mMinPage = bean.getPrices().get(i).getRule().get(0).getMin();
                                    mMaxPage = bean.getPrices().get(i).getRule().get(0).getMax();
                                    if (Integer.parseInt(mMinPage) > 0) {
                                        tvNumber.setText("" + mMinPage);
                                        tvNormalPrice.setText("¥" + ArithUtils.saveSecond(ArithUtils.mul("" + mMinPage, mNormalPrice)));
                                        tvVipPrice.setText("¥" + ArithUtils.saveSecond(ArithUtils.mul("" + mMinPage, mVipPrice)));
                                    }
                                }
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
                    if (bean.getTips() != null) {
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

    private void getTxtType() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.newTxtDown(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<TxtTypeBean> beanList = JSONUtils.jsonString2Beans(response, TxtTypeBean.class);
                if (beanList != null) {
                    if (beanList != null && beanList.size() > 0) {
                        for (int j = 0; j < beanList.size(); j++) {
                            CommonSelectBean selectBean = new CommonSelectBean();
                            selectBean.setName(beanList.get(j).getName());
                            mContactList.add(selectBean);
                            List<CommonSelectBean> secondBeans = new ArrayList<>();
                            if (beanList.get(j).getTexts() != null
                                    && beanList.get(j).getTexts().size() > 0) {
                                for (int k = 0; k < beanList.get(j).getTexts().size(); k++) {
                                    CommonSelectBean secondBean = new CommonSelectBean();
                                    secondBean.setName(beanList.get(j).getTexts().get(k).getName());
                                    secondBean.setId("" + beanList.get(j).getTexts().get(k).getFile_id());
                                    secondBeans.add(secondBean);
                                }
                            }
                            mSecondMap.put(mContactList.size() - 1, secondBeans);
                        }
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

}
