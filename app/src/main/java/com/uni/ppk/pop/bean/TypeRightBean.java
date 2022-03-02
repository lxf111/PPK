package com.uni.ppk.pop.bean;

import java.io.Serializable;

/**
 * @ClassName TypeRightBean
 * @Description TODO
 * @Author LXF
 * @Date 2022/3/2 21:36
 * @Version 1.0
 */
public class TypeRightBean implements Serializable {
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
