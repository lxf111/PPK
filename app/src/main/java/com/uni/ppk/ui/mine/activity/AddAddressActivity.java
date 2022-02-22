package com.uni.ppk.ui.mine.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.bean.JsonBean;
import com.uni.ppk.utils.GetJsonDataUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Android Studio.
 * User: whx
 * Date: 2019/11/2 0002
 * Time: 16:05
 * 新建收货地址
 */
public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tv_select_id)
    TextView tvSelectId;
    @BindView(R.id.edt_add_id)
    EditText edtAddId;
    @BindView(R.id.edt_add_name)
    EditText edtAddName;
    @BindView(R.id.edt_add_phone)
    EditText edtAddPhone;
    @BindView(R.id.st_open)
    Switch stOpen;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    private String mName;
    private String mPhone;
    private String mAddress;

    private List<JsonBean> mOptions1Items = new ArrayList<>();//添加省份数据
    private ArrayList<ArrayList<String>> mOptions2Items = new ArrayList<>();//添加城市数据
    private ArrayList<ArrayList<ArrayList<String>>> mOptions3Items = new ArrayList<>();//添加地区数据
    private String mProvince = "";//省
    private String mCity = "";//市
    private String mArea = "";//区
    private static final int MSG_LOAD_DATA = 0x0001;//开始解析
    private static final int MSG_LOAD_SUCCESS = 0x0002;//解析成功
    private static final int MSG_LOAD_FAILED = 0x0003;//解析失败
    private Thread mThread;
    private static boolean isLoaded = false;
    private String mDefaultFlag = "1";//默认地址 0否 1是

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initData() {
        initTitle("新建收货地址");
        rightTitle.setText("保存");
        rightTitle.setTextColor(Color.WHITE);

        //进入界面开始解析地址文件
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        stOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mDefaultFlag ="1";
                }else {
                    mDefaultFlag ="0";
                }
            }
        });
    }

    @OnClick({R.id.right_title, R.id.tv_select_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address:
                //选择
                if (isLoaded) {
                    SoftInputUtils.hideKeyboard(view);
                    showPickerView();
                } else {
                    Toast.makeText(mContext, "数据加载中，请稍后再试....", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.right_title:
                //保存
                saveAddress();
                break;
        }
    }

    /**
     * 保存
     */
    private void saveAddress() {
        mName = edtAddName.getText().toString();//名字
        mPhone = edtAddPhone.getText().toString();//电话号码
        mAddress = edtAddId.getText().toString();//详细地址
        if (StringUtils.isEmpty(mName)) {
            toast("请输入姓名");
            return;
        }
        if (StringUtils.isEmpty(mPhone)) {
            toast("请输入电话号码");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(mPhone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(mAddress)) {
            toast("请输入详细地址");
            return;
        }
        if ("".equals(mArea)) {
            toast("请选择城市");
            return;
        }
//        StyledDialogUtils.getInstance().loading(mContext);
//        BaseOkHttpClient.newBuilder()
//                .url(NetUrlUtils.MINE_ADD_ADDRESS)
//                .addParam("detailedAddress", "" + mAddress)
//                .addParam("reciverName", "" + mName)
//                .addParam("reciverTelephone", "" + mPhone)
//                .addParam("areap", "" + mProvince)//省
//                .addParam("areac", "" + mCity)//市
//                .addParam("areax", "" + mArea)//区
//                .addParam("defaultFlag", "" + mDefaultFlag)
//                .post()
//                .build()
//                .enqueue(mContext, new BaseCallBack<String>() {
//                    @Override
//                    public void onSuccess(String result, String msg) {
//                        StyledDialogUtils.getInstance().dismissLoading();
//                        toast(msg);
//                        setResult(RESULT_OK);
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        StyledDialogUtils.getInstance().dismissLoading();
//                        toast(msg);
//                    }
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        ToastUtils.show(mContext, getString(R.string.toast_service_error));
//                        StyledDialogUtils.getInstance().dismissLoading();
//                    }
//                });
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = mOptions1Items.size() > 0 ?
                        mOptions1Items.get(options1).getPickerViewText() : "";

                String opt2tx = mOptions2Items.size() > 0
                        && mOptions2Items.get(options1).size() > 0 ?
                        mOptions2Items.get(options1).get(options2) : "";

                String opt3tx = mOptions2Items.size() > 0
                        && mOptions3Items.get(options1).size() > 0
                        && mOptions3Items.get(options1).get(options2).size() > 0 ?
                        mOptions3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;//+ opt3tx

                mProvince = opt1tx;
                mCity = opt2tx;
                mArea = opt3tx;

                tvSelectAddress.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
//        pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(mOptions1Items, mOptions2Items, mOptions3Items);//三级选择器
//        pvOptions.setPicker(mOptions1Items, mOptions2Items);//二级选择器

        pvOptions.show();
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
                                initJsonData();
                            }
                        });
                        mThread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(mContext, "解析失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        mOptions1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            mOptions2Items.add(cityList);

            /**
             * 添加地区数据
             */
            mOptions3Items.add(province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

}
