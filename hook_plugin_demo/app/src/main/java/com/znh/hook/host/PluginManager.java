package com.znh.hook.host;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by znh on 2020/5/5
 * <p>
 * 插件加载管理类
 */
public class PluginManager {

    private volatile static PluginManager mInstance;

    private Resources mResources;

    private AssetManager mAssetManager;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        if (mInstance == null) {
            synchronized (PluginManager.class) {
                if (mInstance == null) {
                    mInstance = new PluginManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 根据路径加载插件
     *
     * @param context
     */
    public void loadPluginByPath(Context context, String pluginFilePath) {

        //合并宿主和插件的dex
        mergeDex(context, pluginFilePath);

        //创建Resource对象并管理插件的资源
        mergeResource(context, pluginFilePath);

        //通过Hook接口IActivityManager来添加自己的代码
        hookAms(context);

        //通过Hook接口mH的Callback来启动真正的Activity
        hookHandler();
    }

    /**
     * 合并宿主和插件的dex
     *
     * @param context        宿主的上下文环境
     * @param pluginFilePath 插件文件的路径
     */
    private void mergeDex(Context context, String pluginFilePath) {
        try {
            //获取宿主的dexElements(Element[]类型)
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            Object hostPathList = DexUtils.getPathList(pathClassLoader);
            Object hostDexElements = DexUtils.getDexElements(hostPathList);

            //创建加载插件的DexClassLoader
            String pluginDexPath = context.getDir("hui_plugin", Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "plugin_dex";
            DexClassLoader dexClassLoader = new DexClassLoader(pluginFilePath, pluginDexPath, null, context.getClassLoader());
            //获取插件的dexElements(Element[]类型)
            Object pluginPathList = DexUtils.getPathList(dexClassLoader);
            Object pluginDexElements = DexUtils.getDexElements(pluginPathList);

            //融合宿主和插件的dexElements
            Object newDexElements = DexUtils.combineArray(hostDexElements, pluginDexElements);

            //将新生成的newDexElements设置回去
            DexUtils.setDexElements(hostPathList, newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Resource对象并管理插件的资源
     *
     * @param pluginFilePath 插件文件路径
     */
    private void mergeResource(Context context, String pluginFilePath) {
        try {
            mAssetManager = AssetManager.class.newInstance();
            Method addAssetPath = mAssetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(mAssetManager, pluginFilePath);

            Method ensureStringBlocks = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            ensureStringBlocks.setAccessible(true);
            ensureStringBlocks.invoke(mAssetManager);

            mResources = new Resources(mAssetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Hook接口IActivityManager来添加自己的代码,从而能够控制系统的执行流程
     *
     * @param context 宿主环境的上下文
     */
    private void hookAms(final Context context) {
        try {
            //通过反射获取getService的返回值IActivityManager对象
            final Object mIActivityManager = ReflectUtils.getMethodValue(null, Class.forName("android.app.ActivityManager"),
                    "getService");

            //通过动态代理，生成IActivityManager的代理对象
            Object mIActivityManagerProxy = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{Class.forName("android.app.IActivityManager")},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            //在这里替换掉intent信息
                            if ("startActivity".equals(method.getName())) {
                                Intent intent = new Intent(context, ProxyActivity.class);
                                intent.putExtra("targetIntent", (Intent) args[2]);
                                args[2] = intent;
                            }
                            //执行系统IActivityManager对象的方法
                            return method.invoke(mIActivityManager, args);
                        }
                    });

            //1. 如果字段不是静态字段的话,要传入反射类的对象.如果传null是会报java.lang.NullPointerException
            //2. 如果字段是静态字段的话,传入任何对象都是可以的,包括null
            Object mIActivityManagerSingleton = ReflectUtils.getFieldValue(null, Class.forName("android.app.ActivityManager"), "IActivityManagerSingleton");

            //mInstance指向的就是IActivityManager对象
            //通过反射获取到Singleton类里面的mInstance字段,替换mInstance为自己的代理对象mIActivityManagerProxy
            ReflectUtils.setFieldValue(mIActivityManagerSingleton, Class.forName("android.util.Singleton"), "mInstance", mIActivityManagerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Hook接口mH的Callback来启动真正的Activity
     * mH是ActivityThread中的成员变量，是Handler类型的
     * Callback是Handler中分发消息用到的一个接口
     */
    private void hookHandler() {
        try {
            //通过反射获取到ActivityThread中的mH变量的值
            Object currentActivityThread = ReflectUtils.getMethodValue(null, Class.forName("android.app.ActivityThread"), "currentActivityThread");
            Handler mH = (Handler) ReflectUtils.getFieldValue(currentActivityThread, Class.forName("android.app.ActivityThread"), "mH");

            //通过反射获取到Handler(mH)内部的mCallback字段，将其赋值为自己的callback
            ReflectUtils.setFieldValue(mH, Handler.class, "mCallback", new HookCallback(mH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Resources getResources() {
        return mResources;
    }

    public AssetManager getAssetManager() {
        return mAssetManager;
    }
}
