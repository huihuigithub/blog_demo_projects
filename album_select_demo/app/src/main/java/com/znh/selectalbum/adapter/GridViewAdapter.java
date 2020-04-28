package com.znh.selectalbum.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.znh.selectalbum.R;
import com.znh.selectalbum.model.ImageItem;
import com.znh.selectalbum.utils.ImageConstans;

import org.xutils.ImageManager;
import org.xutils.image.ImageOptions;

import java.util.List;

/**
 * Created by ninghui on 2016/5/17.
 * <p>
 * 选择结果页面适配器
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;

    private ImageManager mImageManager;

    private List<ImageItem> mSelectImageList;

    private ImageOptions mImageOptions;

    public GridViewAdapter(Context context, ImageManager mImageManager, List<ImageItem> mSelectImageList) {
        this.context = context;
        this.mImageManager = mImageManager;
        this.mSelectImageList = mSelectImageList;
        mImageOptions = new ImageOptions.Builder().setUseMemCache(true).setLoadingDrawableId(R.drawable.camera_no_thum_pictures).setFailureDrawableId(R.drawable.camera_no_thum_pictures).build();
    }

    public void setSelectImageList(List<ImageItem> mSelectImageList) {
        this.mSelectImageList = mSelectImageList;
    }

    @Override
    public int getCount() {
        if (mSelectImageList.size() < ImageConstans.MAX_NUM) {
            return mSelectImageList.size() + 1;
        } else {
            return ImageConstans.MAX_NUM;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.select_image_item_grid, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == mSelectImageList.size()) {
            viewHolder.imageView.setImageResource(R.drawable.add_pic_default);
        } else {
            mImageManager.bind(viewHolder.imageView, mSelectImageList.get(position).getImagePath(), mImageOptions);
        }
        return convertView;
    }

    private class ViewHolder {
        public ImageView imageView;
    }
}
