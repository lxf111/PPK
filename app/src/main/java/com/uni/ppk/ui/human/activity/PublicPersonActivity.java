package com.uni.ppk.ui.human.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.entity.LocalMedia;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.adapter.GridImageAdapter;
import com.uni.ppk.utils.PhotoSelectSingleUtile;
import com.uni.ppk.widget.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName PublicPersonActivity
 * @Description TODO
 * @Author LXF
 * @Date 2022/3/2 21:01
 * @Version 1.0
 * 发布招工
 */
public class PublicPersonActivity extends BaseActivity {
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
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private GridImageAdapter mPhotoAdapter;

    private List<LocalMedia> mSelectList = new ArrayList<>();

    private String mPhoto = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_person;
    }

    @Override
    protected void initData() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvPhoto.setLayoutManager(manager);
        mPhotoAdapter = new GridImageAdapter(mContext, onAddPicClickListener);
        mPhotoAdapter.setSelectMax(9);
        mPhotoAdapter.setList(mSelectList);
        rvPhoto.setAdapter(mPhotoAdapter);
    }

    @OnClick({R.id.tv_type, R.id.tv_city, R.id.tv_code, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            //工种
            case R.id.tv_type:
                break;
            //城市
            case R.id.tv_city:
                break;
            //获取验证码
            case R.id.tv_code:
                break;
            //发布
            case R.id.tv_submit:
                break;
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PhotoSelectSingleUtile.selectPhoto(mContext, mSelectList, 9);
        }
    };

}
