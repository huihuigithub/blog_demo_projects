package com.znh.selectalbum.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.znh.selectalbum.R;
import com.znh.selectalbum.model.ImageItem;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by ninghui on 2016/3/22.
 * <p>
 * 大图预览适配器
 */
public class PreviewImageAdapter extends PagerAdapter {

    private Context context;

    private List<ImageItem> mImageItemList;

    public PreviewImageAdapter(Context context, List<ImageItem> imageItemList) {
        this.context = context;
        this.mImageItemList = imageItemList;
    }

    public void setmImageItemList(List<ImageItem> imageItemList) {
        this.mImageItemList = imageItemList;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mImageItemList == null ? 0 : mImageItemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = container.inflate(context, R.layout.image_item_photo_view, null);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Activity activity = (Activity) context;
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        });
        if (mImageItemList.get(position) != null && mImageItemList.get(position).getBitmap() != null) {
            photoView.setImageBitmap(mImageItemList.get(position).getBitmap());
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
