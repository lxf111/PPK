package com.uni.ppk.ui.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.home.activity.CitySelectActivity;
import com.uni.ppk.ui.home.activity.ClassroomActivity;
import com.uni.ppk.ui.home.activity.HomeClassifyActivity;
import com.uni.ppk.ui.home.activity.LawFirmActivity;
import com.uni.ppk.ui.home.activity.LawyerDetailActivity;
import com.uni.ppk.ui.home.activity.Order110Activity;
import com.uni.ppk.ui.home.activity.OrderAdviserActivity;
import com.uni.ppk.ui.home.activity.OrderAppointActivity;
import com.uni.ppk.ui.home.activity.OrderBookActivity;
import com.uni.ppk.ui.home.activity.OrderContractActivity;
import com.uni.ppk.ui.home.activity.OrderDiagnosisActivity;
import com.uni.ppk.ui.home.activity.OrderEnquireActivity;
import com.uni.ppk.ui.home.activity.OrderEntrustActivity;
import com.uni.ppk.ui.home.activity.OrderExecuteActivity;
import com.uni.ppk.ui.home.activity.OrderGuideActivity;
import com.uni.ppk.ui.home.activity.OrderInvestActivity;
import com.uni.ppk.ui.home.activity.OrderLawsuitActivity;
import com.uni.ppk.ui.home.activity.OrderPromptActivity;
import com.uni.ppk.ui.home.activity.OrderTranslateActivity;
import com.uni.ppk.ui.home.activity.SearchActivity;
import com.uni.ppk.ui.home.activity.VipListActivity;
import com.uni.ppk.ui.home.adapter.HomeLawFirmAdapter;
import com.uni.ppk.ui.home.adapter.HomeLawyerAdapter;
import com.uni.ppk.ui.home.adapter.MarqueeViewAdapter;
import com.uni.ppk.ui.home.bean.HomeBannerBean;
import com.uni.ppk.ui.home.bean.HomeLampBean;
import com.uni.ppk.ui.home.bean.HomeLawFirmBean;
import com.uni.ppk.ui.home.bean.HomeLawyerBean;
import com.uni.ppk.ui.message.activity.SystemMessageActivity;
import com.uni.ppk.ui.message.bean.LastMessageBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.widget.CustomImageView4;
import com.uni.ppk.widget.CustomRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.stx.xmarqueeview.XMarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:13
 * 首页
 */
public class HomeFragment extends LazyBaseFragments {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_unread)
    TextView tvUnread;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_vip)
    CustomImageView4 ivVip;
    @BindView(R.id.tv_book)
    TextView tvBook;
    @BindView(R.id.tv_lawsuit)
    TextView tvLawsuit;
    @BindView(R.id.tv_lawyer)
    TextView tvLawyer;
    @BindView(R.id.tv_contract)
    TextView tvContract;
    @BindView(R.id.tv_entrust)
    TextView tvEntrust;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.tv_adviser)
    TextView tvAdviser;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.iv_adver_right)
    ImageView ivAdverRight;
    @BindView(R.id.iv_adver_clock)
    ImageView ivAdverClock;
    @BindView(R.id.upview2)
    XMarqueeView upView;
    @BindView(R.id.banner_home)
    Banner bannerHome;
    @BindView(R.id.tv_law_firm)
    TextView tvLawFirm;
    @BindView(R.id.tv_select_lawyer)
    TextView tvSelectLawyer;
    @BindView(R.id.rlv_law_firm)
    CustomRecyclerView rlvLawFirm;
    @BindView(R.id.rlv_lawyer)
    CustomRecyclerView rlvLawyer;
    @BindView(R.id.cv_banner)
    CardView cvBanner;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private MarqueeViewAdapter mLampAdapter;//跑马灯

    private HomeLawFirmAdapter mLawFirmAdapter;//律所适配器

    private HomeLawyerAdapter mLawyerAdapter;//律师适配器

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//rxbus取消订阅

    private int mPage = 1;

    private int mSelectType = 1;//1律所 2律师

    private List<HomeBannerBean> mBannerBeans;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_home, null);
        return mRootView;
    }

    @Override
    public void initView() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
        ViewGroup.LayoutParams lp = bannerHome.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int) (MyApplication.getWidth() * 0.37);
        bannerHome.setLayoutParams(lp);

        //律所
        rlvLawFirm.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mLawFirmAdapter = new HomeLawFirmAdapter(mContext);
        rlvLawFirm.setAdapter(mLawFirmAdapter);
        mLawFirmAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<HomeLawFirmBean>() {
            @Override
            public void onItemClick(View view, int position, HomeLawFirmBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, LawFirmActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, HomeLawFirmBean model) {

            }
        });

        //律师
        rlvLawyer.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mLawyerAdapter = new HomeLawyerAdapter(mContext);
        rlvLawyer.setAdapter(mLawyerAdapter);
        mLawyerAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<HomeLawyerBean>() {
            @Override
            public void onItemClick(View view, int position, HomeLawyerBean model) {
                Bundle bundle = new Bundle();
                bundle.putString("id", "" + model.getId());
                MyApplication.openActivity(mContext, LawyerDetailActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, HomeLawyerBean model) {

            }
        });
    }

    @Override
    public void initData() {
        initRefreshLayout();
        //初始化轮播图
        initBanner();
        //获取轮播图
        getBanner();
        //获取跑马灯
        getLamp();
        //初始化定位
        initMao();
        getVipTop();
        RxBus.getInstance().toObservable(String.class)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String type) {
                        if ("refreshAddress".equals(type)) {
                            tvCity.setText("" + MyApplication.mPreferenceProvider.getCity());
                            mPage = 1;
                            if (mSelectType == 1) {
                                getLawFirm();
                            } else {
                                getLawyer();
                            }
                        } else if ("refreshLocation".equals(type)) {
                            initMao();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        if (!StringUtils.isEmpty(MyApplication.mPreferenceProvider.getCity())) {
            tvCity.setText("" + MyApplication.mPreferenceProvider.getCity());
            getLawFirm();
        }
    }

    //获取轮播图
    private void getVipTop() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "" + 5);
        params.put("app_type", "" + 1);
        HttpUtils.homeBanner(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<HomeBannerBean> mBannerBeans = JSONUtils.jsonString2Beans(response, HomeBannerBean.class);
                if (mBannerBeans != null && mBannerBeans.size() > 0) {
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mBannerBeans.get(0).getThumb()), ivVip, mContext, R.mipmap.ic_home_vip);
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

    @OnClick({R.id.tv_city, R.id.iv_message, R.id.tv_search, R.id.iv_vip, R.id.tv_book,
            R.id.tv_lawsuit, R.id.tv_lawyer, R.id.tv_contract, R.id.tv_entrust, R.id.tv_course,
            R.id.tv_adviser, R.id.tv_all, R.id.tv_select_lawyer, R.id.tv_law_firm})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //城市选择
            case R.id.tv_city:
                MyApplication.openActivity(mContext, CitySelectActivity.class);
                break;
            //消息
            case R.id.iv_message:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, SystemMessageActivity.class);
                break;
            //搜索
            case R.id.tv_search:
                MyApplication.openActivity(mContext, SearchActivity.class);
                break;
            //vip
            case R.id.iv_vip:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, VipListActivity.class);
                break;
            //代写文书
            case R.id.tv_book:
                bundle.putInt("index", 1);
                bundle.putString("title", "代写文书");
                bundle.putString("id", "1");
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                break;
            //案件诉讼
            case R.id.tv_lawsuit:
                bundle.putInt("index", 2);
                bundle.putString("title", "案件诉讼");
                bundle.putString("id", "2,46");
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                break;
            //法律咨询
            case R.id.tv_lawyer:
                bundle.putInt("index", 3);
                bundle.putString("title", "法律咨询");
                bundle.putString("id", "3");
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                break;
            //合同服务
            case R.id.tv_contract:
                bundle.putInt("index", 4);
                bundle.putString("title", "合同服务");
                bundle.putString("id", "4");
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                break;
            //委托律师
            case R.id.tv_entrust:
                bundle.putInt("index", 5);
                bundle.putString("title", "委托律师");
                bundle.putString("id", "5");
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                break;
            //在线课程
            case R.id.tv_course:
                MyApplication.openActivity(mContext, ClassroomActivity.class);
                break;
            //法律顾问
            case R.id.tv_adviser:
                bundle.putInt("index", 6);
                bundle.putString("title", "法律套餐");
                bundle.putString("id", "7");
                MyApplication.openActivity(mContext, OrderPromptActivity.class, bundle);
                break;
            //全部分类
            case R.id.tv_all:
                MyApplication.openActivity(mContext, HomeClassifyActivity.class);
                break;
            //律所
            case R.id.tv_law_firm:
                tvLawFirm.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvSelectLawyer.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                rlvLawFirm.setVisibility(View.VISIBLE);
                rlvLawyer.setVisibility(View.GONE);
                mPage = 1;
                mSelectType = 1;
                getLawFirm();
                break;
            //律师
            case R.id.tv_select_lawyer:
                tvLawFirm.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                tvSelectLawyer.setTextColor(mContext.getResources().getColor(R.color.theme));
                rlvLawFirm.setVisibility(View.GONE);
                rlvLawyer.setVisibility(View.VISIBLE);
                mPage = 1;
                mSelectType = 2;
                getLawyer();
                break;
        }
    }

    //初始化轮播图
    private void initBanner() {
        bannerHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object obj, ImageView imageView) {
                HomeBannerBean bannerListBean = (HomeBannerBean) obj;
                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(bannerListBean.getThumb()), imageView, mContext, R.mipmap.ic_default_wide);
//                imageView.setImageResource(R.mipmap.ic_default_wide);
            }
        });
        bannerHome.setBannerStyle(BannerConfig.CENTER);
        bannerHome.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        bannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mBannerBeans.get(position).getTypes_id() <= 0 || mBannerBeans.get(position).getJump() == null) {
                    return;
                }
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                int mIndex = mBannerBeans.get(position).getTypes_id();
                String mTitle = mBannerBeans.get(position).getJump().getPname();
                String mClassifyTitle = mBannerBeans.get(position).getJump().getSon_name();
                String mPid = "" + mBannerBeans.get(position).getJump().getPid();
                String mClassifyId = "" + mBannerBeans.get(position).getJump().getSon_id();
                //1代写文书 2案件诉讼 3法律咨询 4合同服务 5委托律师 6法律顾问 7文本下载
                // 8诉讼投资 9诉讼执行 10案件诊断 11大律约见
                // 12诉讼指导 13法务110
                if (mIndex == -1) {
                    MyApplication.openActivity(mContext, VipListActivity.class);
                    return;
                }
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
                } else if (mIndex == 2 || mIndex == 46) {
                    MyApplication.openActivity(mContext, OrderLawsuitActivity.class, bundle);
                } else if (mIndex == 3) {
                    MyApplication.openActivity(mContext, OrderEnquireActivity.class, bundle);
                } else if (mIndex == 4) {
                    MyApplication.openActivity(mContext, OrderContractActivity.class, bundle);
                } else if (mIndex == 5) {
                    MyApplication.openActivity(mContext, OrderEntrustActivity.class, bundle);
                } else if (mIndex == 7) {
                    MyApplication.openActivity(mContext, OrderAdviserActivity.class, bundle);
                } else if (mIndex == 8) {
                    MyApplication.openActivity(mContext, OrderTranslateActivity.class, bundle);
                } else if (mIndex == 9) {
                    MyApplication.openActivity(mContext, OrderInvestActivity.class, bundle);
                } else if (mIndex == 10) {
                    MyApplication.openActivity(mContext, OrderExecuteActivity.class, bundle);
                } else if (mIndex == 11) {
                    MyApplication.openActivity(mContext, OrderDiagnosisActivity.class, bundle);
                } else if (mIndex == 12) {
                    MyApplication.openActivity(mContext, OrderAppointActivity.class, bundle);
                } else if (mIndex == 13) {
                    MyApplication.openActivity(mContext, OrderGuideActivity.class, bundle);
                } else if (mIndex == 14) {
                    MyApplication.openActivity(mContext, Order110Activity.class, bundle);
                }
//                if (!StringUtils.isEmpty(mBannerBeans.get(position).getHref())) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(NormalWebViewConfig.URL, "" + mBannerBeans.get(position).getHref());
//                    bundle.putString(NormalWebViewConfig.TITLE, "" + mBannerBeans.get(position).getName());
//                    bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, false);
//                    MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
//                }
            }
        });
    }

    private void initMao() {
        //初始化定位
        try {
            AMapLocationClient.updatePrivacyShow(mContext,true,true);
            AMapLocationClient.updatePrivacyAgree(mContext,true);
            mLocationClient = new AMapLocationClient(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        mLocationOption.setInterval(60000);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private class MyAMapLocationListener implements AMapLocationListener {
        //位置变化监听
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            LogUtils.e("TAG", "aMapLocation=" + JSONUtils.toJsonString(aMapLocation));
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    if (StringUtils.isEmpty(MyApplication.mPreferenceProvider.getCity())) {
                        MyApplication.mPreferenceProvider.setCity(aMapLocation.getCity());
                        tvCity.setText("" + aMapLocation.getCity());
                        getLawFirm();
                    }
                    MyApplication.mPreferenceProvider.setLocationAddress(aMapLocation.getCity());
                    MyApplication.mPreferenceProvider.setLatitude("" + aMapLocation.getLatitude());
                    MyApplication.mPreferenceProvider.setLongitude("" + aMapLocation.getLongitude());
                    MyApplication.mPreferenceProvider.setProvince(aMapLocation.getProvince());
//                    mPage = 1;
//                    getData();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    //获取律师
    private void getLawyer() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "" + mPage);
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("city_name", "" + MyApplication.mPreferenceProvider.getCity());
//        params.put("service_limit", "2");
        HttpUtils.homeLawyerList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<HomeLawyerBean> listBeans = JSONUtils.parserArray(response, "list", HomeLawyerBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mLawyerAdapter.refreshList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        mLawyerAdapter.clear();
                    }
                } else {
                    if (listBeans != null && listBeans.size() > 0) {
                        refreshLayout.finishLoadMore();
                        mLawyerAdapter.appendToList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mLawyerAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mLawyerAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    //获取律所
    private void getLawFirm() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "" + mPage);
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("city_name", "" + MyApplication.mPreferenceProvider.getCity());
//        params.put("service_limit", "2");
        HttpUtils.homeLawFirmList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<HomeLawFirmBean> listBeans = JSONUtils.parserArray(response, "list", HomeLawFirmBean.class);
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    if (listBeans != null && listBeans.size() > 0) {
                        mLawFirmAdapter.refreshList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                        mLawFirmAdapter.clear();
                    }
                } else {
                    if (listBeans != null && listBeans.size() > 0) {
                        refreshLayout.finishLoadMore();
                        mLawFirmAdapter.appendToList(listBeans);
                    } else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mLawFirmAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(Call call, IOException e) {
                if (mPage == 1) {
                    refreshLayout.finishRefresh();
                    mLawFirmAdapter.clear();
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }

    //获取轮播图
    private void getBanner() {
        Map<String, Object> params = new HashMap<>();
        params.put("typeid", "" + 3);
        params.put("app_type", "" + 1);
        HttpUtils.homeBanner(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mBannerBeans = JSONUtils.jsonString2Beans(response, HomeBannerBean.class);
                if (mBannerBeans != null && mBannerBeans.size() > 0) {
                    bannerHome.setImages(mBannerBeans);
                    bannerHome.start();
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

    //获取跑马灯
    private void getLamp() {
        Map<String, Object> params = new HashMap<>();
        params.put("size", "" + 100);
        HttpUtils.homeLamp(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<HomeLampBean> homeLampBean = JSONUtils.jsonString2Beans(response, HomeLampBean.class);
                if (homeLampBean != null && homeLampBean.size() > 0) {
                    if (mLampAdapter == null) {
                        mLampAdapter = new MarqueeViewAdapter(homeLampBean, mContext);
                        upView.setAdapter(mLampAdapter);
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

    @Override
    public void onResume() {
        super.onResume();
        if (LoginCheckUtils.checkUserIsLogin(mContext)) {
            onLastMessage();
        } else {
            tvUnread.setVisibility(View.GONE);
        }
    }

    private void onLastMessage() {
        Map<String, Object> params = new HashMap<>();
        params.put("size", "" + Constants.PAGE_SIZE);
        params.put("type", "" + 1);
        HttpUtils.messageList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                LastMessageBean bean = JSONUtils.jsonString2Bean(response, LastMessageBean.class);
                if (bean != null) {
                    tvUnread.setVisibility(View.VISIBLE);
                    if (bean.getCount() > 0 && bean.getCount() <= 99) {
                        tvUnread.setText("" + bean.getCount());
                    } else if (bean.getCount() > 99) {
                        tvUnread.setText("99+");
                    } else {
                        tvUnread.setVisibility(View.GONE);
                    }
                } else {
                    tvUnread.setVisibility(View.GONE);
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

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 1;
            //获取轮播图
            getBanner();
            if (mSelectType == 1) {
                getLawFirm();
            } else {
                getLawyer();
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            if (mSelectType == 1) {
                getLawFirm();
            } else {
                getLawyer();
            }
        });
    }
}
