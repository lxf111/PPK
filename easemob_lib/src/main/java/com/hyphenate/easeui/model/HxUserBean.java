package com.hyphenate.easeui.model;

import java.io.Serializable;

/**
 * @author Jay
 * @email 1136189725@qq.com  有事来邮!!!
 * @date 2019/9/5 0005
 */
public class HxUserBean implements Serializable {
    /**
     * pic : /upload/156920979145800.jpg
     * name : and
     * username : 19940561941
     * id : 33
     * is_friend : true
     */

    private String nickName;
    private String headerUrl;
    private String userId;
    private boolean is_friend;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public boolean isIs_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }
}
