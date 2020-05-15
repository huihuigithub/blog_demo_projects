package com.znh.hook.host;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by znh on 2020/5/5
 * <p>
 * 预埋的代理Activity
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "我是代理Activity", Toast.LENGTH_LONG).show();
    }
}
