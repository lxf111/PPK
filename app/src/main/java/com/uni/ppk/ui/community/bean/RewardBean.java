package com.uni.ppk.ui.community.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/15
 * Time: 15:53
 */
public class RewardBean implements Serializable {
    private boolean isSelect;
    /**
     * reward : 0
     * name : 免费
     */

    private String reward;
    private String name;
    private boolean isInput;

    public boolean isInput() {
        return isInput;
    }

    public void setInput(boolean input) {
        isInput = input;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getReward() {
        return reward == null ? "" : reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
