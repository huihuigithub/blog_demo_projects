package com.znh.hook.host;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;

/**
 * Created by znh on 2020/5/5
 */
public class HookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //应用启动加载插件
        loadPlugin();
    }

    /**
     * 加载插件文件，这里是直接从assets里去拿，实际应用中可以从服务器上下载
     */
    public void loadPlugin() {
        //在App的私有目录下创建插件存储目录和插件apk文件
        //targetFilePath:/data/user/0/com.znh.plugin.demo/app_hui_plugin/plugin.apk
        String pluginFileName = "plugin.apk";
        String targetFilePath = getDir("hui_plugin", MODE_PRIVATE).getAbsolutePath() + File.separator + pluginFileName;

        //从assets中复制插件文件到应用私有目录下
        FileUtils.copyFileFromAssets(this, pluginFileName, targetFilePath);

        //加载插件
        PluginManager.getInstance().loadPluginByPath(this, targetFilePath);
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources() != null ? PluginManager.getInstance().getResources() : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return PluginManager.getInstance().getAssetManager() != null ? PluginManager.getInstance().getAssetManager() : super.getAssets();
    }
}
