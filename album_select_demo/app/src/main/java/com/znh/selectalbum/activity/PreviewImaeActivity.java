package com.znh.selectalbum.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.znh.selectalbum.R;
import com.znh.selectalbum.adapter.PreviewImageAdapter;
import com.znh.selectalbum.utils.ImageConstans;
import com.znh.selectalbum.view.PhotoViewViewPager;


public class PreviewImaeActivity extends Activity implements OnPageChangeListener {

    //当前的位置
    private int mPosition = 0;

    private PhotoViewViewPager pager;

    private PreviewImageAdapter adapter;

    private TextView grallery_center_tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);// 切屏到主界面
        grallery_center_tv = (TextView) findViewById(R.id.grallery_center_tv);
        init();
    }

    public void init() {
        mPosition = getIntent().getIntExtra(ImageConstans.CAMERA_GRALLERY_POSITION, 0);

        pager = (PhotoViewViewPager) findViewById(R.id.view_pager_gallery);
        pager.setOnPageChangeListener(this);
        adapter = new PreviewImageAdapter(this, ImageConstans.selectImageItemList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(mPosition);
        grallery_center_tv.setText((mPosition + 1) + "/" + ImageConstans.selectImageItemList.size());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        grallery_center_tv.setText((position + 1) + "/" + ImageConstans.selectImageItemList.size());
    }

    /**
     * 返回按钮
     *
     * @param v
     */
    public void back(View v) {
        finish();
    }

    /**
     * 删除按钮
     *
     * @param v
     */
    public void delete(View v) {
        ImageConstans.selectImageItemList.remove(mPosition);
        adapter.setmImageItemList(ImageConstans.selectImageItemList);
        if (ImageConstans.selectImageItemList.size() == 0) {
            finish();
        } else {
            adapter.notifyDataSetChanged();
            grallery_center_tv.setText((pager.getCurrentItem() + 1) + "/" + ImageConstans.selectImageItemList.size());
        }
    }
}
