package com.znh.selectalbum.application;

import android.app.Application;
import android.content.Context;


import org.xutils.x;

/**
 * Created by ninghui on 2016/8/24.
 * 整个app的Application，启动时做一些初始化工作
 */
public class ProjiectApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        //初始化xutils
        x.Ext.init(this);
    }

    public static Context getmContext() {
        return mContext;
    }
}
