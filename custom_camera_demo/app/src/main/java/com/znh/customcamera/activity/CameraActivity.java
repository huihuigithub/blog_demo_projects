package com.znh.customcamera.activity;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import com.znh.customcamera.R;
import com.znh.customcamera.utils.DisPlayUtils;

/**
 * Created by znh on 2017/3/15.
 * 拍照actiivty
 */

public class CameraActivity extends Activity implements View.OnClickListener {

    private Button mChangeBtn, mCameraBtn;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    private int mPictureWidth;
    private int mPictureHeight;
    private int mPreviewWidth;
    private int mPreviewHeight;

    //0代表前置摄像头，1代表后置摄像头
    private int mCurrentCameraStatus = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mChangeBtn = (Button) findViewById(R.id.change_btn);
        mCameraBtn = (Button) findViewById(R.id.camera_btn);
        mChangeBtn.setOnClickListener(this);
        mCameraBtn.setOnClickListener(this);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mCamera != null) {
                    setPreviewCamera();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    setPreviewCamera();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surface_view://点击聚焦
                if (mCamera != null) {
                    mCamera.autoFocus(null);
                }
                break;
            case R.id.change_btn://切换前后置摄像头
                changeCamerFG();
                break;
            case R.id.camera_btn://拍照
                takePhoto();
                break;
        }
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        if (mCamera == null) {
            return;
        }
        //获取到相机参数
        Camera.Parameters parameters = mCamera.getParameters();
//        //设置图片预览尺寸
//        parameters.setPreviewSize(disPlayWidth,disPlayHight);
//        //设置图片尺寸
//        parameters.setPictureSize(disPlayWidth,disPlayHight);
        //设置图片保存格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        // 设置照片质量
        parameters.setJpegQuality(100);
        //设置对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        //设置自动对焦
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            //合成图片
//                            ComposeBmpModel composeBmpModel = new ComposeBmpModel();
//                            composeBmpModel.setBytes(data);
//                            composeBmpModel.setCameraStatus(mCurrentCameraStatus);
//                            composeBmpModel.setWidth(disPlayWidth);
//                            composeBmpModel.setHight(disPlayHight);
//                            //IntentUtils.toPreImageActivity(CameraActivity.this, composeBmpModel);
//                            canvasBitmap(composeBmpModel);
                        }
                    });
                }
            }
        });
    }

    /**
     * 切换摄像头前置、后置
     */
    public void changeCamerFG() {
        //切换前后摄像头
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (mCurrentCameraStatus == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    //释放之前的摄像头
                    releaseCamera();

                    //开启新的摄像头
                    if (mCamera == null) {
                        mCamera = Camera.open(i);
                        if (mSurfaceHolder != null && mCamera != null) {
                            setPreviewCamera();
                        }
                    }
                    mCurrentCameraStatus = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    //释放之前的摄像头
                    releaseCamera();

                    //开启新的摄像头
                    if (mCamera == null) {
                        mCamera = Camera.open(i);
                        if (mSurfaceHolder != null && mCamera != null) {
                            setPreviewCamera();
                        }
                    }
                    mCurrentCameraStatus = 1;
                    break;
                }
            }
        }
    }

    /**
     * 设置相机，并调用预览功能
     */
    public void setPreviewCamera() {
        if (mCamera == null || mSurfaceHolder == null) {
            return;
        }
        try {
            //获得相机支持的照片尺寸,选择合适的尺寸
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
            int maxSize = Math.max(DisPlayUtils.getScreenWidth(), DisPlayUtils.getScreenHight());
            if (maxSize > 0 && (sizes != null && !sizes.isEmpty())) {
                for (int i = 0; i < sizes.size(); i++) {
                    if (maxSize <= Math.max(sizes.get(i).width, sizes.get(i).height)) {
                        parameters.setPictureSize(sizes.get(i).width, sizes.get(i).height);
                        mPictureWidth = sizes.get(i).width;
                        mPictureHeight = sizes.get(i).height;
                        break;
                    }
                }
            }
            List<Camera.Size> ShowSizes = parameters.getSupportedPreviewSizes();
            if (maxSize > 0 && (ShowSizes != null && !ShowSizes.isEmpty())) {
                for (int i = 0; i < ShowSizes.size(); i++) {
                    if (maxSize <= Math.max(ShowSizes.get(i).width, ShowSizes.get(i).height)) {
                        parameters.setPreviewSize(ShowSizes.get(i).width, ShowSizes.get(i).height);
                        mPreviewWidth = ShowSizes.get(i).width;
                        mPictureHeight = ShowSizes.get(i).height;
                        break;
                    }
                }
            }
            //摄像头设置SurfaceHolder对象，把摄像头与SurfaceHolder进行绑定
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //调整系统相机拍照角度
            mCamera.setDisplayOrientation(90);
            //调用相机预览功能
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 释放相机资源
     */
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //开启摄像头
        if (mCamera == null) {
            mCurrentCameraStatus = 1;
            changeCamerFG();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }
}
