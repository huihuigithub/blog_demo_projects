package com.znh.customcamera;

import android.app.Application;
import android.content.Context;

/**
 * Created by znh on 2017/3/15.
 */

public class CameraApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
