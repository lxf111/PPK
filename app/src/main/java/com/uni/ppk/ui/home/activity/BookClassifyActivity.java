package com.uni.ppk.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.BookTitleAdapter;
import com.uni.ppk.ui.home.bean.BookTitleBean;
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
 * Time: 10:54
 * 代写文书全部分类
 */
public class BookClassifyActivity extends BaseActivity {
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
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;

    private int mIndex = 1;//1代写文书 2案件诉讼 3法律咨询 4合同服务 5委托律师 6法律顾问 7文件翻译
    // 8诉讼投资 9诉讼执行 10案件诊断 11大律约见
    // 12诉讼指导 13法务110

    private BookTitleAdapter mAdapter;
    private String mTitle = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_classify;
    }

    @Override
    protected void initData() {
        mIndex = getIntent().getIntExtra("index", 1);
        mTitle = getIntent().getStringExtra("title");

        initTitle("更多");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BookTitleAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<BookTitleBean>() {
            @Override
            public void onItemClick(View view, int position, BookTitleBean model) {

            }

            @Override
            public void onItemLongClick(View view, int position, BookTitleBean model) {

            }
        });

        mAdapter.setOnClassifyItemCallback(new BookTitleAdapter.OnClassifyItemCallback() {
            @Override
            public void onClassifyCallback(OrderClassifyBean model, int position) {
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
        });

        getClassify();

    }

    @Override
    public void onBackPressed() {
//        for (int i = 0; i < mAdapter.getList().size(); i++) {
//            for (int j = 0; j < mAdapter.getList().get(i).getData().size(); j++) {
//                if (mAdapter.getList().get(i).getData().get(j).isSelect()) {

//                    return;
//                }
//            }
//        }
        super.onBackPressed();
    }

    private void getClassify() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "1");
        params.put("is_group", "1");
        HttpUtils.secondClassify(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<BookTitleBean> beans = JSONUtils.jsonString2Beans(response, BookTitleBean.class);
                if (beans != null && beans.size() > 0) {
                    mAdapter.refreshList(beans);
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
