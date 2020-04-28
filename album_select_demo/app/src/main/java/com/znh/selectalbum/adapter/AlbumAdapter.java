package com.znh.selectalbum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.znh.selectalbum.R;
import com.znh.selectalbum.model.ImageItem;

import org.xutils.ImageManager;
import org.xutils.image.ImageOptions;

import java.util.List;

/**
 * Created by ninghui on 2016/8/30.
 * <p>
 * 选择相册页面适配器
 */
public class AlbumAdapter extends BaseAdapter {
    private Context context;

    private List<ImageItem> dataList;

    private List<ImageItem> selectedDataList;

    private ImageManager mImageManager;

    private ImageOptions mImageOptions;


    public AlbumAdapter(Context context, ImageManager mImageManager, List<ImageItem> dataList, List<ImageItem> selectedDataList) {
        this.context = context;
        this.mImageManager = mImageManager;
        this.dataList = dataList;
        this.selectedDataList = selectedDataList;
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(500);
        mImageOptions = new ImageOptions.Builder().setUseMemCache(true).setAnimation(alphaAnimation).setLoadingDrawableId(R.drawable.camera_no_thum_pictures).setFailureDrawableId(R.drawable.camera_no_thum_pictures).build();
    }

    public void setSelectedDataList(List<ImageItem> selectedDataList) {
        this.selectedDataList = selectedDataList;
    }

    public void setDataList(List<ImageItem> dataList) {
        this.dataList = dataList;
    }

    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.album_camera_item_grid, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            viewHolder.rl_select_overly = (RelativeLayout) convertView.findViewById(R.id.rl_select_overly);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageItem item = dataList.get(position);
        mImageManager.bind(viewHolder.imageView, item.getThumbnailPath() == null ? item.getImagePath() : item.getThumbnailPath(), mImageOptions);
        if (selectedDataList.contains(item)) {
            viewHolder.rl_select_overly.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rl_select_overly.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        RelativeLayout rl_select_overly;
    }
}
