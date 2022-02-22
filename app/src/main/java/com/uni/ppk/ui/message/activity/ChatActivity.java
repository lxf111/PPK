package com.uni.ppk.ui.message.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.uni.commoncore.utils.AppManager;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.ui.message.ChatFragment;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends EaseBaseActivity {
    private static final String TAG = "ChatActivity";

    private EaseChatFragment easeChatFragment;

    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        AppManager.getInstance().addActivity(this);
        setStatusBar();
        setContentView(R.layout.em_activity_chat);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //IM服务号
            toChatUsername = bundle.getString(EaseConstant.EXTRA_USER_ID, "");
        }
        easeChatFragment = new ChatFragment();  //环信聊天界面
        easeChatFragment.setArguments(getIntent().getExtras()); //需要的参数
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, easeChatFragment).commit();  //Fragment切换
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }

    protected void setStatusBar() {
        StatusBarUtils.setStatusBarColor(this, R.color.bottom_text_color_normal);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (easeChatFragment != null) {
            easeChatFragment.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}

