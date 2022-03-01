package com.uni.ppk.ui.human.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.ui.human.adapter.HumanClassifyAdapter;
import com.uni.ppk.ui.human.adapter.HumanHomeListAdapter;
import com.uni.ppk.ui.human.bean.HumanClassifyBean;
import com.uni.ppk.ui.human.bean.HumanHomeListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName HumanHomeFragment
 * @Description TODO
 * @Author LXF
 * @Date 2022/2/22 23:25
 * @Version 1.0
 */
public class HumanHomeFragment extends LazyBaseFragments {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_call_phone)
    TextView tvCallPhone;
    @BindView(R.id.iv_phone_icon)
    ImageView ivPhoneIcon;
    @BindView(R.id.tv_call_title)
    TextView tvCallTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.ll_sort)
    LinearLayout llSort;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.iv_person)
    ImageView ivPerson;
    @BindView(R.id.iv_work)
    ImageView ivWork;
    @BindView(R.id.view_top)
    View viewTop;

    private HumanHomeListAdapter mHomeAdapter;//下方招工信息

    private HumanClassifyAdapter mClassifyAdapter;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_human_home, null);
        return mRootView;
    }

    @Override
    public void initView() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
    }

    @Override
    public void initData() {
        mHomeAdapter = new HumanHomeListAdapter(mContext);
        rvList.setAdapter(mHomeAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        List<HumanHomeListBean> beans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            beans.add(new HumanHomeListBean());
        }
        mHomeAdapter.refreshList(beans);

        mClassifyAdapter = new HumanClassifyAdapter(mContext);
        rvClassify.setAdapter(mClassifyAdapter);
        rvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
        List<HumanClassifyBean> beanList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            beanList.add(new HumanClassifyBean());
        }
        mClassifyAdapter.refreshList(beanList);

    }

    @OnClick({R.id.iv_back, R.id.iv_icon, R.id.tv_search, R.id.tv_notice, R.id.tv_call_phone, R.id.ll_area, R.id.ll_type, R.id.ll_sort, R.id.iv_person, R.id.iv_work})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                mContext.finish();
                break;
            case R.id.iv_icon:
                break;
            //搜索
            case R.id.tv_search:
                break;
            //通知
            case R.id.tv_notice:
                break;
            //打电话
            case R.id.tv_call_phone:
                break;
            //城市地址
            case R.id.ll_area:
                break;
            //工种
            case R.id.ll_type:
                break;
            //排序
            case R.id.ll_sort:
                break;
            //发布招人
            case R.id.iv_person:
                break;
            //发布招工
            case R.id.iv_work:
                break;
        }
    }
}
