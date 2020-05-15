package com.znh.hook.host;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * Created by znh on 2020/5/5
 * <p>
 * 自定义Handler的Callback，在里面实现自己的逻辑
 */
public class HookCallback implements Handler.Callback {

    private static final int LAUNCH_ACTIVITY = 100;

    private Handler mH;

    public HookCallback(Handler mH) {
        this.mH = mH;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == LAUNCH_ACTIVITY) {
            try {
                //msg.obj是ActivityClientRecord对象
                //将intent的值替换为真正的targetIntent(将启动ProxyActivity的操作替换成启动PluginActivity的操作)
                Intent intent = (Intent) ReflectUtils.getFieldValue(msg.obj, msg.obj.getClass(), "intent");
                Intent targetIntent = intent.getParcelableExtra("targetIntent");
                if (targetIntent != null) {
                    ReflectUtils.setFieldValue(msg.obj, msg.obj.getClass(), "intent", targetIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //让handler拿着修改后的msg继续执行系统的流程
        mH.handleMessage(msg);

        //这里需要返回true,不然handler会拿着老的msg继续执行系统的流程
        return true;
    }
}
