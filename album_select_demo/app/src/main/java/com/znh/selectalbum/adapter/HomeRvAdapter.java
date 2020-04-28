package com.znh.selectalbum.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.znh.selectalbum.R;
import com.znh.selectalbum.activity.MainActivity;
import com.znh.selectalbum.model.ImageItem;
import com.znh.selectalbum.utils.ImageConstans;
import com.znh.selectalbum.view.Roat3DAnimation;

import org.xutils.x;

import java.util.List;

/**
 * Created by znh on 2017/5/5.
 */

public class HomeRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //默认图片资源
    private int[] images = new int[]{R.drawable.default1, R.drawable.default2, R.drawable.default3};

    private MainActivity context;

    public HomeRvAdapter(MainActivity context) {
        this.context = context;
    }

    private static class ComViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ComViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter_item, parent, false);
        RecyclerView.ViewHolder holder = new ComViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComViewHolder viewHolder = (ComViewHolder) holder;

        ViewGroup.LayoutParams lp = viewHolder.imageView.getLayoutParams();
        lp.height = (int) (200 + Math.random() * 200);
        viewHolder.imageView.setLayoutParams(lp);

        List<ImageItem> list = ImageConstans.selectImageItemList;
        if (list != null && !list.isEmpty()) {
            x.image().bind(viewHolder.imageView, list.get(position % list.size()).getImagePath());
            viewHolder.imageView.setTag(list.get(position % list.size()).getImagePath());
        } else {
            viewHolder.imageView.setImageResource(images[(position % images.length)]);
        }

        viewHolder.imageView.setOnClickListener(context);

        // 获取布局的中心点位置，作为旋转的中心点
        float centerX = lp.width / 2f;
        float centerY = lp.height / 2f;
        Roat3DAnimation rotation = new Roat3DAnimation(-90, 90, centerX, centerY, 1.0f, false);
        rotation.setDuration(5000);
        rotation.setRepeatCount(Animation.INFINITE);
        rotation.setRepeatMode(Animation.REVERSE);
        rotation.setInterpolator(new LinearInterpolator());
        viewHolder.imageView.startAnimation(rotation);
    }
}
