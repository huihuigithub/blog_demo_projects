package com.znh.selectalbum.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextUtils;
import android.util.Log;


import com.znh.selectalbum.model.ImageItem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageHelper {
    /**
     * 全局上下文对象
     */
    Context context;

    /**
     * 用于操作内容提供者对外提供的数据
     */
    ContentResolver mContentResolver;

    /**
     * 手机中所有图片信息列表
     */
    List<ImageItem> mImageItemList;

    /**
     * 图片缩略图信息集合,key是图片id，value是图片缩略图路径
     */
    HashMap<String, String> mThumbnailList = new HashMap<String, String>();

    /**
     * ImageHelper实例对象引用
     */
    private static ImageHelper instance;

    private ImageHelper() {
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static ImageHelper getHelper() {
        if (instance == null) {
            instance = new ImageHelper();
        }
        return instance;
    }

    /**
     * 初始化ContentResolver
     *
     * @param context
     */
    public void init(Context context) {
        if (this.context == null) {
            this.context = context;
            mContentResolver = context.getContentResolver();
        }
    }

    /**
     * 获取图片缩略图信息
     */
    private void getThumbnail() {
        String[] projection = {Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA};
        Cursor cursor = mContentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
        getThumbnailColumnData(cursor);
    }

    private void getThumbnailColumnData(Cursor cur) {
        if (cur.moveToFirst()) {
            int image_id;
            String image_path;
            int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
            do {
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);
                mThumbnailList.put("" + image_id, image_path);
            } while (cur.moveToNext());
        }
    }

    /**
     * 获取所有图片信息
     */
    private void buildImagesList() {
        mImageItemList = new ArrayList<>();
        getThumbnail();
        String columns[] = new String[]{Media._ID, Media.DATA};
        Cursor cur = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
        if (cur.moveToFirst()) {
            int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
            int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);

            do {
                String _id = cur.getString(photoIDIndex);
                String path = cur.getString(photoPathIndex);

                ImageItem imageItem = new ImageItem();
                imageItem.imageId = _id;
                imageItem.imagePath = path;
                imageItem.thumbnailPath = mThumbnailList.get(_id);
                if (!TextUtils.isEmpty(imageItem.imagePath)) {
                    mImageItemList.add(imageItem);
                }
            } while (cur.moveToNext());
        }
    }


    public List<ImageItem> getImageItemList() {
        if (mImageItemList != null) {
            mImageItemList.clear();
        }
        buildImagesList();
        return mImageItemList;
    }

    /**
     * 根据图片路径查询出图片id
     *
     * @param imagePath
     * @return
     */
    public String getImageId(String imagePath) {
        Cursor cursor = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, null, Media.DATA + "=?", new String[]{imagePath}, null);
        if (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndexOrThrow(Media._ID));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(Media.DATA));
            Log.i("camera1", "id " + _id);
            Log.i("camera1", "path " + path);
            return _id;
        } else {
            Log.i("camera1", "没有查询到数据");
            return "";
        }
    }

    public String SAVE_NET_PIC = Environment.getExternalStorageDirectory() + "/vintop_k/";

    /**
     * 得到图片路径
     *
     * @param picName
     * @param context
     */
    public String getPhotoImagePath(String picName, Context context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SAVE_NET_PIC = context.getCacheDir() + "/vintop_k/";
        }
        File file = new File(SAVE_NET_PIC);
        file.mkdirs();// 创建文件夹
        return SAVE_NET_PIC + picName + ".jpg";
    }

    /**
     * 根据图片路径缩放图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 根据图片路径缩放成缩略图
     *
     * @param path
     * @return
     * @throws IOException
     */
    public Bitmap revitionImageSizeToThumb(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 256) && (options.outHeight >> i <= 256)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 旋转图片
     *
     * @param angle  旋转的角度
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
