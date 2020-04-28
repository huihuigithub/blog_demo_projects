package com.znh.customviewdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.znh.customviewdemo.R;
import com.znh.customviewdemo.view.FlowLayout;

/**
 * Created by Administrator on 2017/8/19.
 */

public class FlowLayoutActivity extends Activity {

    private FlowLayout flow_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        flow_layout=findViewById(R.id.flow_layout);
    }
}
