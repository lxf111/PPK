package com.uni.ppk.ui.message.activity;

import android.widget.TextView;

import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.message.bean.MessageBean;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/12/15
 * Time: 9:19
 * 消息详情
 */
public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private MessageBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initData() {
        initTitle("消息详情");
        mBean = (MessageBean) getIntent().getSerializableExtra("bean");
        if (mBean != null) {
            tvContent.setText("" + mBean.getContent());
            tvType.setText("" + mBean.getTitle());
            tvTime.setText("" + mBean.getCreate_time());
        }
    }

}
