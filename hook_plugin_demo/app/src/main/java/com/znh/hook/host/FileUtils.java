package com.znh.hook.host;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by znh on 2020/5/5
 * <p>
 * 操作文件工具类
 */
public class FileUtils {
    /**
     * 将assets下的文件拷贝到指定的文件中
     *
     * @param assetFilePath  assets下要拷贝的文件路径
     * @param targetFilePath 要拷贝的目标文件路径
     */
    public static boolean copyFileFromAssets(Context context, String assetFilePath, String targetFilePath) {
        File oldFile = new File(targetFilePath);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = context.getResources().getAssets().open(assetFilePath);
            out = new FileOutputStream(targetFilePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            File targetFile = new File(targetFilePath);
            return targetFile.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
