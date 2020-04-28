package com.znh.customviewdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.znh.customviewdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转到自定义进度条页面
     *
     * @param view
     */
    public void click1(View view) {
        Intent intent = new Intent(this, CustomProgressBarActivity.class);
        startActivity(intent);
    }


    /**
     * 跳转到自定义流式布局页面
     *
     * @param view
     */
    public void click2(View view) {
        Intent intent = new Intent(this, FlowLayoutActivity.class);
        startActivity(intent);
    }
}
