package com.znh.selectalbum.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.znh.selectalbum.R;
import com.znh.selectalbum.adapter.GridViewAdapter;
import com.znh.selectalbum.model.ImageItem;
import com.znh.selectalbum.utils.ImageConstans;
import com.znh.selectalbum.utils.ImageHelper;

import org.xutils.ImageManager;
import org.xutils.x;

import java.io.File;

public class SelectImageActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ImageHelper mImageHelper;

    private GridView mGridView;

    private GridViewAdapter mGridViewAdapter;

    private ImageManager mImageManager;

    private PopupWindow pop;

    private LinearLayout ll_popup;

    private View activityView;

    private String filePath;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        activityView = getLayoutInflater().inflate(R.layout.activity_select_image, null);
        init();
        initPopupWindow();
    }

    public void init() {
        mImageHelper = ImageHelper.getHelper();
        mImageHelper.init(getApplicationContext());
        mImageManager = x.image();
        mGridView = (GridView) findViewById(R.id.image_gridview);
        mGridViewAdapter = new GridViewAdapter(this, mImageManager, ImageConstans.selectImageItemList);
        mGridView.setAdapter(mGridViewAdapter);
        mGridView.setOnItemClickListener(this);
    }

    public void initPopupWindow() {
        if (pop == null) {
            pop = new PopupWindow(this);
        }
        View popView = getLayoutInflater().inflate(R.layout.layout_popup_window, null);
        ll_popup = (LinearLayout) popView.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(popView);

        popView.findViewById(R.id.pop_parent).setOnClickListener(this);
        popView.findViewById(R.id.view_popupwindow_camera).setOnClickListener(this);
        popView.findViewById(R.id.view_popupwindow_Photo).setOnClickListener(this);
        popView.findViewById(R.id.view_popupwindow_cancel).setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (ImageConstans.selectImageItemList.size() < ImageConstans.MAX_NUM && ImageConstans.selectImageItemList.size() == position && ll_popup != null && pop != null) {
            ll_popup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_activity_translate_in));
            pop.showAtLocation(activityView, Gravity.BOTTOM, 0, 0);
        } else {
            //跳到大图预览界面
            Intent intent = new Intent(this, PreviewImaeActivity.class);
            intent.putExtra(ImageConstans.CAMERA_GRALLERY_POSITION, position);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_popupwindow_camera:
                cameraPicture();
                break;
            case R.id.view_popupwindow_Photo:
                Intent intent = new Intent(this, AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pop_activity_translate_in, R.anim.pop_activity_translate_out);
                break;
        }
        popDismiss();
    }

    /**
     * popwindow消失相关设置
     */
    public void popDismiss() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }

        if (ll_popup != null) {
            ll_popup.clearAnimation();
        }
    }

    /**
     * 调起相机
     */
    public void cameraPicture() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = String.valueOf(System.currentTimeMillis());
        File file = new File(mImageHelper.getPhotoImagePath(fileName, this));
        uri = getUriForFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        filePath = file.getAbsolutePath();
        startActivityForResult(openCameraIntent, 100);
    }

    private Uri getUriForFile(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getApplicationContext(), "com.znh.selectalbum.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 100:
                if (filePath == null) {
                    Toast.makeText(this, "图片保存失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                sendBroadcast(intent);
                ImageItem takePhoto = new ImageItem();
                takePhoto.setImagePath(filePath);
                SystemClock.sleep(1000);
                takePhoto.setImageId(mImageHelper.getImageId(filePath));
                ImageConstans.selectImageItemList.add(takePhoto);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGridViewAdapter.setSelectImageList(ImageConstans.selectImageItemList);
        mGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ImageConstans.selectImageItemList.clear();
    }

    public void back(View view) {
        if (!isFinishing()) {
            finish();
        }
    }
}
