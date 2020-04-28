package com.znh.sharedemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.znh.sharedemo.R;
import com.znh.sharedemo.model.SharePlatformModel;

import java.util.List;

/**
 * Created by znh on 2017/6/16.
 */

public class ShareCustomAdapter extends BaseAdapter {

    private Context context;
    private List<SharePlatformModel> mSharePlatformEntities;


    public ShareCustomAdapter(Context context) {
        this.context = context;
    }

    public void setSharePlatformEntities(List<SharePlatformModel> mSharePlatformEntities) {
        this.mSharePlatformEntities = mSharePlatformEntities;
    }

    @Override
    public int getCount() {
        return mSharePlatformEntities == null ? 0 : mSharePlatformEntities.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.adapter_share_custom_item, null);
            viewHolder.iv_platform_icon = (ImageView) convertView.findViewById(R.id.id_platform_icon);
            viewHolder.tv_platform_name = (TextView) convertView.findViewById(R.id.id_platform_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SharePlatformModel item = mSharePlatformEntities.get(position);
        if (item == null) {
            return null;
        }
        viewHolder.iv_platform_icon.setImageResource(item.getPlatformIcon());
        viewHolder.tv_platform_name.setText(item.getPlatformShowName());
        return convertView;
    }

    public class ViewHolder {
        ImageView iv_platform_icon;
        TextView tv_platform_name;
    }
}
