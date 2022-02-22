package com.uni.ppk.ui.message;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.config.Constants;
import com.uni.ppk.ui.message.activity.SystemMessageActivity;
import com.uni.ppk.ui.message.bean.LastMessageBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:18
 * 消息
 */
public class MessageFragment extends LazyBaseFragments {

    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_unread)
    TextView tvUnread;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rlyt_system)
    RelativeLayout rlytSystem;
    private EaseConversationListFragment mFragment;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_message, null);
        return mRootView;
    }

    @Override
    public void initView() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
    }

    @Override
    public void initData() {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragment = new ConversationListFragment();
        mFragmentTransaction.add(R.id.fl_message, mFragment);
        mFragmentTransaction.commit();
    }

    @OnClick(R.id.rlyt_system)
    public void onViewClicked() {
        MyApplication.openActivity(mContext, SystemMessageActivity.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginCheckUtils.checkUserIsLogin(mContext)) {
            onLastMessage();
        } else {
            tvContent.setText("");
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
                    tvContent.setText("" + bean.getLatest_title());
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

    /**
     * 刷新用户会话列表
     */
    public void refreshView() {
        if (mFragment != null) {
            mFragment.setUpView();
        }
    }
}
