package com.uni.ppk.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.HomeClassifyAdapter;
import com.uni.ppk.ui.home.bean.HomeClassifyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 15:01
 * 首页的全部分类
 */
public class HomeClassifyActivity extends BaseActivity {
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
    //"合同审批",
    private String mName[] = {"出具律师函", "国家赔偿类诉讼", "刑事会见",
            "执行申请书", "法律意见书", "遗产继承类诉讼", "文本下载",
            "诉讼投资", "案件执行", "电话咨询", "大律约见",
            "诉讼指导", "法务110"};
    //    R.mipmap.ic_classify1,
    private int mImg[] = {R.mipmap.ic_classify2, R.mipmap.ic_classify3, R.mipmap.ic_classify4,
            R.mipmap.ic_classify5, R.mipmap.ic_classify6, R.mipmap.ic_classify7, R.mipmap.ic_classify8,
            R.mipmap.ic_classify9, R.mipmap.ic_classify10, R.mipmap.ic_classify11, R.mipmap.ic_classify12,
            R.mipmap.ic_classify13, R.mipmap.ic_classify14};

    private HomeClassifyAdapter mAdapter;
    private List<HomeClassifyBean> mBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_classify;
    }

    @Override
    protected void initData() {
        initTitle("全部分类");
        rlvList.setLayoutManager(new GridLayoutManager(mContext, 4));
        mAdapter = new HomeClassifyAdapter(mContext);
        rlvList.setAdapter(mAdapter);
        for (int i = 0; i < mName.length; i++) {
            HomeClassifyBean bean = new HomeClassifyBean();
            bean.setName(mName[i]);
            bean.setImg(mImg[i]);
            mBeanList.add(bean);
        }
        mAdapter.refreshList(mBeanList);
        mAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<HomeClassifyBean>() {
            @Override
            public void onItemClick(View view, int position, HomeClassifyBean model) {
                //合同审批=合同服务
                //
                //律师函、刑事会见=委托律师
                //
                //刑事辩护、劳动纠纷=案件诉讼
                //
                //执行申请书、法律意见书=代写文书
                Bundle bundle = new Bundle();
                bundle.putString("title", "" + model.getName());
                switch (model.getName()) {
//                    case "合同审批":
//                        bundle.putInt("index", 4);
//                        bundle.putString("id", "4");
//                        bundle.putString("classifyId", "140");
//                        bundle.putString("title", "合同服务");
//                        bundle.putString("classifyTitle", "合同审批");
//                        bundle.putBoolean("isSecond", true);
//                        break;
                    case "出具律师函":
                        bundle.putInt("index", 5);
                        bundle.putString("id", "5");
                        bundle.putString("classifyId", "63");
                        bundle.putString("title", "委托律师");
                        bundle.putString("classifyTitle", "出具律师函");
                        bundle.putBoolean("isSecond", true);
                        break;
                    case "国家赔偿类诉讼":
                        bundle.putInt("index", 2);
                        bundle.putString("id", "2,46");
                        bundle.putString("classifyId", "107");
                        bundle.putString("title", "案件诉讼");
                        bundle.putString("classifyTitle", "国家赔偿类诉讼");
                        bundle.putBoolean("isSecond", true);
                        break;
                    case "刑事会见":
                        bundle.putInt("index", 5);
                        bundle.putString("id", "5");
                        bundle.putString("classifyId", "136");
                        bundle.putString("title", "委托律师");
                        bundle.putString("classifyTitle", "刑事会见");
                        bundle.putBoolean("isSecond", true);
                        break;
                    case "执行申请书":
                        bundle.putInt("index", 1);
                        bundle.putString("id", "1");
                        bundle.putString("classifyId", "17");
                        bundle.putString("title", "代写文书");
                        bundle.putString("classifyTitle", "执行申请书");
                        bundle.putBoolean("isSecond", true);
                        break;
                    case "法律意见书":
                        bundle.putInt("index", 1);
                        bundle.putString("id", "1");
                        bundle.putString("classifyId", "67");
                        bundle.putString("title", "代写文书");
                        bundle.putString("classifyTitle", "法律意见书");
                        bundle.putBoolean("isSecond", true);
                        break;
                    case "遗产继承类诉讼":
                        bundle.putInt("index", 2);
                        bundle.putString("id", "2,46");
                        bundle.putString("classifyId", "101");
                        bundle.putString("title", "案件诉讼");
                        bundle.putString("classifyTitle", "遗产继承类诉讼");
                        bundle.putBoolean("isSecond", true);
                        break;
                    case "文本下载":
                        bundle.putInt("index", 7);
                        bundle.putString("id", "8");
                        break;
                    case "诉讼投资":
                        bundle.putInt("index", 8);
                        bundle.putString("id", "9");
                        break;
                    case "案件执行":
                        bundle.putInt("index", 9);
                        bundle.putString("id", "10");
                        break;
                    case "电话咨询":
                        bundle.putInt("index", 10);
                        bundle.putString("id", "11");
                        break;
                    case "大律约见":
                        bundle.putInt("index", 11);
                        bundle.putString("id", "12");
                        break;
                    case "诉讼指导":
                        bundle.putInt("index", 12);
                        bundle.putString("id", "13");
                        break;
                    case "法务110":
                        bundle.putInt("index", 13);
                        bundle.putString("id", "14");
                        break;
                }
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, HomeClassifyBean model) {

            }
        });
    }

}
