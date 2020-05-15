package com.znh.plugin.plugin_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);

        findViewById(R.id.jump_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, SecondPluginActivity.class);
                startActivity(intent);
            }
        });
    }
}
