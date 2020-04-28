package com.znh.selectalbum.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


import com.znh.selectalbum.R;
import com.znh.selectalbum.adapter.AlbumAdapter;
import com.znh.selectalbum.model.ImageItem;
import com.znh.selectalbum.utils.ImageConstans;
import com.znh.selectalbum.utils.ImageHelper;

import org.xutils.ImageManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ninghui on 2016/8/30.
 * 相册选择页面
 */
public class AlbumActivity extends Activity {
    private GridView mGridView;

    private AlbumAdapter mCameraAlbumAdapter;

    private Button completeBtn;

    private List<ImageItem> dataList;

    private ImageHelper helper;

    private ArrayList<ImageItem> preImageItemList = new ArrayList<ImageItem>();

    private ImageManager mImageManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_album);
        init();
        setListener();
    }

    public void cancel(View v) {
        ImageConstans.selectImageItemList.clear();
        ImageConstans.selectImageItemList.addAll(preImageItemList);
        finish();
    }

    public void complete(View v) {
        overridePendingTransition(R.anim.pop_activity_translate_in, R.anim.pop_activity_translate_out);
        Log.i("camera1", "ImageConstans.selectImageItemList完成按钮  " + ImageConstans.selectImageItemList.size());
        finish();
    }


    // 初始化，给一些对象赋值
    private void init() {
        mImageManager= x.image();
        preImageItemList.clear();
        preImageItemList.addAll(ImageConstans.selectImageItemList);

        helper = ImageHelper.getHelper();
        helper.init(getApplicationContext());

        mGridView = (GridView) findViewById(R.id.myGrid);
        mCameraAlbumAdapter = new AlbumAdapter(this,mImageManager, dataList, ImageConstans.selectImageItemList);
        mGridView.setAdapter(mCameraAlbumAdapter);

        completeBtn = (Button) findViewById(R.id.btn_complete);
        completeBtn.setText("完成" + "(" + ImageConstans.selectImageItemList.size() + "/" + ImageConstans.MAX_NUM + ")");
    }

    public void setListener(){
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ImageConstans.selectImageItemList.contains(dataList.get(position))) {
                    ImageConstans.selectImageItemList.remove(dataList.get(position));
                    mCameraAlbumAdapter.setSelectedDataList(ImageConstans.selectImageItemList);
                    mCameraAlbumAdapter.notifyDataSetChanged();
                } else if (ImageConstans.selectImageItemList.size() < ImageConstans.MAX_NUM) {
                    ImageConstans.selectImageItemList.add(dataList.get(position));
                    mCameraAlbumAdapter.setSelectedDataList(ImageConstans.selectImageItemList);
                    mCameraAlbumAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AlbumActivity.this, "最多只能选择" + ImageConstans.MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                }
                completeBtn.setText("完成" + "(" +ImageConstans.selectImageItemList.size() + "/" + ImageConstans.MAX_NUM + ")");
            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ImageConstans.selectImageItemList.clear();
            ImageConstans.selectImageItemList.addAll(preImageItemList);
            finish();
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        dataList = helper.getImageItemList();
        Collections.reverse(dataList);
        if (mCameraAlbumAdapter != null) {
            mCameraAlbumAdapter.setDataList(dataList);
            mCameraAlbumAdapter.notifyDataSetChanged();
        }
    }
}
