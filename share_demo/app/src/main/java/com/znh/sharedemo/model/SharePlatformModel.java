package com.znh.sharedemo.model;

/**
 * Created by znh on 2017/6/16.
 * 封装分享平台信息
 */

public class SharePlatformModel {
    //平台图标
    private int platformIcon;

    //对外显示的平台名称
    private String platformShowName;

    //标识平台的名称，根据该名称区分是哪个平台
    private String platformUniqeName;

    public int getPlatformIcon() {
        return platformIcon;
    }

    public void setPlatformIcon(int platformIcon) {
        this.platformIcon = platformIcon;
    }

    public String getPlatformShowName() {
        return platformShowName;
    }

    public void setPlatformShowName(String platformShowName) {
        this.platformShowName = platformShowName;
    }

    public String getPlatformUniqeName() {
        return platformUniqeName;
    }

    public void setPlatformUniqeName(String platformUniqeName) {
        this.platformUniqeName = platformUniqeName;
    }
}
