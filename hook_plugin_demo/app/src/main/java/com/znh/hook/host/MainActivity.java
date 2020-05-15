package com.znh.hook.host;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转到插件页面
     *
     * @param view
     */
    public void jump(View view) {
        Intent intent = new Intent();
        intent.setClassName(this, "com.znh.plugin.app.PluginMainActivity");
        startActivity(intent);
    }
}
