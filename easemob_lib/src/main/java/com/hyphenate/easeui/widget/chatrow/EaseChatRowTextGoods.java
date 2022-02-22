package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.widget.emojicon.EaseGoodsBean;
import com.hyphenate.helpdesk.R;

import java.util.List;

public class EaseChatRowTextGoods extends EaseChatRow {

    private TextView contentView;
    private TextView tvPrice;
    private ImageView ivImg;

    public EaseChatRowTextGoods(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_goods : R.layout.ease_row_sent_goods, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_title);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        ivImg = (ImageView) findViewById(R.id.iv_img);
    }

    @Override
    public void onSetUpView() {
        if ("1".equals(message.ext().get("em_mine_type"))) {
            //商品
            EaseGoodsBean goodsBean = JSONUtils.jsonString2Bean(message.ext().get("em_mine_body").toString(), EaseGoodsBean.class);
            contentView.setText("" + goodsBean.getEm_goods_name());
            tvPrice.setText("¥" + goodsBean.getEm_goods_price());
            ImageUtils.getPic(createPhotoUrl(goodsBean.getEm_goods_thumb()), ivImg, context, R.drawable.ic_default_wide);
        }
    }

    /**
     * 图片地址拼接
     *
     * @param srcUrl
     * @return
     */
    public static String createPhotoUrl(String srcUrl) {
        if (StringUtils.isEmpty(srcUrl)) {
            return "";
        }
        if (srcUrl.startsWith("http")) {
            return srcUrl;
        } else {
            return "https://www.chaopin100.cn/chaopin-boot/" + srcUrl;
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            List<String> userList = EaseDingMessageHelper.get().getAckUsers(message);
            int count = userList == null ? 0 : userList.size();
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
