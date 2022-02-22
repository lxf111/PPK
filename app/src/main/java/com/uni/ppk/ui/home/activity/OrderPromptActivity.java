package com.uni.ppk.ui.home.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.OrderPromptPopup;
import com.uni.ppk.ui.home.adapter.OrderClassifyAdapter;
import com.uni.ppk.ui.home.bean.OrderClassifyBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.utils.OrderAgreeUtils;
import com.uni.ppk.widget.CustomRecyclerView;

import java.io.IOException;
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
 * Time: 16:51
 * 下单提示界面：过度和选择子分类界面
 */
public class OrderPromptActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rlv_classify)
    CustomRecyclerView rlvClassify;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.view_classify)
    View viewClassify;

    private OrderClassifyAdapter mClassifyAdapter;//分类适配器

    private int mIndex = 1;//1代写文书 2案件诉讼 3法律咨询 4合同服务 5委托律师 6法律顾问 7文件翻译
    // 8诉讼投资 9诉讼执行 10案件诊断 11大律约见
    // 12诉讼指导 13法务110
    private String mTitle = "";
    private String mId = "";//第一级分类id

    private OrderPromptPopup mPromptPopup;//安全保障弹窗

    private String mClassifyId = "";//选中的分类id
    private String mPid = "";//一级分类id
    private String mClassifyTitle = "";//子分类标题

    private boolean isSecond = false;//是否为2级分类

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_prompt;
    }

    @Override
    protected void initData() {
        viewLine.setVisibility(View.VISIBLE);
        mIndex = getIntent().getIntExtra("index", 1);
        mTitle = getIntent().getStringExtra("title");
        mId = getIntent().getStringExtra("id");
        isSecond = getIntent().getBooleanExtra("isSecond", false);
        if (isSecond) {
            mClassifyId = getIntent().getStringExtra("classifyId");
            mPid = mId;
            mClassifyTitle = getIntent().getStringExtra("classifyTitle");
        }
        if (!StringUtils.isEmpty(mTitle)) {
            initTitle("" + mTitle);
        } else {
            initTitle("下单分类选择");
        }
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_order_custom);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(drawable, null, null, null);

        rlvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
        mClassifyAdapter = new OrderClassifyAdapter(mContext);
        rlvClassify.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<OrderClassifyBean>() {
            @Override
            public void onItemClick(View view, int position, OrderClassifyBean model) {
                if (model.isOther()) {
                    for (int i = 0; i < mClassifyAdapter.getList().size(); i++) {
                        mClassifyAdapter.getList().get(i).setSelect(false);
                    }
                    model.setSelect(true);
                    mClassifyId = "";
                    mPid = "";
                    Bundle bundle = new Bundle();
                    bundle.putString("id", "" + mId);
                    bundle.putString("title", "" + mTitle);
                    bundle.putInt("index", mIndex);
                    if (mIndex == 1) {
                        MyApplication.openActivityForResult(mContext, BookClassifyActivity.class, bundle, 101);
                    } else {
                        MyApplication.openActivityForResult(mContext, OtherClassifyActivity.class, bundle, 101);
                    }
                    mClassifyAdapter.notifyDataSetChanged();
                    return;
                }
                if (model.isSelect()) {
                    model.setSelect(false);
                    mClassifyId = "";
                    mPid = "";
                } else {
                    mClassifyId = "" + model.getId();
                    mPid = "" + model.getPid();
                    mClassifyTitle = "" + model.getName();
                    for (int i = 0; i < mClassifyAdapter.getList().size(); i++) {
                        mClassifyAdapter.getList().get(i).setSelect(false);
                    }
                    model.setSelect(true);
                }
                mClassifyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view, int position, OrderClassifyBean model) {

            }
        });
        //获取子分类
        getClassify();

        getTipContent();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.tv_submit, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //下单
            case R.id.tv_submit:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                //1代写文书 2案件诉讼 3法律咨询 4合同服务 5委托律师 6法律顾问 7文本下载
                // 8诉讼投资 9诉讼执行 10案件诊断 11大律约见
                // 12诉讼指导 13法务110
                if (StringUtils.isEmpty(mClassifyId)) {
                    ToastUtils.show(mContext, "请选择分类");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", "" + mTitle);
                bundle.putString("classifyTitle", "" + mClassifyTitle);
                bundle.putString("classifyId", "" + mClassifyId);
                bundle.putString("id", "" + mPid);
                if (mIndex == 1) {
                    MyApplication.openActivity(mContext, OrderBookActivity.class, bundle);
                } else if (mIndex == 2) {
                    MyApplication.openActivity(mContext, OrderLawsuitActivity.class, bundle);
                } else if (mIndex == 3) {
                    MyApplication.openActivity(mContext, OrderEnquireActivity.class, bundle);
                } else if (mIndex == 4) {
                    MyApplication.openActivity(mContext, OrderContractActivity.class, bundle);
                } else if (mIndex == 5) {
                    MyApplication.openActivity(mContext, OrderEntrustActivity.class, bundle);
                } else if (mIndex == 6) {
                    MyApplication.openActivity(mContext, OrderAdviserActivity.class, bundle);
                } else if (mIndex == 7) {
                    MyApplication.openActivity(mContext, OrderTranslateActivity.class, bundle);
                } else if (mIndex == 8) {
                    MyApplication.openActivity(mContext, OrderInvestActivity.class, bundle);
                } else if (mIndex == 9) {
                    MyApplication.openActivity(mContext, OrderExecuteActivity.class, bundle);
                } else if (mIndex == 10) {
                    MyApplication.openActivity(mContext, OrderDiagnosisActivity.class, bundle);
                } else if (mIndex == 11) {
                    MyApplication.openActivity(mContext, OrderAppointActivity.class, bundle);
                } else if (mIndex == 12) {
                    MyApplication.openActivity(mContext, OrderGuideActivity.class, bundle);
                } else if (mIndex == 13) {
                    MyApplication.openActivity(mContext, Order110Activity.class, bundle);
                }
                break;
            //客服
            case R.id.right_title:
                OrderAgreeUtils.jumpChat(mContext);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (mPromptPopup == null) {
                mPromptPopup = new OrderPromptPopup(mContext);
                mPromptPopup.showAtLocation(tvSubmit, Gravity.CENTER, 0, 0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    //代写文书
                    if (data != null) {
                        OrderClassifyBean bean = (OrderClassifyBean) data.getSerializableExtra("bean");
                        mClassifyId = "" + bean.getId();
                        mPid = "" + bean.getPid();
                        mClassifyTitle = "" + bean.getName();
                    }
                    break;
            }
        }
    }

    private void getClassify() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "" + mId);
        HttpUtils.secondClassify(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<OrderClassifyBean> beans = JSONUtils.jsonString2Beans(response, OrderClassifyBean.class);
                if (beans != null && beans.size() > 0) {
                    if (beans.size() > 7) {
                        if (!isSecond) {
                            beans.get(0).setSelect(true);
                            mClassifyId = "" + beans.get(0).getId();
                            mPid = "" + beans.get(0).getPid();
                            mClassifyTitle = "" + beans.get(0).getName();
                        }
                        OrderClassifyBean classifyBean = new OrderClassifyBean();
                        classifyBean.setName("其他");
                        classifyBean.setOther(true);
                        List<OrderClassifyBean> removeList = beans.subList(0, 7);
                        removeList.add(classifyBean);
                        mClassifyAdapter.refreshList(removeList);
                    } else {
                        if (!isSecond) {
                            beans.get(0).setSelect(true);
                            mClassifyId = "" + beans.get(0).getId();
                            mPid = "" + beans.get(0).getPid();
                            mClassifyTitle = "" + beans.get(0).getName();
                        }
                        mClassifyAdapter.refreshList(beans);
                    }
                    viewClassify.setVisibility(View.VISIBLE);
                } else {
                    //没有子分类
                    if (!isSecond) {
                        mClassifyId = "0";
                        mPid = "" + mId;
                        viewClassify.setVisibility(View.GONE);
                    }
                }
                if (isSecond) {
                    if (mClassifyAdapter.getList().size() > 0) {
                        boolean isHave = false;//是否存在相同的
                        for (int i = 0; i < mClassifyAdapter.getItemCount(); i++) {
                            mClassifyAdapter.getItem(i).setSelect(false);
                            if (mClassifyId.equals("" + mClassifyAdapter.getItem(i).getId())) {
                                mClassifyAdapter.getItem(i).setSelect(true);
                                mClassifyId = "" + mClassifyAdapter.getItem(i).getId();
                                mPid = "" + mClassifyAdapter.getItem(i).getPid();
                                isHave = true;
                            }
                        }
                        if (!isHave) {
                            mClassifyAdapter.getItem(mClassifyAdapter.getItemCount() - 1).setSelect(true);
                        }
                        mClassifyAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                mClassifyId = "0";
                mPid = "" + mId;
                viewClassify.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, mContext.getResources().getString(R.string.service_error));
            }
        });
    }

    private void getTipContent() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "" + mId);
        HttpUtils.prompt(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                String content = JSONUtils.getNoteJson(response, "service_introduction");
                if (!StringUtils.isEmpty(content)) {
                    tvIntroduce.setText("" + content);
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
