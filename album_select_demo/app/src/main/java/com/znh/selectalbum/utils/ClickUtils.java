package com.znh.selectalbum.utils;

import android.content.Context;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ninghui on 2016/8/24.
 * 点击事件相关功能处理工具类
 */
public class ClickUtils {

    private static Boolean isExit = false;//记录点击状态标志

    /**
     * 双击退出程序，返回true退出程序，返回false不退出
     */
    public static boolean exitBy2Click(Context context) {
        if (!isExit) {
            isExit = true; // 准备退出
            ToastUtils.showToast("再按一次退出程序");
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            return false;
        }
        return true;
    }
}
