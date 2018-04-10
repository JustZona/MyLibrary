package com.example.mylibrary.control.PicBanner.bottom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.control.PicBanner.CarouselBuilder;
import com.example.mylibrary.control.PicBanner.CarouselRelativeLayout;


/**
 * Created by zy on 2017/8/17.
 */

public class TitleRelativeLayout extends CarouselRelativeLayout {

    private Context context;
    private LinearLayout contentParent;
    private TextView tv;

    public TitleRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public TitleRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        contentParent = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        contentParent.setLayoutParams(layoutParams);
    }

    @Override
    public void initL(CarouselBuilder carouselBuilder, int max) {
        super.initL(carouselBuilder, max);
        contentParent.removeAllViews();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.rightMargin = 10;
        layoutParams.leftMargin = 10;
        tv = new TextView(context);
        tv.setSingleLine(true);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.LEFT);
        tv.setLayoutParams(layoutParams);
        contentParent.addView(tv);
        addView(contentParent);
    }

    /**
     * 设置标题.
     * @param title
     */
    public void setTextTitle(String title){
        tv.setText(title);
    }
}
