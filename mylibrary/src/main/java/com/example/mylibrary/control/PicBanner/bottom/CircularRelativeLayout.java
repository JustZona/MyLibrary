package com.example.mylibrary.control.PicBanner.bottom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mylibrary.ViewUtil.AdaptationUtil;
import com.example.mylibrary.control.PicBanner.CarouselBuilder;
import com.example.mylibrary.control.PicBanner.CarouselRelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zy on 2017/8/17.
 */

public class CircularRelativeLayout extends CarouselRelativeLayout {

    private Context context;
    private LinearLayout contentParent;
    private List<View> views;

    public CircularRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public CircularRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircularRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        contentParent = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        contentParent.setLayoutParams(layoutParams);
        views = new ArrayList<>();
    }

    @Override
    public void initL(CarouselBuilder carouselBuilder, int max) {
        super.initL(carouselBuilder,max);
        contentParent.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AdaptationUtil.dip2px(context,8), AdaptationUtil.dip2px(context,8));
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        for (int i = 0;i<max;i++){
            View view = new View(context);
            if (i==carouselBuilder.getStart()){
                view.setBackgroundResource(carouselBuilder.getChooseCircular());
            }else {
                view.setBackgroundResource(carouselBuilder.getNoCircular());
            }
            views.add(view);
            view.setLayoutParams(layoutParams);
            contentParent.addView(view);
        }
        addView(contentParent);
    }

    /**
     * 设置当前选中位置.
     * @param position
     */
    public void setPosition(int position){
        for (int i = 0;i<views.size();i++){
            if (i==position){
                views.get(i).setBackgroundResource(carouselBuilder.getChooseCircular());

            }else {
                views.get(i).setBackgroundResource(carouselBuilder.getNoCircular());
            }
        }
    }

}
