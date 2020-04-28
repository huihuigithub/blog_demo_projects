package com.znh.customviewdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.znh.customviewdemo.R;
import com.znh.customviewdemo.view.CustomProgressBar;

/**
 * Created by znh on 2017/8/14.
 * 自定义进度条
 */

public class CustomProgressBarActivity extends Activity {

    private int progress = 10;

    private CustomProgressBar progress_bar;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            if (progress == 90) {
                progress = 10;
            }
            progress_bar.setmRate(progress / 100.0f);
            mHandler.sendEmptyMessageDelayed(0, 200);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar_layout);
        progress_bar = findViewById(R.id.progress_bar);
        mHandler.sendEmptyMessageDelayed(0, 200);
    }
}
