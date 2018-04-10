package com.example.mylibrary.control.PicBanner;

import android.support.v4.view.ViewPager;

import com.example.mylibrary.R;


/**
 * Created by zy on 2017/8/17.
 */

public class CarouselBuilder {

    /**选中圆圈背景.*/
    private int chooseCircular = R.drawable.pointchoose;
    /**未选中圆圈背景.*/
    private int noCircular = R.drawable.point;
    /**底部栏背景.*/
    private int Color = android.graphics.Color.parseColor("#00000000");
    /**底部资源背景*/
    private int bg = -1;
    /**从第几页开始.*/
    private int start = 0;
    /**间隔时间.*/
    private long IntervalTime = 5000;

    /**底部高度.*/
    private int circularH = 25;

    /**
     * 设置模式.
     * 0:圆圈模式.
     * 1:右下角显示数字模式.
     * 2:底部标题模式.
     * 3:标题加数字模式.
     * 4:无底部.
     */
    private int MODEL = 0;

    /**页面滑动动画效果.*/
    private ViewPager.PageTransformer pageTransformer;

    public ViewPager.PageTransformer getPageTransformer() {
        return pageTransformer;
    }

    public CarouselBuilder setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
        return this;
    }

    public int getChooseCircular() {
        return chooseCircular;
    }

    public CarouselBuilder setChooseCircular(int chooseCircular) {
        this.chooseCircular = chooseCircular;
        return this;
    }

    public int getNoCircular() {
        return noCircular;
    }

    public CarouselBuilder setNoCircular(int noCircular) {
        this.noCircular = noCircular;
        return this;
    }

    public int getColor() {
        return Color;
    }

    public CarouselBuilder setColor(int color) {
        Color = color;
        return this;
    }

    public int getStart() {
        return start;
    }

    public CarouselBuilder setStart(int start) {
        this.start = start;
        return this;
    }

    public long getIntervalTime() {
        return IntervalTime;
    }

    public CarouselBuilder setIntervalTime(long intervalTime) {
        IntervalTime = intervalTime;
        return this;
    }

    public int getCircularH() {
        return circularH;
    }

    public CarouselBuilder setCircularH(int circularH) {
        this.circularH = circularH;
        return this;
    }

    public int getMODEL() {
        return MODEL;
    }

    public CarouselBuilder setMODEL(int MODEL) {
        this.MODEL = MODEL;
        return this;
    }

    public int getBg() {
        return bg;
    }

    public CarouselBuilder setBg(int bg) {
        this.bg = bg;
        return this;
    }
}
