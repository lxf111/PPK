package com.uni.ppk.ui.home.bean;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/10/7
 * Time: 18:02
 */
public class UploadImageBean implements Serializable {

    /**
     * id : 202
     * path : http://api.suwenlawyer.com/uploads/images/91/7253468378f3161d84d664df63766b.jpeg
     * thumb :
     */

    private String id;
    private String path;
    private String thumb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
