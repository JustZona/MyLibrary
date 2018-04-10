package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2017/2/22.
 */

/**
 * 添加右侧滑动的item.
 * */
public class SlideMenuItem {
    private LinearLayout linearLayout;
    protected List<SlideItemBuilder> slideItemBuilders;
    private Context context;
    private int rightWidth = 0;
    private int leftWidth = 0;
    private LinearLayout.LayoutParams params=null;
    public SlideMenuItem(Context context){
        this.context = context;
        slideItemBuilders = new ArrayList<>();
    }

    /**
     * 保存添加的item参数.
     * @param slideItemBuilder
     */
    public void addItem(SlideItemBuilder slideItemBuilder){
        slideItemBuilder.setContext(context);
        slideItemBuilders.add(slideItemBuilder);
    }

    /**
     * 获取SlideItemBuilder中需要添加的
     * 右侧滑动item,并返回所有item的宽度.
     * @param linearLayout
     * @return
     */
    protected int setRightLinearLayout(SlideLinearLayout linearLayout){
        this.linearLayout = linearLayout;
        if (linearLayout.getChildCount()-1<slideItemBuilders.size()){
            rightWidth = 0;
            LinearLayout linearLayout1 = new LinearLayout(context);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            for (int i =0;i<slideItemBuilders.size();i++){
                SlideItemBuilder slideItemBuilder = slideItemBuilders.get(i);
                if (slideItemBuilder.getView()==null){
                    SlideItem slideItem = slideItemBuilder.create();
                    slideItem.setText(slideItemBuilder.getText());
                    slideItem.setTextColor(slideItemBuilder.getTextColor());
                    slideItem.setBackgroundColor(slideItemBuilder.getBgColor());
                    if (slideItemBuilder.getTextSize()!=-1){
                        slideItem.setTextSize(TypedValue.COMPLEX_UNIT_PX,slideItemBuilder.getTextSize());
                    }
                    if (slideItemBuilder.getBg()!=-1){
                        slideItem.setBackgroundResource(slideItemBuilder.getBg());
                    }
                    if (slideItemBuilder.getDrawable()!=null){
                        slideItem.setBackground(slideItemBuilder.getDrawable());
                    }
                    params = new LinearLayout.LayoutParams(slideItemBuilder.getWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayout1.addView(slideItem,params);
                }else {
                    View view = slideItemBuilder.getView();
                    params = new LinearLayout.LayoutParams(slideItemBuilder.getWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayout1.addView(view,params);
                }
                rightWidth += slideItemBuilder.getWidth();
            }
            params = new LinearLayout.LayoutParams(rightWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.addRightViews(linearLayout1,params);
        }
        slideItemBuilders.clear();
        return rightWidth;
    }

    /**
     * 获取SlideItemBuilder中需要添加的
     * 右侧滑动item,并返回所有item的宽度.
     * @param linearLayout
     * @return
     */
    protected int setLeftLinearLayout(SlideLinearLayout linearLayout){
        this.linearLayout = linearLayout;
        if (linearLayout.getChildCount()-1<slideItemBuilders.size()){
            leftWidth = 0;
            LinearLayout linearLayout1 = new LinearLayout(context);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            for (int i =0;i<slideItemBuilders.size();i++){
                SlideItemBuilder slideItemBuilder = slideItemBuilders.get(i);
                if (slideItemBuilder.getView()==null){
                    SlideItem slideItem = slideItemBuilder.create();
                    slideItem.setText(slideItemBuilder.getText());
                    slideItem.setTextColor(slideItemBuilder.getTextColor());
                    slideItem.setBackgroundColor(slideItemBuilder.getBgColor());
                    if (slideItemBuilder.getTextSize()!=-1){
                        slideItem.setTextSize(TypedValue.COMPLEX_UNIT_PX,slideItemBuilder.getTextSize());
                    }
                    if (slideItemBuilder.getBg()!=-1){
                        slideItem.setBackgroundResource(slideItemBuilder.getBg());
                    }
                    if (slideItemBuilder.getDrawable()!=null){
                        slideItem.setBackground(slideItemBuilder.getDrawable());
                    }
                    params = new LinearLayout.LayoutParams(slideItemBuilder.getWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayout1.addView(slideItem,params);
                }else {
                    View view = slideItemBuilder.getView();
                    params = new LinearLayout.LayoutParams(slideItemBuilder.getWidth(), ViewGroup.LayoutParams.MATCH_PARENT);
                    linearLayout1.addView(view,params);
                }
                leftWidth += slideItemBuilder.getWidth();
            }
            params = new LinearLayout.LayoutParams(leftWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.addLeftViews(linearLayout1,params);
            linearLayout.scrollTo(leftWidth,0);
        }
        slideItemBuilders.clear();
        return leftWidth;
    }

    public int getRightWidth(){
        return rightWidth;
    }

    public int getLeftWidth() {
        return leftWidth;
    }
}
