package com.znh.plugin.plugin_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.znh.plugin.plugin_interface.PluginActivityInterface;
import com.znh.plugin.plugin_interface.PluginConstance;

/**
 * Created by znh on 2020-04-17
 */
public class BaseActivity extends AppCompatActivity implements PluginActivityInterface {

    Activity that;

    /**
     * 设置上下文
     *
     * @param content
     */
    @Override
    public void setActivityContent(Activity content) {
        this.that = content;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (that == null) {
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (that != null) {
            that.setContentView(layoutResID);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (that != null) {
            return that.findViewById(id);
        }
        return super.findViewById(id);
    }

    @Override
    public void startActivity(Intent intent) {
        if (intent.getComponent() == null) {
            return;
        }
        if (that != null) {//todo
            Intent pluginIntent = new Intent();
            pluginIntent.putExtra(PluginConstance.KEY_CLASS_NAME, intent.getComponent().getClassName());
            that.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }
}
