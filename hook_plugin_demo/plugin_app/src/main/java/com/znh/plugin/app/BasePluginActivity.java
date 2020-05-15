package com.znh.plugin.app;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Created by znh on 2020/5/5
 * <p>
 * 插件的基类Activity
 */
public abstract class BasePluginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Resources getResources() {
        if (getApplication() != null && getApplication().getResources() != null) {
            return getApplication().getResources();
        }
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        if (getApplication() != null && getApplication().getAssets() != null) {
            return getApplication().getAssets();
        }
        return super.getAssets();
    }
}
