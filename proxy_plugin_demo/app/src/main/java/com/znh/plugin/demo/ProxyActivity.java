package com.znh.plugin.demo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.znh.plugin.plugin_interface.PluginActivityInterface;
import com.znh.plugin.plugin_interface.PluginConstance;

import java.lang.reflect.Constructor;

/**
 * Created by znh on 2020-04-17
 * <p>
 * 宿主App中的占位代理Activity
 */
public class ProxyActivity extends AppCompatActivity {

    private String className = "";

    private PluginActivityInterface mPluginActivityInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra(PluginConstance.KEY_CLASS_NAME);

        Log.e("znh", "className:" + className);

        try {
            Class<?> aClass = getClassLoader().loadClass(className);
            Constructor constructor = aClass.getConstructor(new Class[]{});
            Object obj = constructor.newInstance(new Object[]{});
            mPluginActivityInterface = (PluginActivityInterface) obj;
            mPluginActivityInterface.setActivityContent(this);
            mPluginActivityInterface.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {//todo
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra(PluginConstance.KEY_CLASS_NAME, intent.getStringExtra(PluginConstance.KEY_CLASS_NAME));
        super.startActivity(intent1);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }
}
