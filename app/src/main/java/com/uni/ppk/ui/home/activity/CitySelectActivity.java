package com.uni.ppk.ui.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.RxBus;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.StyledDialogUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.adapter.AddressListAdapter;
import com.uni.ppk.ui.home.adapter.CityLabelAdapter;
import com.uni.ppk.ui.home.adapter.CityListAdapater;
import com.uni.ppk.ui.home.bean.AddressListBean;
import com.uni.ppk.ui.home.bean.CityBean;
import com.uni.ppk.ui.home.bean.CityListBean;
import com.uni.ppk.utils.Cn2Spell;
import com.uni.ppk.utils.PinyinUtils;
import com.uni.ppk.utils.ReadAssetsFileUtil;
import com.uni.ppk.widget.FlowLayoutManager;
import com.uni.ppk.widget.SideLetterBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @Description: 城市选择
 * @Author: longyu
 * @CreateDate: 2020/1/19 0019$ 11:00$
 * @Version: 1.0
 */
public class CitySelectActivity extends BaseActivity {

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
    //    @BindView(R.id.llyt_title)
//    LinearLayout llytTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.rlv_search_city_list)
    RecyclerView rlvSearchCityList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rlv_hot_city_list)
    RecyclerView rlvHotCityList;
    @BindView(R.id.rlv_city_list)
    RecyclerView rlvCityList;
    @BindView(R.id.slv_city)
    ScrollView slvCity;
    @BindView(R.id.side_letter_ber)
    SideLetterBar sideLetterBer;
    @BindView(R.id.tv_letter_overlay)
    TextView tvLetterOverlay;
    @BindView(R.id.tv_location_city)
    TextView tvLocationCity;
    @BindView(R.id.llyt_city)
    LinearLayout llytCity;
    @BindView(R.id.view_scroll)
    View viewScroll;
    @BindView(R.id.edt_search)
    EditText edtSearch;

    private List<CityBean> mHistoryList = new ArrayList<>();
    private CityLabelAdapter mHistoryLabelAdapter;
    private List<CityBean> mHotList = new ArrayList<>();
    private CityLabelAdapter mHotLabelAdapter;
    private List<CityListBean.ChildrenBean> mCityBeans = new ArrayList<>();
    private CityListAdapater mCityAdapter;

    private List<CityListBean.ChildrenBean> mSearchCityBeans = new ArrayList<>();

//    private String[] mHotStr = new String[]{"北京", "上海", "成都", "广州", "南京", "深圳", "武汉", "郑州"};

    private HashMap<String, Integer> letterIndexes = new HashMap<>();

    private static final int MSG_LOAD_DATA = 0x0001;//开始解析
    private static final int MSG_LOAD_SUCCESS = 0x0002;//解析成功
    private static final int MSG_LOAD_FAILED = 0x0003;//解析失败
    private Thread mThread;
    private static boolean isLoaded = false;

    private boolean isFish = false;

    private AddressListAdapter addressListAdapter;

    private String mSearch = "";

    /**
     * 启动界面
     *
     * @param activity
     */
    public static void toActivity(Activity activity) {
        Intent intent = new Intent(activity, CitySelectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_select;
    }

    @Override
    protected void initData() {
        initTitle("选择城市");
        //进入界面开始解析地址文件
//        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        isFish = getIntent().getBooleanExtra("fish", false);

        tvLocation.setText("" + MyApplication.mPreferenceProvider.getCity());

        tvLocationCity.setText("" + MyApplication.mPreferenceProvider.getLocationAddress());

        tvLocationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //城市
                if (!isFish) {
                    MyApplication.mPreferenceProvider.setCity(MyApplication.mPreferenceProvider.getLocationAddress());
                    RxBus.getInstance().post("refreshAddress");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("city", "" + MyApplication.mPreferenceProvider.getLocationAddress());
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    SoftInputUtils.hideSoftInput(mContext);
                    getCityList();
                    return true;
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (StringUtils.isEmpty(edtSearch.getText().toString().trim())) {
                    getCityList();
                }
            }
        });

        sideLetterBer.setOverlay(tvLetterOverlay);

        initRecylerView();
        getHotData();
        getCityList();
    }

    /**
     * 初始化
     */
    private void initRecylerView() {
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        rlvSearchCityList.setLayoutManager(flowLayoutManager);
        mHistoryLabelAdapter = new CityLabelAdapter(mContext);
        rlvSearchCityList.setAdapter(mHistoryLabelAdapter);
        mHistoryLabelAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CityBean>() {
            @Override
            public void onItemClick(View view, int position, CityBean model) {
                //城市
                if (!isFish) {
                    if (model.getName().indexOf(",") != -1) {
                        MyApplication.mPreferenceProvider.setCity(model.getName().substring(0, model.getName().indexOf(",")));
                        MyApplication.mPreferenceProvider.setCityCode(model.getName().substring(model.getName().indexOf(",") + 1, model.getName().length()));
                    } else {
                        MyApplication.mPreferenceProvider.setCity(model.getName());
                        MyApplication.mPreferenceProvider.setCityCode("" + model.getId());
                    }
                    RxBus.getInstance().post("refreshAddress");
                } else {
                    Intent intent = new Intent();
                    if (model.getName().indexOf(",") != -1) {
                        intent.putExtra("city", "" + model.getName().substring(0, model.getName().indexOf(",")));
                    } else {
                        intent.putExtra("city", "" + model.getName());
                    }
                    setResult(RESULT_OK, intent);
                }
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position, CityBean model) {

            }
        });

        FlowLayoutManager flowLayoutManager2 = new FlowLayoutManager();
        rlvHotCityList.setLayoutManager(flowLayoutManager2);
        mHotLabelAdapter = new CityLabelAdapter(mContext);
        rlvHotCityList.setAdapter(mHotLabelAdapter);
        mHotLabelAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CityBean>() {
            @Override
            public void onItemClick(View view, int position, CityBean model) {
                //城市
                if (!isFish) {
                    MyApplication.mPreferenceProvider.setCity("" + model.getName());
                    MyApplication.mPreferenceProvider.setCityCode("" + model.getId());
                    RxBus.getInstance().post("refreshAddress");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("city", "" + model.getName());
                    setResult(RESULT_OK, intent);
                }
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position, CityBean model) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rlvCityList.setLayoutManager(layoutManager);
        addressListAdapter = new AddressListAdapter(mContext);
        rlvCityList.setAdapter(addressListAdapter);
        addressListAdapter.setOnAddressCityOnclick(new AddressListAdapter.OnAddressCityOnclick() {
            @Override
            public void setOnCityCallback(AddressListBean.CitysBean mBean, int firstPosition, int secondPosition) {
                //城市
                if (!isFish) {
                    MyApplication.mPreferenceProvider.setCity("" + mBean.getName());
                    MyApplication.mPreferenceProvider.setCityCode("" + mBean.getId());
                    RxBus.getInstance().post("refreshAddress");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("city", "" + mBean.getName());
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
//        mCityAdapter = new CityListAdapater(mContext);
//        rlvCityList.setAdapter(mCityAdapter);
//        mCityAdapter.setOnItemClickListener(new AFinalRecyclerViewAdapter.OnItemClickListener<CityListBean.ChildrenBean>() {
//            @Override
//            public void onItemClick(View view, int position, CityListBean.ChildrenBean model) {
//                //城市
//                if (!isFish) {
//                    MyApplication.mPreferenceProvider.setCity("" + model.getName());
//                    MyApplication.mPreferenceProvider.setCityCode("" + model.getId());
//                    RxBus.getInstance().post("refreshAddress");
//                } else {
//                    Intent intent = new Intent();
//                    intent.putExtra("city", "" + model.getName());
//                    setResult(RESULT_OK, intent);
//                }
//                finish();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position, CityListBean.ChildrenBean model) {
//
//            }
//        });

        sideLetterBer.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = getLetterPosition(letter);
                if (position == -1) {
//                    slvCity.smoothScrollTo(0, 0);
                } else {
                    LinearLayoutManager llm = (LinearLayoutManager) rlvCityList.getLayoutManager();
                    int y = viewScroll.getTop()
                            + llm.findViewByPosition(position).getTop();
                    slvCity.scrollTo(0, y);
                }
            }
        });
    }

    /**
     * 获取热门数据
     */
    private void getHotData() {
//        for (int i = 0; i < mHotStr.length; i++) {
////            CityBean cityBean = new CityBean();
////            cityBean.setId(i);
////            cityBean.setName(mHotStr[i]);
////            mHotList.add(cityBean);
////        }
        Map<String, Object> params = new HashMap<>();
        HttpUtils.addressHot(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mHotList = JSONUtils.jsonString2Beans(response, CityBean.class);
                mHotLabelAdapter.refreshList(mHotList);
            }

            @Override
            public void onError(String msg, int code) {

            }

            @Override
            public void onFail(Call call, IOException e) {

            }
        });
    }

    private void getCityList() {
        mSearch = edtSearch.getText().toString().trim();
        Map<String, Object> params = new HashMap<>();
        HttpUtils.addressList(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<AddressListBean> addressListBeans = JSONUtils.jsonString2Beans(response, AddressListBean.class);
                if (addressListBeans != null && addressListBeans.size() > 0) {
                    if (!StringUtils.isEmpty(mSearch)) {
                        List<AddressListBean> searchAddress = new ArrayList<>();
                        for (int i = 0; i < addressListBeans.size(); i++) {
                            AddressListBean addressBean = new AddressListBean();
                            List<AddressListBean.CitysBean> citysBeanList = new ArrayList<>();
                            for (int j = 0; j < addressListBeans.get(i).getCitys().size(); j++) {
                                if (addressListBeans.get(i).getCitys().get(j).getName().indexOf(mSearch) != -1) {
                                    citysBeanList.add(addressListBeans.get(i).getCitys().get(j));
                                }
                            }
                            if (citysBeanList.size() > 0) {
                                addressBean.setCitys(citysBeanList);
                                addressBean.setPinyin(addressListBeans.get(i).getPinyin());
                                searchAddress.add(addressBean);
                            }
                        }
                        addressListAdapter.refreshList(searchAddress);
                    } else {
                        addressListAdapter.refreshList(addressListBeans);
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

    /**
     * 初始化城市数据
     */
    private void initCityData() {
        StyledDialogUtils.getInstance().loading(mContext);
        String json = ReadAssetsFileUtil.getJson(this, "city.json");
        List<CityListBean> beanList = JSONUtils.jsonString2Beans(json, CityListBean.class);
        for (int i = 0; i < beanList.size(); i++) {
            List<CityListBean.ChildrenBean> children = beanList.get(i).getChildren();
            for (int j = 0; j < children.size(); j++) {
                String s = children.get(j).getName().replaceAll("   ", "");
                if ("漯河".equals(s)) {
                    children.get(j).setPinyin("luohe");
                } else if ("濮阳".equals(s)) {
                    children.get(j).setPinyin("puyang");
                } else if ("亳州".equals(s)) {
                    children.get(j).setPinyin("haozhou");
                } else if ("泸州".equals(s)) {
                    children.get(j).setPinyin("luzhou");
                } else if ("儋州".equals(s)) {
                    children.get(j).setPinyin("danzhou");
                } else if ("衢州".equals(s)) {
                    children.get(j).setPinyin("quzhou");
                } else {
                    children.get(j).setPinyin(Cn2Spell.getInstance().getSelling(s));
                }
                mCityBeans.add(children.get(j));
            }
        }
        //按照字母排序
        Collections.sort(mCityBeans, new Comparator<CityListBean.ChildrenBean>() {
            @Override
            public int compare(CityListBean.ChildrenBean city, CityListBean.ChildrenBean t1) {
                return city.getPinyin().compareTo(t1.getPinyin());
            }
        });

        for (int i = 0; i < mCityBeans.size(); i++) {
            String pinyin = mCityBeans.get(i).getPinyin();
            String currentLetter = PinyinUtils.getFirstLetter(pinyin);
            //上个首字母，如果不存在设为""
            String previousLetter = i >= 1 ? PinyinUtils.getFirstLetter(mCityBeans.get(i - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, i);
            }
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = -1;
//        Integer integer = letterIndexes.get(letter);
        for (int i = 0; i < addressListAdapter.getItemCount(); i++) {
            if (letter.equals(addressListAdapter.getItem(i).getPinyin())) {
                integer = i;
            }
        }
        return integer == null ? -1 : integer;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (mThread == null) {//如果已创建就不再重新创建子线程了
                        mThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initCityData();
                            }
                        });
                        mThread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    StyledDialogUtils.getInstance().dismissLoading();
                    mCityAdapter.appendToList(mCityBeans);
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(mContext, "解析失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHotList != null) {
            mHotList.clear();
        }
        if (mHistoryList != null) {
            mHistoryList.clear();
        }
        if (mCityBeans != null) {
            mCityBeans.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
