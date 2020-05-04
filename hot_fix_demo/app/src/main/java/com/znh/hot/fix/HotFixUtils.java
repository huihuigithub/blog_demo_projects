package com.znh.hot.fix;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by znh on 2020/5/4
 * <p>
 * 热修复管理类
 */
public class HotFixUtils {

    /**
     * 处理dex替换，完成修复
     *
     * @param context
     */
    public static void fixBug(Context context) {
        //将修复的dex文件复制到App的私有目录下
        //targetFilePath:/data/user/0/com.znh.hot.fix/app_hui_patch/patch.apk
        String assetFileName = "patch.apk";
        String targetFilePath = context.getDir("hui_patch", Context.MODE_PRIVATE).getAbsolutePath() + File.separator + assetFileName;
        FileUtils.copyFileFromAssets(context, assetFileName, targetFilePath);

        //创建dex解压输出目录
        String optimizedDirectory = context.getDir("hui_patch", Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "opt_dex";
        File dirFile = new File(optimizedDirectory);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //创建DexClassLoader
        DexClassLoader patchDexClassLoader = new DexClassLoader(targetFilePath, optimizedDirectory, null, context.getClassLoader());

        try {
            //获取自己的dexElements数组
            Object patchDexElements = DexUtils.getDexElements(DexUtils.getPathList(patchDexClassLoader));

            //获取系统原有的dexElements数组
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            Object sysDexElements = DexUtils.getDexElements(DexUtils.getPathList(pathClassLoader));

            //合并两个dexElements数组(这里需要将自己的数组放前面以保证优先加载)
            Object newDexElements = DexUtils.combineArray(patchDexElements, sysDexElements);

            //重新将系统的dexElements替换为合并后的值
            DexUtils.setDexElements(DexUtils.getPathList(pathClassLoader), newDexElements);

            Toast.makeText(context, "修复完成", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
