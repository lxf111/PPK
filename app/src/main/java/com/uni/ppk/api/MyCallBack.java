package com.uni.ppk.api;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by maning on 16/1/13.
 * <p/>
 * 网络回调
 */
public interface MyCallBack {

    /**
     * 成功的回调对象
     *
     * @param response
     */
    void onSuccess(String response, String msg);

    void onError(String msg, int code);

    void onFail(Call call, IOException e);

}
