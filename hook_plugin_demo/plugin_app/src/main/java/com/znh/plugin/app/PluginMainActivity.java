package com.znh.plugin.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PluginMainActivity extends BasePluginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
    }

    /**
     * 跳转到第二个页面
     *
     * @param view
     */
    public void jump(View view) {
        Intent intent = new Intent(this, PluginSecondActivity.class);
        startActivity(intent);
    }
}
