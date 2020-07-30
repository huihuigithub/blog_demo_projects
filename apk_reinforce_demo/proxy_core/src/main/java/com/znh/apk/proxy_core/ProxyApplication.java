package com.znh.apk.proxy_core;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

/**
 * Created by znh on 2020/5/15
 */
public class ProxyApplication extends Application {

    private String app_name;
    private String app_version;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = applicationInfo.metaData;
            if (metaData.containsKey("app_name")) {
                app_name = metaData.getString("app_name");
            }
            if (metaData.containsKey("app_version")) {
                app_version = metaData.getString("app_version");
            }

            //打包出来的apk路径
            String apkDir = applicationInfo.sourceDir;
            Log.e("znh", "apkDir:" + apkDir);

            //dex的解压目录
            File targetDir = getDir(app_name + "_" + app_version + File.separator + "dexDir", MODE_PRIVATE);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
