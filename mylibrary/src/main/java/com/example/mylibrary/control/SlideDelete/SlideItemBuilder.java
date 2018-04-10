package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by zy on 2017/2/22.
 */

/**
 * 初始化右侧item.
 */
public class SlideItemBuilder {
    private int textColor = Color.WHITE;
    private int bgColor = Color.WHITE;
    private String text="";
    //滑动菜单item默认宽度100
    private int Width = 100;
    //使用px单位.
    private int TextSize = -1;
    private Context context;
    private int bg = -1;
    private Drawable drawable;
    private View view;
    public SlideItemBuilder setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public SlideItemBuilder setBgColor(int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public SlideItemBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public SlideItemBuilder setWidth(int width) {
        Width = width;
        return this;
    }


    public SlideItemBuilder setTextSize(int textSize) {
        TextSize = textSize;
        return this;
    }

    public int getBg() {
        return bg;
    }

    public SlideItemBuilder setBg(int bg) {
        return this;
    }

    public View getView() {
        return view;
    }

    public SlideItemBuilder setView(View view) {
        this.view = view;
        return this;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public SlideItemBuilder setDrawable(Drawable drawable) {
        return this;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    protected SlideItem create(){
        return new SlideItem(context);
    }

    public int getTextColor() {
        return textColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public String getText() {
        return text;
    }

    public int getWidth() {
        return Width;
    }

    public int getTextSize() {
        return TextSize;
    }
}
