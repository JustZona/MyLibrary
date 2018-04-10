package com.example.mylibrary.control.PicBanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mylibrary.MyActivityManager;
import com.example.mylibrary.ThreadRun.ThreadPoolFramework;
import com.example.mylibrary.control.PicBanner.bottom.CircularRelativeLayout;
import com.example.mylibrary.control.PicBanner.bottom.NandTRelativeLayout;
import com.example.mylibrary.control.PicBanner.bottom.NumberRelativeLayout;
import com.example.mylibrary.control.PicBanner.bottom.TitleRelativeLayout;
import com.example.mylibrary.control.ViewPager.SlideOrNoViewPager;

import java.util.List;


/**
 * Created by zy on 2017/8/15.
 */

public class CarouselView extends RelativeLayout{

    private Context context;
    /**view显示.*/
    private SlideOrNoViewPager viewPager;
    /**底部显示.*/
    private CarouselRelativeLayout viewCircular;

    /**初始化参数.*/
    private CarouselBuilder carouselBuilder;

    /** Adapter.*/
    private CarouselAdapter carouselAdapter;

    private ViewPager.OnPageChangeListener onPageChangeListener;

    private List<String> titles;

    /**播放间隔时间.*/
    private long playDelay = 1000;

    /**是否开启播放.*/
    private boolean playStart = true;

    /**当前页面.*/
    public int thisPosition = 0;

    /**页面滑动动画效果.*/
    private ViewPager.PageTransformer pageTransformer;

    public CarouselView(Context context) {
        super(context);
        init(context);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        viewPager = new SlideOrNoViewPager(context);
        viewPager.setCanScroll(true);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(layoutParams);
        addView(viewPager);
    }

    public CarouselAdapter getCarouselAdapter() {
        return carouselAdapter;
    }

    /**设置标题.*/
    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    /**获取ViewPager*/
    public SlideOrNoViewPager getViewPager() {
        return viewPager;
    }

    /**获取构建参数对象.*/
    public CarouselBuilder getCarouselBuilder() {
        return carouselBuilder;
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    public List<String> getTitles() {
        return titles;
    }

    /**
     * 设置adapter.
     * @param carouselAdapter
     * @return
     */
    public void setCarouselAdapter(CarouselAdapter carouselAdapter) {
        if (viewCircular!=null){
            removeView(viewCircular);
        }
        if (carouselBuilder==null){
            carouselBuilder = new CarouselBuilder();
        }
        playStart = false;
        this.carouselAdapter = carouselAdapter;
        initCarouselBottom();
        initViewPager();
    }

    public void setCarouselBuilder(CarouselBuilder carouselBuilder) {
        this.carouselBuilder = carouselBuilder;
    }

    /**自定义底部栏.*/
    public void setViewCircular(CarouselRelativeLayout viewCircular) {
        this.viewCircular = viewCircular;
    }

    public void initCarouselBottom(){
        if(viewCircular==null){
            switch (carouselBuilder.getMODEL()){
                case 0:
                    viewCircular = new CircularRelativeLayout(context);
                    viewCircular.initL(carouselBuilder,carouselAdapter.getCount());
                    ((CircularRelativeLayout)viewCircular).setPosition(thisPosition);
                    break;
                case 1:
                    viewCircular = new NumberRelativeLayout(context);
                    viewCircular.initL(carouselBuilder,carouselAdapter.getCount());
                    ((NumberRelativeLayout)viewCircular).setPositionTitle(thisPosition);
                    break;
                case 2:
                    viewCircular = new TitleRelativeLayout(context);
                    viewCircular.initL(carouselBuilder,carouselAdapter.getCount());
                    if (titles!=null){
                        ((TitleRelativeLayout)viewCircular).setTextTitle(titles.get(thisPosition));
                    }else {
                        ((TitleRelativeLayout)viewCircular).setTextTitle("");
                    }
                    break;
                case 3:
                    viewCircular = new NandTRelativeLayout(context);
                    viewCircular.initL(carouselBuilder,carouselAdapter.getCount());
                    if (titles!=null){
                        ((NandTRelativeLayout)viewCircular).setTvAndTitle(thisPosition,titles.get(thisPosition));
                    }else {
                        ((NandTRelativeLayout)viewCircular).setTvAndTitle(thisPosition,"");
                    }
                    break;
                case 4:
                    viewCircular = null;
                    break;
            }
        }
        if (viewCircular!=null){
            addView(viewCircular);
        }
    }

    public void initViewPager(){
        if (carouselBuilder.getPageTransformer()!=null){
            viewPager.setPageTransformer(true,carouselBuilder.getPageTransformer());
        }
        viewPager.setAdapter(carouselAdapter);
        viewPager.setCurrentItem(carouselBuilder.getStart());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageChangeListener!=null){
                    onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageChangeListener!=null){
                    onPageChangeListener.onPageSelected(position);
                }
                if (viewCircular!=null){
                    thisPosition = position;
                    switch (carouselBuilder.getMODEL()){
                        case 0:
                            ((CircularRelativeLayout)viewCircular).setPosition(position);
                            break;
                        case 1:
                            ((NumberRelativeLayout)viewCircular).setPositionTitle(position);
                            break;
                        case 2:
                            if (titles!=null){
                                ((TitleRelativeLayout)viewCircular).setTextTitle(titles.get(position));
                            }else {
                                ((TitleRelativeLayout)viewCircular).setTextTitle("");
                            }
                            break;
                        case 3:
                            if (titles!=null){
                                ((NandTRelativeLayout)viewCircular).setTvAndTitle(position,titles.get(position));
                            }else {
                                ((NandTRelativeLayout)viewCircular).setTvAndTitle(position,"");
                            }
                            break;

                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onPageChangeListener!=null){
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public long getPlayDelay() {
        return playDelay;
    }

    /**
     * 设置播放间隔时间.
     * @param playDelay
     */
    public void setPlayDelay(long playDelay) {
        this.playDelay = playDelay;
        if (thisPosition!=0){
            carouselBuilder.setStart(thisPosition);
        }
    }

    /**开启播放.*/
    public void openPlay(){
        playStart = true;
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                while(playStart){
                    try {
                        Thread.sleep(playDelay);
                        MyActivityManager.getInstance().currentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (carouselAdapter != null){
                                    if (thisPosition == carouselAdapter.getCount()-1){
                                        thisPosition = 0;
                                    }else {
                                        thisPosition+=1;
                                    }
                                    viewPager.setCurrentItem(thisPosition);
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**停止播放.*/
    public void stopPlay(){
        suspend();
        thisPosition = 0;
    }

    /**暂停播放.*/
    public void suspend(){
        playStart = false;
    }

    /**
     * 隐藏底部.
     */
    public void hideBottom(){
        viewCircular.setVisibility(GONE);
    }

    /**
     * 隐藏底部.
     */
    public void displayBottom(){
        viewCircular.setVisibility(VISIBLE);
    }

}
