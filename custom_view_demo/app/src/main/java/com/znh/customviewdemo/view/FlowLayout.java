package com.znh.customviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/8/19.
 * 自定义流式布局
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //父view指定好的大小和模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        //自己计算出的大小，用于包裹内容
        int width = 0;
        int height = 0;

        //记录单行的宽度
        int lineWidth = 0;

        //记录单行的高度
        int lineHeight = 0;

        //遍历测量子view
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //子view在宽度上占据的空间
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子view在高度上占据的空间
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth) {//换行
                //宽度最大的作为最终的宽度
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {//不换行
                //累计行宽
                lineWidth += childWidth;
                //取子view中高度最高的作为行高
                lineHeight = Math.max(lineHeight, childHeight);
            }

            //最后一个子view
            if (i == getChildCount() - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        //设置最终大小
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, sizeHeight);
        } else {
            setMeasuredDimension(sizeWidth, height);
        }
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        //累加的高度
        int height = 0;

        //记录单行的高度
        int lineHeight = 0;

        //记录单行的宽度
        int lineWidth = 0;

        //遍历layout子view
        for (int i = 0; i < getChildCount(); i++) {
            //是否换行，换行为true
            boolean isChangeRank = false;

            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //子view在宽度上占据的空间
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子view在高度上占据的空间
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > getWidth()) {//换行
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
                isChangeRank = true;
            } else {//不换行
                //累计行宽
                lineWidth += childWidth;
                //取子view中高度最高的作为行高
                lineHeight = Math.max(lineHeight, childHeight);
                isChangeRank = false;
            }

            //最后一个子view
            if (i == getChildCount() - 1 && isChangeRank) {
                height += lineHeight;
            }

            //计算当前子view的四个顶点坐标
            int childLeft = lineWidth - childWidth + lp.leftMargin;
            int childTop = height + lp.topMargin;
            int childRight = childLeft + child.getMeasuredWidth();
            int childBotom = childTop + child.getMeasuredHeight();
            child.layout(childLeft, childTop, childRight, childBotom);
        }
    }

    /**
     * 重写该方法，以支持margin
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
