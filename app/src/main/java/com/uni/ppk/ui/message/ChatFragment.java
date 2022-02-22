package com.uni.ppk.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.uni.commoncore.utils.LogUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.MainActivity;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.message.activity.VideoCallActivity;
import com.uni.ppk.ui.message.activity.VoiceCallActivity;
import com.uni.ppk.ui.message.bean.MessageUserBean;
import com.uni.ppk.utils.SpSaveListUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowImage;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EasyUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/7/30
 * Time: 13:59
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper, EaseChatRowImage.ShowBigImageListener {

    private static final int VIDEO = 6;//视频
    private static final int VOICE = 7;//音频
    private static final int CAMERA = 12;//拍照
    private static final int AGENT = 8;//委托代理
    private static final int END = 9;//结束
    private static final int RENEW = 10;//续费
    private static final int SIGN_UP = 11;//签约付费

    private String mGoodsName = "";
    private String mGoodsPrice = "";
    private String mGoodsImg = "";
    private String mGoodsId = "";
    private String mGoodsType = "";

    private ImageView ivSelectPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EaseChatRowImage.setShowBigImageListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoodsType = getArguments().getString("goodsType");
        ivSelectPhoto = getView().findViewById(R.id.iv_photo);
        if (!StringUtils.isEmpty(mGoodsType)) {
            mGoodsName = getArguments().getString("goodsName");
            mGoodsPrice = getArguments().getString("goodsPrice");
            mGoodsImg = getArguments().getString("goodsImg");
            mGoodsId = getArguments().getString("goodsId");
            try {
                JSONObject object = new JSONObject();
                object.put("em_goods_name", "" + mGoodsName);
                object.put("em_goods_id", "" + mGoodsId);
                object.put("em_goods_price", "" + mGoodsPrice);
                object.put("em_goods_type", "" + mGoodsType);
                object.put("em_goods_thumb", "" + mGoodsImg);
                EMMessage emMessage = EMMessage.createTxtSendMessage("[商品]", conversation.conversationId());
                emMessage.setAttribute("em_mine_type", "1");
                emMessage.setAttribute("em_mine_body", object);
                LogUtils.e("TAG", "9999=" + emMessage.ext().get("em_mine_body"));
                sendMessage(emMessage);
                emMessage.ext().clear();
                messageList.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ivSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BottomMenu show = BottomMenu.show((AppCompatActivity) getActivity(), new String[]{"拍照", "相册选取"}, (text, index) -> {
//                    if (index == 0) {
//                        //拍照
//                        selectPicFromCamera();
//                    } else {
//                        //相册选取
//
//                    }
//                });
//                TextInfo cancelInfo = new TextInfo();
//                cancelInfo.setFontColor(R.color.color_333333);
//                show.setMenuTextInfo(cancelInfo);
//                TextInfo menuInfo = new TextInfo();
//                //menuInfo.setFontColor(Color.rgb(0x33, 51, 51));
//                menuInfo.setFontColor(getActivity().getResources().getColor(R.color.theme));
//                show.setCancelButtonTextInfo(menuInfo);
//                show.refreshView();
//                show.show();
                selectPicFromLocal();
            }
        });
    }

    private String mHeader = "";
    private String mName = "";

    @Override
    protected void setUpView() {
//        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
//            @Override
//            public EaseUser getUser(String username) {
//                EaseUser user = new EaseUser(username);
////                if (username.equals(conversation.conversationId())) {
////                    user.setAvatar(NetUrlUtils.createPhotoUrl(mHeader));
////                    user.setNickname(mName);
////                } else
//                if (username.equals("" + MyApplication.mPreferenceProvider.getUId())) {
//                    user.setAvatar(NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getPhoto()));
//                    user.setNickname(MyApplication.mPreferenceProvider.getUserName());
//                }
//                return user;
//            }
//        });
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                EaseUser user = new EaseUser(username);
                if (getActivity() != null) {
                    SpSaveListUtils mSpUtils = new SpSaveListUtils(getActivity(), "lawyer_user");
                    List<MessageUserBean> beans = mSpUtils.getDataList("lawyer_user");
                    if (beans != null && beans.size() > 0) {
                        for (int i = 0; i < beans.size(); i++) {
                            if (username.equals(beans.get(i).getId())) {
                                user.setAvatar(beans.get(i).getHeader());
                                user.setNickname(beans.get(i).getName());
                            }
                        }
                    }
                }
                if (username.equals(MyApplication.mPreferenceProvider.getUId())) {
                    user.setAvatar(NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getPhoto()));
                    user.setNickname(MyApplication.mPreferenceProvider.getUserName());
                }
                if (username.equals(MyApplication.mPreferenceProvider.getKFAccount())) {
                    user.setAvatar(NetUrlUtils.createPhotoUrl(MyApplication.mPreferenceProvider.getKFHeader()));
                    user.setNickname(MyApplication.mPreferenceProvider.getKFName());
                }
                return user;
            }
        });
        super.setUpView();
        setChatFragmentListener(this);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
    }

    @Override
    protected void registerExtendMenuItem() {
        super.registerExtendMenuItem();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //商品em_goods_type  0常规商品 1秒杀商品  2特价商品
//        if (!message.ext().isEmpty()) {
//            EaseGoodsBean goodsBean = JSONUtils.jsonString2Bean(message.ext().get("em_mine_body").toString(), EaseGoodsBean.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("id", "" + goodsBean.getEm_goods_id());
//            if ("1".equals(goodsBean.getEm_goods_type())) {
//                bundle.putString("orderType", "4");
//            } else if ("2".equals(goodsBean.getEm_goods_type())) {
//                bundle.putString("orderType", "6");
//            } else {
//                bundle.putString("orderType", "1");
//            }
//        }
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            //视频
            case VIDEO:
                startVideoCall();
                break;
            //拍照
            case CAMERA:
                selectPicFromCamera();
                break;
            //语音
            case VOICE:
                startVoiceCall();
                break;
            //委托代理
            case AGENT:
                break;
            //结束
            case END:
                break;
            //续费
            case RENEW:
                break;
            //签约付费
            case SIGN_UP:
                break;
        }
        return false;
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class)
                    .putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            inputMenu.hideExtendMenuContainer();
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

    @Override
    public void showBigImage(String[] imgUrls, String currentImg) {

    }

}
