package com.example.mylibrary.control.PicBanner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.mylibrary.ViewUtil.AdaptationUtil;


/**
 * Created by zy on 2017/8/17.
 */

public class CarouselRelativeLayout extends RelativeLayout {

    private Context context;
    public CarouselBuilder carouselBuilder;
    public int max;


    public CarouselRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public CarouselRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CarouselRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        removeAllViews();
    }

    public void initL(CarouselBuilder carouselBuilder,int max){
        this.carouselBuilder = carouselBuilder;
        this.max = max;
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AdaptationUtil.dip2px(context,carouselBuilder.getCircularH()));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.bottomMargin = 10;
        setLayoutParams(layoutParams);
        setBackgroundColor(carouselBuilder.getColor());
        if (carouselBuilder.getBg()!=-1){
            setBackgroundResource(carouselBuilder.getBg());
        }
    }
}
