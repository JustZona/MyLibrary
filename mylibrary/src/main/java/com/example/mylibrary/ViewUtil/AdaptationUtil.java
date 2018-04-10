package com.example.mylibrary.ViewUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by zy on 2017/4/8.
 * 适配相关工具类.
 */
public class AdaptationUtil {

    protected AdaptationUtil(){}

    /**
     * 根据手机的分辨率从 dp(像素) 的单位 转成为 px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕高宽px.
     * @return
     * point.x
     * 宽度
     * point.y
     * 高度
     */
    public static Point getDisplay(Context context) {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getRealSize(point);
        return point;
    }

    /**
     * 按钮点击波浪反馈.
     * @param normalColor
     * 普通状态背景颜色.
     * @param pressColor
     * 按下背景颜色.
     * @param view
     */
    public static void setOnClickFeedBack(int normalColor, int pressColor, View view) {
        Drawable bgDrawble;
        ColorDrawable drawablePressed = new ColorDrawable(pressColor);//分别解析两种颜色为colordrawble
        ColorDrawable drawableNormal = new ColorDrawable(normalColor);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {//高版本设置RippleDrawable 而低版本设置 StateListDrawable也就是selector
            ColorStateList stateList = ColorStateList.valueOf(pressColor);
            RippleDrawable rippleDrawable = new RippleDrawable(stateList, drawableNormal, drawablePressed);
            bgDrawble = rippleDrawable;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
            stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, drawableNormal);
            bgDrawble = stateListDrawable;
        }
        view.setBackgroundDrawable(bgDrawble);//最终设置给我们的view作为背景
    }

    /**
     * 按钮点击波浪反馈.
     * @param normalColor
     * 普通状态背景.
     * @param pressColor
     * 按下状态背景和波浪背景.
     * @param view
     */
    public static void setOnClickFeedBack(Drawable normalColor, int pressColor, View view) {
        Drawable bgDrawble;
        ColorDrawable drawablePressed = new ColorDrawable(pressColor);//分别解析两种颜色为colordrawble
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {//高版本设置RippleDrawable 而低版本设置 StateListDrawable也就是selector
            ColorStateList stateList = ColorStateList.valueOf(pressColor);
            RippleDrawable rippleDrawable = new RippleDrawable(stateList, normalColor, drawablePressed);
            bgDrawble = rippleDrawable;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
            stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, normalColor);
            bgDrawble = stateListDrawable;
        }
        view.setBackgroundDrawable(bgDrawble);//最终设置给我们的view作为背景
    }

    /**
     * 按钮点击波浪反馈.
     * @param normalColor
     * 普通状态背景.
     * @param color
     * 波浪.
     * @param pressColor
     * 按下状态背景.
     * @param view
     */
    public static void setOnClickFeedBack(Drawable normalColor,int color,Drawable pressColor, View view) {
        Drawable bgDrawble;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {//高版本设置RippleDrawable 而低版本设置 StateListDrawable也就是selector
            ColorStateList stateList = ColorStateList.valueOf(color);
            RippleDrawable rippleDrawable = new RippleDrawable(stateList, normalColor, pressColor);
            bgDrawble = rippleDrawable;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressColor);
            stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, normalColor);
            bgDrawble = stateListDrawable;
        }
        view.setBackgroundDrawable(bgDrawble);//最终设置给我们的view作为背景
    }
}
