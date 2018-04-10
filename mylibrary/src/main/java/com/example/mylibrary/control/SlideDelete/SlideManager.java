package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.example.mylibrary.MyException.MyException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zy on 2017/2/21.
 */

public class SlideManager implements View.OnTouchListener,OnBindView{

    /**
     * SLIDE:滑动删除模式.
     * Swip:侧滑删除模式.
     * CANCEL:普通模式.
     */
    public enum Model{
        SLIDE,
        Swip,
        CANCEL
    }
    private boolean isDrag = false;
    private SlideCallBack callBack;
    private RecyclerView recyclerView;
    private SlideAdapter adapter;
    private List mItems;
    private SlideListener slideListener;
    private SlideItemCreator slideItemCreator;
    private SlideMenuItem slideRightMenuItem;
    private SlideMenuItem slideLeftMenuItem;
    private int modelNum = 0;
    private boolean isScroll = false;
    private LinearLayoutManager manager;
    private boolean isRL = true;
    private boolean isDirect = false;
    private List<SlideLinearLayout> scroller;
    private Model model;
    /**
     * 点击事件判断,点击的x坐标.
     */
    private float firstDownX;
    /**
     * 点击事件判断,点击的y坐标.
     */
    private float firstDownY;
    protected SlideManager(Context context, List mItems, boolean isRL, SlideAdapter adapter, RecyclerView recyclerView, SlideListener slideListener, SlideItemCreator slideItemCreator){
        this.recyclerView = recyclerView;
        this.slideListener = slideListener;
        this.slideItemCreator = slideItemCreator;
        slideRightMenuItem = new SlideMenuItem(context);
        slideLeftMenuItem = new SlideMenuItem(context);
        this.adapter = adapter;
        this.isRL = isRL;
        adapter.setSlideManager(this);
        recyclerView.setAdapter(adapter);
        callBack = new SlideCallBack(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callBack);
        touchHelper.attachToRecyclerView(recyclerView);
        manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        callBack.setSwipe(false);
        recyclerView.setOnTouchListener(null);
        this.modelNum = 0;
        this.mItems = mItems;
        scroller = new ArrayList<>();
    }

    public void setModelNum(Model model){
        this.model = model;
        switch (model){
            case Swip:
                callBack.setSwipe(true);
                recyclerView.setOnTouchListener(null);
                this.modelNum = 1;
                break;
            case SLIDE:
                callBack.setSwipe(false);
                recyclerView.setOnTouchListener(this);
                this.modelNum = 2;
                break;
            case CANCEL:
                callBack.setSwipe(false);
                recyclerView.setOnTouchListener(null);
                this.modelNum = 0;
                break;
        }
    }

    /**获取当前模式.*/
    public Model getModel() {
        return model;
    }

    /**
     * 是否开启长按修改位置.
     * @param isDrag
     */
    public void setDrag(boolean isDrag){
        this.isDrag = isDrag;
        callBack.setDrag(isDrag);
    }

    /**
     * 返回是否开启长按修改位置.
     * @return
     */
    public boolean getDrag(){
        return isDrag;
    }

    /**
     * 返回model;
     * @return
     * 0:普通模式.
     * 1:侧滑删除.
     * 2:滑动删除.
     */
    public int getModelNum(){
        return modelNum;
    }

    /**
     * 手势的拦截.
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("isScroll:"+isScroll);
        return isScroll;
    }

    /**
     * 手势的拦截
     * @param isScroll
     * 避免滑动删除与RecyclerView
     * 产生冲突.
     */
    protected void setIsScroll(boolean isScroll){
        this.isScroll = isScroll;
    }


    /**
     *
     * @param holder
     * @param view
     * 该行的view.
     * @param position
     */
    @Override
    public void bindView(SlideHolder holder, View view, int position) {
        SlideLinearLayout layout = (SlideLinearLayout) view;
        layout.setPosition(position);
        layout.setOnClick(holder);
    }



    /**
     * 置顶item.
     * @param fromPosition
     * 起始位置.
     * @param toPosition
     * 终止位置.
     * 是否将该位置移动变化发送给ItemDragListen接口.
     */
    public void moveItemTop(int fromPosition, int toPosition){
        adapter.notifyItemMoved(fromPosition, toPosition);
        adapter.notifyItemRangeChanged(0,mItems.size());
    }

    /**
     * 长按移动.
     * @param fromPosition 起止位置.
     * @param toPosition 终止位置.
     */
    public void moveDrag(int fromPosition, int toPosition){
        movePosition(fromPosition,toPosition);
    }

    /**
     * 删除item
     * @param position 删除位置.
     */
    public void removeItem(int position){
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
        if (modelNum ==1){
            if(slideListener.getOnSlideItemMoveDelete()!=null){
                slideListener.getOnSlideItemMoveDelete().slideItemMoveDelete(position);
            }
        }
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(0,mItems.size());
    }

    /**
     * 向接口传递移动位置.
     * @param toPosition 终止
     * @param fromPosition 起始
     */
    public void movePosition(int fromPosition, int toPosition){
        if (slideListener.getSlideItemDrag()!=null&&fromPosition!=toPosition){
            slideListener.getSlideItemDrag().ItemDragListen(fromPosition,toPosition);
        }
    }

    /**
     * 在进行顶置操作时数据通用处理.
     * @param fromPosition 处理数据的q起始位置.
     * @param data 处理的数据.
     */
    public void dataTopHandle(int fromPosition,List<List> data){
        if (data==null){
            throw new NullPointerException("data is Null");
        }
        if (fromPosition<0){
            throw new NegativeArraySizeException("fromPosition less than 0");
        }
        for(int i = 0;i<data.size();i++){
            List list = data.get(i);
            Object fromOb = list.get(fromPosition);
            list.remove(fromPosition);
            list.add(0,fromOb);
        }
    }

    /**
     * 在进行数据移动操作时数据通用处理.
     * @param fromPosition 处理数据的起始位置.
     * @param toPosition 处理数据的最终位置.
     * @param data 处理的数据.
     */
    public void dataMoveHandle(int fromPosition, int toPosition,List<List> data){
        if (data==null){
            new MyException("data is Null");
        }
        if (fromPosition<0){
            new MyException("fromPosition less than 0");
        }
        if (toPosition<0){
            new MyException("toPosition less than 0");
        }
        for(int i = 0;i<data.size();i++){
            List list = data.get(i);
            list.set(fromPosition,list.set(toPosition,list.get(fromPosition)));
//            Collections.swap(list, fromPosition, toPosition);
        }
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter.notifyItemMoved(fromPosition, toPosition);
        adapter.notifyItemRangeChanged(0,mItems.size());
    }

    public boolean getIsRL(){
        return isRL;
    }

    public SlideItemCreator getSlideItemCreator(){
        return slideItemCreator;
    }

    public SlideMenuItem getSlideRightMenuItem(){
        return slideRightMenuItem;
    }

    public SlideMenuItem getSlideLeftMenuItem() {
        return slideLeftMenuItem;
    }

    /**
     * 通过代码方式直接将侧滑栏滑出.
     */
    public void directOperation(){
        if (scroller.size()==0){
            isDirect = true;
            adapter.directOperation(isDirect);
        }
    }
    /**
     * 通过代码方式直接将测滑栏复原．
     */
    public void directRestore(){
        if (scroller.size()==0){
            isDirect = false;
            adapter.directRestore(isDirect);
        }
    }

    public boolean isDirect() {
        return isDirect;
    }

    public void addScroller(SlideLinearLayout scroller){
        if (!this.scroller.contains(scroller)){
            this.scroller.add(scroller);
        }
    }
    public void removeScroller(){
        if (scroller.size()>0){
            this.scroller.get(0).scrollFY();
            this.scroller.removeAll(scroller);
        }
    }

    public List<SlideLinearLayout> getScroller() {
        return scroller;
    }

    public boolean getScrollerLinear(SlideLinearLayout scroller){
        if (this.scroller.contains(scroller)){
            return true;
        }
        return false;
    }
}
