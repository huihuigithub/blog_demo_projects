package com.znh.plugin.plugin_interface;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by znh on 2020-04-17
 */
public interface PluginActivityInterface {

    /**
     * 设置上下文
     *
     * @param content
     */
    void setActivityContent(Activity content);

    void onCreate(Bundle savedInstanceState);
}
