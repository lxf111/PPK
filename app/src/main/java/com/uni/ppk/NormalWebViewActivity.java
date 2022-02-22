package com.uni.ppk;

import android.view.View;
import android.widget.TextView;

import com.uni.commoncore.widget.TopProgressWebView;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.config.NormalWebViewConfig;

import butterknife.BindView;
import butterknife.OnClick;

public class NormalWebViewActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.webVi_message_details)
    TopProgressWebView webViMessageDetails;
    @BindView(R.id.view_title)
    View view_title;
    private String strTitle;

    private String strUrl;
    //是否显示title
    private boolean isShowTitle = true;
    private boolean isHtmlText = false;

    @OnClick(R.id.rl_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void initData() {
        strUrl = getIntent().getStringExtra(NormalWebViewConfig.URL);
//        strUrl = "http://live.wasu.cn/show/id/17659";
        strTitle = getIntent().getStringExtra(NormalWebViewConfig.TITLE);
        isShowTitle = getIntent().getBooleanExtra(NormalWebViewConfig.IS_SHOW_TITLE,true);
        isHtmlText = getIntent().getBooleanExtra(NormalWebViewConfig.IS_HTML_TEXT,false);

        if(isShowTitle){
            view_title.setVisibility(View.VISIBLE);
            centerTitle.setText(strTitle+"");
        }else{
            view_title.setVisibility(View.GONE);
        }
        if (!isHtmlText) {
            webViMessageDetails.loadUrl(strUrl);
        } else {
            webViMessageDetails.loadTextContent(strUrl);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_webview;
    }
}

