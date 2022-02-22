package com.uni.ppk.ui.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.ppk.MyApplication;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.ui.message.activity.ChatActivity;
import com.uni.ppk.ui.message.bean.ChatMessageBean;
import com.uni.ppk.ui.message.bean.MessageUserBean;
import com.uni.ppk.utils.EaseMobHelper;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.utils.SpSaveListUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.HxUserBean;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/7/31
 * Time: 15:57
 */
public class ConversationListFragment extends EaseConversationListFragment {

    private List<HxUserBean> mHxUserBean = new ArrayList<>();//环信用户信息表

    //保存历史记录工具类
    private SpSaveListUtils mSpUtils;
    //历史记录类型
    private String mSpTag = "lawyer_user";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    MessageUserBean bean = new MessageUserBean();
                    bean.setId("" + message.conversationId());
//                    if (message.conversationId().startsWith("kefu")) {
//                        //删除和某个user会话，如果需要保留聊天记录，传false
//                        EMTextMessageBody body = (EMTextMessageBody) message.getBody();
//                        if ("会话已结束".equals(body.getMessage())) {
//                            AppManager.getInstance().finishActivity(ChatActivity.class);
//                            EMClient.getInstance().chatManager().deleteConversation(message.conversationId(), false);
//                        }
//                        return;
//                    }
                    try {
                        if (!StringUtils.isEmpty(message.getStringAttribute(EaseConstant.EXTRA_USER_MY_NAME))
                                || !"null".equals(message.getStringAttribute(EaseConstant.EXTRA_USER_MY_NAME))) {
                            bean.setHeader("" + message.getStringAttribute(EaseConstant.EXTRA_USER_MY_HEAD));
                            bean.setName("" + message.getStringAttribute(EaseConstant.EXTRA_USER_MY_NAME));
                            getHistorySearch(bean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (LoginCheckUtils.checkUserIsLogin(getActivity())) {
                    getHxNicknameList();
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        });
    }

    @Override
    protected void getHxNicknameList() {
        super.getHxNicknameList();
        conversationListView.setListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                if (userBeanHashMap != null && userBeanHashMap.get(conversation.conversationId()) != null) {
                    EaseMobHelper.callChatIM(getActivity(), ChatActivity.class,
                            "" + userBeanHashMap.get(conversation.conversationId()).getNickName(),
                            "" + conversation.conversationId(), true);
                } else {
                    EaseMobHelper.callChatIM(getActivity(), ChatActivity.class,
                            "客服",
                            "" + conversation.conversationId(), true);
                }
            }
        });
        if (mHxIdSet.size() == 0) {
            //没有消息
            initUserNickname();
            return;
        }
//        Iterator<String> iterator2 = mHxIdSet.iterator();
//        while (iterator2.hasNext()) {
//            String name = iterator2.next();
//            if (name.startsWith("kefu")) {
//                //删除和某个user会话，如果需要保留聊天记录，传false
//                EMClient.getInstance().chatManager().deleteConversation(name, false);
//                iterator2.remove();
//            }
//        }
        mSpUtils = new SpSaveListUtils(getActivity(), mSpTag);
        List<MessageUserBean> lastList = mSpUtils.getDataList(mSpTag);
        if (mHxIdSet.size() > lastList.size()) {
            Iterator<String> iterator = mHxIdSet.iterator();
            while (iterator.hasNext()) {
                getChatInfo(iterator.next());
            }
            return;
        }
        if (lastList == null || lastList.size() == 0) {
            //有消息，但是没获取到用户信息 这时候不显示聊天
            return;
        }
        handler.sendEmptyMessage(0);
//        conversationListView.setUserName(mHxUserBean);
//        StringBuffer sb = new StringBuffer();
//        Iterator<String> iterator = mHxIdSet.iterator();
//        while (iterator.hasNext()) {
//            sb.append(iterator.next());
//            sb.append(",");
//        }
//        LogUtils.e("TAG", "sb=" + sb.toString());
//        BaseOkHttpClient.newBuilder()
//                .addParam("HxId", sb.toString().substring(0, sb.toString().length() - 1))
//                .get()
//                .url(NetUrlUtils.MESSAGE_IM_USER_INFO)
//                .build().enqueue(getActivity(), new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String s, String msg) {
//                if (StringUtils.isEmpty(s)) {
//                    return;
//                }
//                mHxUserBean = JSONUtils.jsonString2Beans(s, HxUserBean.class);
//                userBeanHashMap = new HashMap<>();
//                HxUserBean tempBean = new HxUserBean();
//                tempBean.setAvatar(MyApplication.mPreferenceProvider.getPhoto());
//                tempBean.setShopName(MyApplication.mPreferenceProvider.getUserName());
//                tempBean.setId(MyApplication.mPreferenceProvider.getUId());
//                userBeanHashMap.put(tempBean.getId(), tempBean);
//                for (int i = 0; i < mHxUserBean.size(); i++) {
//                    userBeanHashMap.put("" + mHxUserBean.get(i).getHxId(), mHxUserBean.get(i));
//                }
//                initUserNickname();
//                EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
//                    @Override
//                    public EaseUser getUser(String username) {
//                        EaseUser user = new EaseUser(username);
//                        if (userBeanHashMap.get(username) != null) {
//                            user.setAvatar(NetUrlUtils.createPhotoUrl(userBeanHashMap.get(username).getAvatar()));
//                            user.setNickname(userBeanHashMap.get(username).getShopName());
//                        } else {
////                            user.setAvatar(NetUrlUtils.createPhotoUrl(""));
////                            user.setNickname("客服");
//                        }
//                        return user;
//                    }
//                });
//                initUserNickname();
//                conversationListView.setUserName(mHxUserBean);
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//        });
    }

    private void getChatInfo(String uid) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", "" + uid);
        HttpUtils.getChatInfo(getActivity(), params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ChatMessageBean bean = JSONUtils.jsonString2Bean(response, ChatMessageBean.class);
                if (bean != null) {
                    MessageUserBean userBean = new MessageUserBean();
                    userBean.setName(bean.getUser_nickname());
                    userBean.setHeader(bean.getHead_img());
                    userBean.setId("" + bean.getId());
                    getHistorySearch(userBean);
                    if (LoginCheckUtils.checkUserIsLogin(getActivity())) {
                        handler.sendEmptyMessage(0);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mHxUserBean = new ArrayList<>();
                    mSpUtils = new SpSaveListUtils(getActivity(), mSpTag);
                    List<MessageUserBean> lastList = mSpUtils.getDataList(mSpTag);
                    if (lastList == null || lastList.size() == 0) {
                        //有消息，但是没获取到用户信息
                        initUserNickname();
                        return;
                    }
                    for (int i = 0; i < lastList.size(); i++) {
                        HxUserBean bean = new HxUserBean();
                        bean.setHeaderUrl("" + lastList.get(i).getHeader());
                        bean.setUserId("" + lastList.get(i).getId());
                        bean.setNickName("" + lastList.get(i).getName());
                        mHxUserBean.add(bean);
                    }
                    userBeanHashMap = new HashMap<>();
                    HxUserBean tempBean = new HxUserBean();
                    tempBean.setHeaderUrl(MyApplication.mPreferenceProvider.getPhoto());
                    tempBean.setNickName(MyApplication.mPreferenceProvider.getUserName());
                    tempBean.setUserId(MyApplication.mPreferenceProvider.getUId());
                    userBeanHashMap.put(tempBean.getUserId(), tempBean);
                    for (int i = 0; i < mHxUserBean.size(); i++) {
                        userBeanHashMap.put("" + mHxUserBean.get(i).getUserId(), mHxUserBean.get(i));
                    }
                    initUserNickname();
                    EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
                        @Override
                        public EaseUser getUser(String username) {
                            EaseUser user = new EaseUser(username);
                            if (userBeanHashMap.get(username) != null) {
                                user.setAvatar(NetUrlUtils.createPhotoUrl(userBeanHashMap.get(username).getHeaderUrl()));
                                user.setNickname(userBeanHashMap.get(username).getNickName());
                            }
                            return user;
                        }
                    });
                    initUserNickname();
                    conversationListView.setUserName(mHxUserBean);
                    break;
            }
        }
    };

    /**
     * 获取搜索历史
     */
    private void getHistorySearch(MessageUserBean bean) {
        List<MessageUserBean> lastList = mSpUtils.getDataList(mSpTag);
        if (lastList == null || lastList.size() <= 0) {
            saveSearchHistory(bean);
            return;
        }
        for (int i = 0; i < lastList.size(); i++) {
            if (!bean.getId().equals(lastList.get(i).getId())) {
                //没存的存起来
                saveSearchHistory(bean);
            } else {
                refreshHistory(bean);
            }
        }
    }

    /**
     * 保存历史搜索记录
     *
     * @param keyWord
     */
    private void saveSearchHistory(MessageUserBean keyWord) {
        List<MessageUserBean> lastList = mSpUtils.getDataList(mSpTag);
        if (lastList == null) {
            lastList = new ArrayList<>();
        }
        //过滤相同的关键词
        for (int i = 0; i < lastList.size(); i++) {
            if (keyWord.equals(lastList.get(i)))
                return;
        }
        List<MessageUserBean> historyList = new ArrayList<>();
        historyList.add(keyWord);
        historyList.addAll(lastList);
        mSpUtils.setDataList(mSpTag, historyList);
    }

    /**
     * 刷新用户信息
     *
     * @param keyWord
     */
    private void refreshHistory(MessageUserBean keyWord) {
        List<MessageUserBean> lastList = mSpUtils.getDataList(mSpTag);
        if (lastList == null) {
            lastList = new ArrayList<>();
        }
        //过滤相同的关键词
        for (int i = 0; i < lastList.size(); i++) {
            if (keyWord.getId().equals(lastList.get(i).getId())) {
                lastList.get(i).setHeader(keyWord.getHeader());
                lastList.get(i).setName(keyWord.getName());
            }
        }
        List<MessageUserBean> historyList = new ArrayList<>();
        historyList.addAll(lastList);
        mSpUtils.setDataList(mSpTag, historyList);
    }

}
