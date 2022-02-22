package com.uni.ppk.ui.mine.activity;

import android.content.Intent;
import android.net.Uri;
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
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 10:55
 * 关于诉问
 */
public class AboutActivity extends BaseActivity {
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
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_customer)
    TextView tvCustomer;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.wv_view)
    TopProgressWebView wvView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        initTitle("关于诉问");
        getAbout();
    }

    @OnClick(R.id.tv_customer)
    public void onViewClicked() {
        callPhone("4000087807");
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    //获取关于我们
    private void getAbout() {
        Map<String, Object> params = new HashMap<>();
        params.put("category_id", "3");
        HttpUtils.getAgree(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                if (StringUtils.isEmpty(response)) {
                    ToastUtils.show(mContext, "无此配置项");
                    return;
                }
                String title = JSONUtils.getNoteJson(response, "name");
                String content = JSONUtils.getNoteJson(response, "content");
                String img = JSONUtils.getNoteJson(response, "cat_img");
//                ImageUtils.getPic(NetUrlUtils.createPhotoUrl(img),ivImg,mContext,R.mipmap.ic_logo);
                tvExplain.setText("" + title);
                wvView.loadTextContent(content);
//                Bundle bundle = new Bundle();
//                bundle.putString(NormalWebViewConfig.URL, "" + content);
//                bundle.putString(NormalWebViewConfig.TITLE, "" + title);
//                bundle.putBoolean(NormalWebViewConfig.IS_HTML_TEXT, true);
//                MyApplication.openActivity(mContext, NormalWebViewActivity.class, bundle);
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
