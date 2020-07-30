package com.znh.aptdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.znh.annotation.HuiAnnotation;

@HuiAnnotation
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    public void test() {
        String msg = MainActivityHelper.print("测试一下...");
        System.out.println(msg);
    }
}
