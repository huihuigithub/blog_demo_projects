package com.znh.selectalbum.utils;

import android.content.Context;
import android.content.Intent;

import com.znh.selectalbum.activity.SelectImageActivity;


/**
 * Created by ninghui on 2016/8/31.
 */
public class IntentUtils {
    public static void toSelectImageActivity(Context context){
        Intent intent=new Intent(context, SelectImageActivity.class);
        context.startActivity(intent);
    }
}
