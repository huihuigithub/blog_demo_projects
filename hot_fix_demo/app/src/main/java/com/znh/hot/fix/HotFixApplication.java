package com.znh.hot.fix;

import android.app.Application;

/**
 * Created by znh on 2020/5/4
 */
public class HotFixApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //执行热修复
        HotFixUtils.fixBug(this);
    }
}
