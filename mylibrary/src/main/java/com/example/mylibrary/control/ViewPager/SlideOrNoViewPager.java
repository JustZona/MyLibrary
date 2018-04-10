package com.example.mylibrary.control.ViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zy on 2017/1/13.
 * @author amazy
 */

public class SlideOrNoViewPager extends ViewPager {
    /**
     * 判断是否能够手动滑动.
     */
    private boolean isCanScroll = false;

    /**
     * 实例化.
     * @param context
     * context
     */
    public SlideOrNoViewPager(Context context) {
        super(context);
    }
    /**
     * 实例化.
     * @param context
     * context
     * @param attrs
     * attrs
     */
    public SlideOrNoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isCanScroll){
            return super.onTouchEvent(arg0);
        }else{
            return false;
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll){
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,true);//表示切换的时候，不需要切换时间。
    }

    /**是否能够手动滑动.*/
    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }
}
