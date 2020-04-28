package com.znh.selectalbum.utils;

import com.znh.selectalbum.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninghui on 2016/5/17.
 */
public class ImageConstans {

    /**
     * 可以选择的最大的图片数
     */
    public static final int MAX_NUM = 9;

    /**
     * 选择的图片的集合
     */
    public static List<ImageItem> selectImageItemList = new ArrayList<>();

    /**
     * 传递的当前的position
     */
    public static final String CAMERA_GRALLERY_POSITION="position";
}
