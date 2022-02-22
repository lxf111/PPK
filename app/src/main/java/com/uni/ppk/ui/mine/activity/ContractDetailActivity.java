package com.uni.ppk.ui.mine.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.commoncore.widget.TopProgressWebView;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/24
 * Time: 9:08
 * 合约详情
 */
public class ContractDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.wv_view)
    TopProgressWebView wvView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_detail;
    }

    @Override
    protected void initData() {
        initTitle("合约详情");
        tvName.setText("客户姓名：" + getIntent().getStringExtra("name"));
        tvPhone.setText("联系方式：" + getIntent().getStringExtra("phone"));
        tvAddress.setText("原告户籍所在地：" + getIntent().getStringExtra("address"));
        getAgree();
    }

    private void getAgree(){
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "9");
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                wvView.loadTextContent("" + content);
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
