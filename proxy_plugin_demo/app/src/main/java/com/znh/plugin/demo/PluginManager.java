package com.znh.plugin.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by znh on 2020-04-17
 * <p>
 * 插件加载管理类
 */
public class PluginManager {

    private static PluginManager mInstance = new PluginManager();

    private PackageInfo packageInfo;

    private DexClassLoader dexClassLoader;

    private Resources resources;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        return mInstance;
    }

    /**
     * 根据路径加载插件
     *
     * @param context
     */
    public void loadPluginByPath(Context context, String pluginFilePath) {

        //获取插件的包信息
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(pluginFilePath, PackageManager.GET_ACTIVITIES);

        //创建加载插件的DexClassLoader
        File fileDex = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(pluginFilePath, fileDex.getAbsolutePath(), null, context.getClassLoader());

        //创建Resource对象
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, pluginFilePath);

            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }
}
