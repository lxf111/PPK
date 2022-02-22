package com.uni.ppk.utils;

import android.app.Activity;

import com.uni.ppk.ui.message.bean.MessageUserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/11/17
 * Time: 16:32
 */
public class SaveUserUtils {
    private SpSaveListUtils mSpUtils;
    //历史记录类型
    private String mSpTag = "lawyer_user";

    public SaveUserUtils(Activity activity) {
        this.mSpUtils = new SpSaveListUtils(activity, mSpTag);
    }

    /**
     * 获取搜索历史
     */
    public void getHistorySearch(MessageUserBean bean) {
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
    public void saveSearchHistory(MessageUserBean keyWord) {
        List<MessageUserBean> lastList = mSpUtils.getDataList(mSpTag);
        if (lastList == null) {
            lastList = new ArrayList<>();
        }
        //过滤相同的关键词
        for (int i = 0; i < lastList.size(); i++) {
            if (keyWord.equals(lastList.get(i)))
                return;
        }
        //最多10条历史记录
//        if (lastList.size() >= 10) {
//            lastList.remove(lastList.size() - 1);
//        }
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
    public void refreshHistory(MessageUserBean keyWord) {
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
