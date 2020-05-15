package com.znh.plugin.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.znh.plugin.plugin_interface.PluginConstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPlugin();
    }

    /**
     * 加载插件
     */
    public void loadPlugin() {

        //在App的私有目录下创建插件存储目录和插件apk文件
        //filePath:/data/user/0/com.znh.plugin.demo/app_plugin/plugin.apk
        String fileName = "plugin.apk";
        String filePath = getDir("plugin", MODE_PRIVATE).getAbsolutePath() + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        Log.e("znh", "filePath:" + filePath);

        //将SD卡上的插件拷贝到App的私有目录下
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = getResources().getAssets().open(fileName);
            out = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            File file1 = new File(filePath);
            if (file1.exists()) {
                Toast.makeText(this, "插件加载成功", Toast.LENGTH_LONG).show();
            }

            //加载插件
            PluginManager.getInstance().loadPluginByPath(this, filePath);
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 跳转到插件Activity
     *
     * @param view
     */
    public void jump(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(PluginConstance.KEY_CLASS_NAME, PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }
}
