
package com.znh.sharedemo.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.znh.sharedemo.ShareApplication;

public class ToastUtil {

    /**
     * 显示在屏幕中间
     * @param info
     */
    public static void showCenterToast(String info){
        Toast toast= Toast.makeText(ShareApplication.getContext(), info, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(String info) {
        Toast.makeText(ShareApplication.getContext(), info, Toast.LENGTH_LONG).show();
    }

}
