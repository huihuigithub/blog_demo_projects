package com.znh.sharedemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.znh.sharedemo.R;
import com.znh.sharedemo.adapter.ShareCustomAdapter;
import com.znh.sharedemo.model.ShareModel;
import com.znh.sharedemo.model.SharePlatformModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by znh on 2017/6/15.
 * <p>
 * 自定义分享操作的工具类
 */

public class ShareUtils implements AdapterView.OnItemClickListener {

    private static final int SHARE_RESULT_SUCCESS = 1;//分享成功
    private static final int SHARE_RESULT_ERROR = 2;//分享失败
    private static final int SHARE_RESULT_CANCLE = 3;//分享取消

    //应用名称
    public static final String appName = "分享demo";

    //平台图标列表
    private int[] mPlatformIcons = {R.drawable.logo_sinaweibo, R.drawable.logo_wechat, R.drawable.logo_wechatmoments, R.drawable.logo_qq, R.drawable.logo_qzone};

    //平台显示名称列表
    private String[] mPlatformShowNames = {"微博", "微信好友", "朋友圈", "QQ", "QQ空间"};

    //ShareSdk中的各平台名称
    private String[] mPlatformUniqueNames = {SinaWeibo.NAME, Wechat.NAME, WechatMoments.NAME, QQ.NAME, QZone.NAME};

    //上下文对象
    private Context sContext;

    //分享弹框
    private AlertDialog mAlertDialog;

    //显示分享平台的GridView
    private GridView mGridView;

    //分享的平台列表
    private List<SharePlatformModel> mSharePlatformModels;

    //要隐藏的平台的名称列表（如：SinaWeibo.NAME）
    private List<String> mHidePlatformNames;

    //平台列表适配器
    private ShareCustomAdapter mShareCustomAdapter;

    //分享时需要的一些数据参数
    private ShareModel mShareModel;


    //将分享结果回调到主线程
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHARE_RESULT_SUCCESS://分享成功
                    ToastUtil.show("分享成功");
                    setShareCallback((Platform) msg.obj);
                    break;
                case SHARE_RESULT_ERROR://分享失败
                    ToastUtil.show("分享失败");
                    break;
                case SHARE_RESULT_CANCLE://分享取消
                    ToastUtil.show("分享取消");
                    break;
            }
        }
    };


    /**
     * 构造方法，进行一些初始化操作
     *
     * @param context
     */
    public ShareUtils(Context context, ShareModel shareModel) {
        init(context, shareModel);
    }

    /**
     * 初始化操作
     *
     * @param context
     */
    private void init(Context context, ShareModel shareModel) {
        sContext = context;
        mShareModel = shareModel;
        mShareCustomAdapter = new ShareCustomAdapter(sContext);
        mSharePlatformModels = new ArrayList<>();
    }

    /**
     * 弹出分享页面
     */
    public void show() {
        if (mShareModel == null) {
            ToastUtil.show("参数有误");
            return;
        }

        //准备分享的平台列表
        for (int i = 0; i < mPlatformUniqueNames.length; i++) {
            if (mHidePlatformNames != null && !mHidePlatformNames.isEmpty() && mHidePlatformNames.contains(mPlatformUniqueNames[i])) {
                continue;
            }
            SharePlatformModel entity = new SharePlatformModel();
            entity.setPlatformIcon(mPlatformIcons[i]);
            entity.setPlatformShowName(mPlatformShowNames[i]);
            entity.setPlatformUniqeName(mPlatformUniqueNames[i]);
            mSharePlatformModels.add(entity);
        }

        //创建弹框
        dismiss();
        mAlertDialog = new AlertDialog.Builder(sContext, R.style.DialogNoActionBar).create();
        mAlertDialog.show();

        //设置弹框的位置和宽度
        Window window = mAlertDialog.getWindow();
        if (window == null) {
            return;
        }
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setWindowAnimations(R.style.dialog_anim_styles);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(R.layout.share_custom_dialog_layout);

        //设置取消按钮
        ImageView iv_cancel_btn = window.findViewById(R.id.iv_cancel_btn);
        iv_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //设置GridView
        mGridView = window.findViewById(R.id.share_grid_view);
        mGridView.setNumColumns(mSharePlatformModels.size());
        mShareCustomAdapter.setSharePlatformEntities(mSharePlatformModels);
        mGridView.setAdapter(mShareCustomAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 点击分享条目分享
     *
     * @param adapterView
     * @param view
     * @param position
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        dismiss();
        if (mShareModel == null) {
            ToastUtil.show("参数有误");
            return;
        }

        if (mSharePlatformModels == null || mSharePlatformModels.isEmpty()) {
            ToastUtil.show("平台不正确");
            return;
        }

        //分享
        commonShare(mSharePlatformModels.get(position));
    }

    /**
     * 分享
     *
     * @param platformEntity
     */
    private void commonShare(SharePlatformModel platformEntity) {
        //分享平台
        Platform platform = null;

        //分享参数
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setTitle(mShareModel.getTitle());
        shareParams.setText(mShareModel.getText());
        shareParams.setImageUrl(mShareModel.getImgUrl());

        if (SinaWeibo.NAME.equals(platformEntity.getPlatformUniqeName())) {
            platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        } else if (Wechat.NAME.equals(platformEntity.getPlatformUniqeName())) {
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setUrl(mShareModel.getUrl());
            platform = ShareSDK.getPlatform(Wechat.NAME);
        } else if (WechatMoments.NAME.equals(platformEntity.getPlatformUniqeName())) {
            shareParams.setShareType(Platform.SHARE_WEBPAGE);
            shareParams.setUrl(mShareModel.getUrl());
            platform = ShareSDK.getPlatform(WechatMoments.NAME);
        } else if (QQ.NAME.equals(platformEntity.getPlatformUniqeName())) {
            shareParams.setTitleUrl(mShareModel.getUrl());
            platform = ShareSDK.getPlatform(QQ.NAME);
        } else if (QZone.NAME.equals(platformEntity.getPlatformUniqeName())) {
            shareParams.setSite(appName);
            shareParams.setTitleUrl(mShareModel.getUrl());
            shareParams.setSiteUrl(mShareModel.getUrl());
            platform = ShareSDK.getPlatform(QZone.NAME);
        }
        if (platform == null) {
            ToastUtil.show("平台不正确");
            return;
        }
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Message message = Message.obtain();
                message.what = SHARE_RESULT_SUCCESS;
                message.obj = platform;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Message message = Message.obtain();
                message.what = SHARE_RESULT_ERROR;
                message.obj = platform;
                mHandler.sendMessage(message);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Message message = Message.obtain();
                message.what = SHARE_RESULT_CANCLE;
                message.obj = platform;
                mHandler.sendMessage(message);
            }
        });
        ToastUtil.show("分享正在后台操作...");
        //platform.SSOSetting(true);
        platform.share(shareParams);
    }


    /**
     * 分享结果处理
     *
     * @param platform
     */
    private void setShareCallback(Platform platform) {
        if (mShareModel == null) {
            return;
        }
        ToastUtil.show("处理分享结果");
    }


    /**
     * 设置要隐藏的平台的名称列表
     *
     * @param mHidePlatformNames
     */
    public void setPlatformShowNames(List<String> mHidePlatformNames) {
        this.mHidePlatformNames = mHidePlatformNames;
    }


    /**
     * 关闭对话框
     */
    private void dismiss() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }
}
