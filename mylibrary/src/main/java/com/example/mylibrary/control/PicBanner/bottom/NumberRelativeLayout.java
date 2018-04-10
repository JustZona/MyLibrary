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

import com.example.mylibrary.R;
import com.example.mylibrary.control.PicBanner.CarouselBuilder;
import com.example.mylibrary.control.PicBanner.CarouselRelativeLayout;


/**
 * Created by zy on 2017/8/17.
 */

public class NumberRelativeLayout extends CarouselRelativeLayout {

    private Context context;
    private LinearLayout contentParent;
    private TextView tv;

    public NumberRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public NumberRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        contentParent = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.rightMargin = 15;
        contentParent.setLayoutParams(layoutParams);
        contentParent.setGravity(Gravity.CENTER);
        contentParent.setBackgroundResource(R.drawable.carouse_tv_bg);
    }

    @Override
    public void initL(CarouselBuilder carouselBuilder, int max) {
        super.initL(carouselBuilder, max);
        contentParent.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.rightMargin = 10;
        layoutParams.leftMargin = 10;
        tv = new TextView(context);
        tv.setLayoutParams(layoutParams);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setText((carouselBuilder.getStart()+1)+"/"+max);
        contentParent.addView(tv);
        addView(contentParent);
    }

    /**
     * 设置当前页数.
     * @param position
     */
    public void setPositionTitle(int position){
        tv.setText((position+1)+"/"+max);
    }
}
