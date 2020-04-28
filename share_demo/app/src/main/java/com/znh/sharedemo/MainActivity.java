package com.znh.sharedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.znh.sharedemo.model.ShareModel;
import com.znh.sharedemo.utils.ShareUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 分享按钮
     *
     * @param view
     */
    public void shareClick(View view) {
        ShareModel shareModel = new ShareModel("分享标题", "分享文本", "分享url", "分享图片url");
        ShareUtils shareUtils = new ShareUtils(this, shareModel);
        shareUtils.show();
    }
}
