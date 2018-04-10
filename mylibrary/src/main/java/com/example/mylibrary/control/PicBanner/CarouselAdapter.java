package com.example.mylibrary.control.PicBanner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zy on 2017/8/17.
 */

public abstract class CarouselAdapter extends PagerAdapter {

    private List<View> idViewMap;

    public abstract View getView(ViewGroup container,int position);

    public abstract int getCount();

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = container.getChildAt(position);
        if(itemView==null){
            itemView = getView(container,position);
            container.addView(itemView);
        }
        onBind(itemView,position);
        return itemView;
    }

    public void onBind(View view,int position){

    }

}
