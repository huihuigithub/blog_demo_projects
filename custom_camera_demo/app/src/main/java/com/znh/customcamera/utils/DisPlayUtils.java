package com.znh.customcamera.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.view.WindowManager;

import com.znh.customcamera.CameraApplication;


/**
 * 作者：znh on 2017/3/15 09:55
 * <p>
 * 获取设备的相关参数
 */
public class DisPlayUtils {
    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) CameraApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHight() {
        WindowManager wm = (WindowManager) CameraApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 判断Android当前的屏幕是横屏还是竖屏,竖屏返回true
    public static boolean isVerticalScreen() {
        return CameraApplication.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
