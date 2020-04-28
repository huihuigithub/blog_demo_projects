package com.znh.sharedemo.model;


/**
 * Created by znh on 2017/6/16.
 * 封装分享参数
 */

public class ShareModel {

    //分享标题
    private String title;

    //分享内容
    private String text;

    //分享链接
    private String url;

    //分享图片链接
    private String imgUrl;

    public ShareModel() {
    }

    public ShareModel(String title, String text, String url, String imgUrl) {
        this.title = title;
        this.text = text;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
