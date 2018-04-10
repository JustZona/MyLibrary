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

public class NandTRelativeLayout extends CarouselRelativeLayout {

    private Context context;
    private LinearLayout contentParent;
    private TextView tv;
    private TextView title;

    public NandTRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public NandTRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NandTRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        contentParent = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        contentParent.setGravity(Gravity.CENTER);
        contentParent.setLayoutParams(layoutParams);
    }

    @Override
    public void initL(CarouselBuilder carouselBuilder, int max) {
        super.initL(carouselBuilder, max);
        contentParent.removeAllViews();
        initTitle();
        initTv();
        addView(contentParent);
    }

    private void initTv(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.rightMargin = 10;
        layoutParams.leftMargin = 50;
        tv = new TextView(context);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(layoutParams);
        tv.setText((carouselBuilder.getStart()+1)+"/"+max);
        contentParent.addView(tv);

    }

    private void initTitle(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        title = new TextView(context);
        title.setSingleLine(true);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.LEFT);
        title.setLayoutParams(layoutParams);
        contentParent.addView(title);
    }

    /**
     * 设置标题和页数.
     * @param position
     * @param titleStr
     */
    public void setTvAndTitle(int position,String titleStr){
        tv.setText((position+1)+"/"+max);
        title.setText(titleStr);
    }

}
