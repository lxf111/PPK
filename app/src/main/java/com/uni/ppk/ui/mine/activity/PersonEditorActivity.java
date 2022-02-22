package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.SoftInputUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.home.bean.UploadImageBean;
import com.uni.ppk.ui.mine.bean.PersonDataBean;
import com.uni.ppk.utils.PhotoSelectSingleUtile;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 编辑个人资料
 */
public class PersonEditorActivity extends BaseActivity {
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
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_motto)
    EditText edtMotto;
    @BindView(R.id.tv_gail)
    TextView tvGail;
    @BindView(R.id.tv_male)
    TextView tvMale;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private List<String> mSexList = new ArrayList<>();//性别的item

    private String mSex = "0";//性别 1男 2女
    private String mName = "";//昵称
    private String mHeader = "";//头像

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private PersonDataBean mDataBean;

    private TimePickerView pvTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_editor;
    }

    @Override
    protected void initData() {
        initTitle("" + getString(R.string.person_data));
        //初始化生日选择器
        initTimeSelect();
        getData();
    }

    //获取用户资料
    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        HttpUtils.mineData(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mDataBean = JSONUtils.jsonString2Bean(response, PersonDataBean.class);
                //sex 男1，女：2，未知：0
                if (mDataBean != null) {
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDataBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                    edtName.setText("" + mDataBean.getUser_nickname());
                    edtMotto.setText("" + mDataBean.getMotto());
                    tvBirth.setText("" + mDataBean.getBirthday().replace("00:00:00", ""));
                    mSex = "" + mDataBean.getSex();
                    if (mDataBean.getSex() == 1) {
                        //男
                        tvMale.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);
                        tvGail.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);

                        tvMale.setTextColor(mContext.getResources().getColor(R.color.theme));
                        tvGail.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                    } else if (mDataBean.getSex() == 2) {
                        //女
                        tvMale.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);
                        tvGail.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);

                        tvMale.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                        tvGail.setTextColor(mContext.getResources().getColor(R.color.theme));
                    } else {
                        //未知
                        tvMale.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);
                        tvGail.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);

                        tvMale.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                        tvGail.setTextColor(mContext.getResources().getColor(R.color.color_666666));
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
                    if (mSelectList.size() > 0) {
                        ImageUtils.getPic(MyApplication.selectPhotoShow(mContext, mSelectList.get(0)), ivHeader, mContext, R.mipmap.ic_default_header);
                    }
                    uploadImg();
                    break;
            }
        }
    }

    /**
     * 保存
     */
    private void submit() {
        mName = edtName.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            toast("请输入昵称");
            return;
        }
        if ("0".equals(mSex)) {
            toast("请选择性别");
            return;
        }
        String birth = tvBirth.getText().toString().trim();
        String motto = edtMotto.getText().toString().trim();
//        if (StringUtils.isEmpty(birth)) {
//            ToastUtils.show(mContext, "请选择生日");
//            return;
//        }
        Map<String, Object> params = new HashMap<>();
        params.put("user_nickname", "" + mName);
        params.put("head_img", "" + mHeader);
        params.put("motto", "" + motto);
        params.put("sex", "" + mSex);
        params.put("birthday", "" + birth);
        HttpUtils.updateData(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                finish();
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

    /**
     * 图片上传
     */
    private void uploadImg() {
        HttpUtils.uploadPhoto(mContext, mSelectList, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                List<UploadImageBean> imageBeans = JSONUtils.jsonString2Beans(response, UploadImageBean.class);
                if (imageBeans != null && imageBeans.size() > 0) {
                    mHeader = "" + imageBeans.get(0).getId();
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

    @OnClick({R.id.rl_header, R.id.tv_gail, R.id.tv_male, R.id.tv_birth, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //头像
            case R.id.rl_header:
                // 进入相册
                PhotoSelectSingleUtile.selectSinglePhoto(mContext, mSelectList, PictureConfig.CHOOSE_REQUEST);
                break;
            //女
            case R.id.tv_gail:
                tvGail.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);
                tvMale.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);

                tvGail.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvMale.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                mSex = "2";
                break;
            //男
            case R.id.tv_male:
                mSex = "1";
                tvMale.setBackgroundResource(R.drawable.shape_white_border_theme_radius2);
                tvGail.setBackgroundResource(R.drawable.shape_border_e6e6e6_radius2);

                tvMale.setTextColor(mContext.getResources().getColor(R.color.theme));
                tvGail.setTextColor(mContext.getResources().getColor(R.color.color_666666));
                break;
            //生日
            case R.id.tv_birth:
                SoftInputUtils.hideSoftInput(mContext);
                pvTime.show();
                break;
            //保存
            case R.id.tv_save:
                submit();
                break;
        }
    }

    /**
     * 时间选择器
     */
    private void initTimeSelect() {
        //时间选择器
        Calendar calendar = Calendar.getInstance();  //获取当前时间
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar startDate = Calendar.getInstance();
        startDate.set(year - 100, month, day);
        //时间选择器
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvBirth.setText(fmortData(date));
            }
        }).setDate(calendar).setRangDate(startDate, calendar).build();
    }

    /**
     * 时间格式转换
     *
     * @param date
     * @return
     */
    private String fmortData(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
