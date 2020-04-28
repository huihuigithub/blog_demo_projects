package com.znh.selectalbum.utils;

import android.widget.Toast;

import com.znh.selectalbum.application.ProjiectApplication;


/**
 * Created by ninghui on 2016/8/24.
 * Toast提醒工具类
 */
public class ToastUtils {
    /**
     * 普通的toast提示
     */
    public static void showToast(String msg) {
        Toast.makeText(ProjiectApplication.getmContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
