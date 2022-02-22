package com.uni.ppk.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.OrderClassifyAdapter;
import com.uni.ppk.ui.home.bean.OrderClassifyBean;
import com.uni.ppk.utils.LoginCheckUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 11:08
 * 其他分类：一层
 */
public class OtherClassifyActivity extends BaseActivity {
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
    @BindView(R.id.rlv_classify)
    RecyclerView rlvClassify;

    private OrderClassifyAdapter mClassifyAdapter;

    private String mId = "";
    private int mIndex = 1;//1代写文书 2案件诉讼 3法律咨询 4合同服务 5委托律师 6法律顾问 7文件翻译
    // 8诉讼投资 9诉讼执行 10案件诊断 11大律约见
    // 12诉讼指导 13法务110
    private String mTitle = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_classify;
    }

    @Override
    protected void initData() {
        mIndex = getIntent().getIntExtra("index", 1);
        mTitle = getIntent().getStringExtra("title");
        mId = getIntent().getStringExtra("id");
        initTitle("更多");
        rlvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
        mClassifyAdapter = new OrderClassifyAdapter(mContext);
        rlvClassify.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<OrderClassifyBean>() {
            @Override
            public void onItemClick(View view, int position, OrderClassifyBean model) {
//                Intent intent = new Intent();
//                intent.putExtra("bean", model);
//                setResult(RESULT_OK, intent);
//                finish();
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                //1代写文书 2案件诉讼 3法律咨询 4合同服务 5委托律师 6法律顾问 7文件翻译
                // 8诉讼投资 9诉讼执行 10案件诊断 11大律约见
                // 12诉讼指导 13法务110
//                if (StringUtils.isEmpty(mClassifyId)) {
//                    ToastUtils.show(mContext, "请选择分类");
//                    return;
//                }
                Bundle bundle = new Bundle();
                bundle.putString("title", "" + mTitle);
                bundle.putString("classifyTitle", "" + model.getName());
                bundle.putString("classifyId", "" + model.getId());
                bundle.putString("id", "" + model.getPid());
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
            }

            @Override
            public void onItemLongClick(View view, int position, OrderClassifyBean model) {

            }
        });
        getClassify();
    }

    private void getClassify() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "" + mId);
        HttpUtils.secondClassify(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<OrderClassifyBean> beans = JSONUtils.jsonString2Beans(response, OrderClassifyBean.class);
                if (beans != null && beans.size() > 0) {
                    mClassifyAdapter.refreshList(beans);
                }
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
