package com.znh.hot.fix;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by znh on 2020/5/4
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        int value = 10 / 0;

        Toast.makeText(this, "bug修复成功", Toast.LENGTH_LONG).show();
    }
}
