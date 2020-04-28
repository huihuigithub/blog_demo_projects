package com.znh.selectalbum.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.znh.selectalbum.R;
import com.znh.selectalbum.adapter.HomeRvAdapter;
import com.znh.selectalbum.utils.ClickUtils;
import com.znh.selectalbum.utils.IntentUtils;

import java.util.concurrent.Executors;


public class MainActivity extends Activity implements View.OnClickListener {
    //图片列表RecyclerView
    private RecyclerView mRecyclerView;
    //图片列表的适配器
    private HomeRvAdapter mAdapter;
    //属性动画
    private ValueAnimator mValueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestFilePermission(this);
        init();
    }

    /**
     * 初始化操作
     */
    public void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mAdapter = new HomeRvAdapter(this);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(16));

        mValueAnimator = ValueAnimator.ofInt(0, 200000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(Integer.MAX_VALUE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (mRecyclerView != null) {
                    mRecyclerView.scrollBy(0, 6);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mValueAnimator != null) {
            mValueAnimator.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }


    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view://选择图片
                IntentUtils.toSelectImageActivity(this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ClickUtils.exitBy2Click(this)) {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 申请文件读取权限
     *
     * @param activity
     * @return
     */
    public boolean requestFilePermission(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //异步发起权限请求，防止界面锁死
                new AsyncTask<String, Integer, Object>() {
                    @Override
                    protected Object doInBackground(String... params) {
                        activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                    }
                }.executeOnExecutor(Executors.newCachedThreadPool());
                return false;
            }
        }
        return true;
    }
}
