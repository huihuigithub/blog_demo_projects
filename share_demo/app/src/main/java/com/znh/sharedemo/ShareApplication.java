package com.znh.sharedemo;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;

import com.znh.sharedemo.constants.ConfigConstants;

/**
 * Created by znh on 2017/8/21.
 */

public class ShareApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        MobSDK.init(this, ConfigConstants.SHARE_SDK_APP_KEY, ConfigConstants.SHARE_SDK_APP_SECRET);
    }

    public static Context getContext() {
        return sContext;
    }
}
