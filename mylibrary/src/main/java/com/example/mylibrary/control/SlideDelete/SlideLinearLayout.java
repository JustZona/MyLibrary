package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.mylibrary.ViewUtil.AdaptationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zy on 2017/2/22.
 * 滑动删除.
 */
public class SlideLinearLayout extends LinearLayout{

    private View view;

    float diff = 0;
    /**
     * 点击事件判断,点击的x坐标.
     */
    private float firstDownX;
    /**
     * 点击事件判断,点击的y坐标.
     */
    private float firstDownY;
    /**
     * 点击判断是否滑动.
     */
    private boolean isSlide = false;

    /**
     * 判断手指滑动初始是上下还是左右.
     * 0:待定
     * 1:上下
     * 2:左右
     */
    private int slideXorY = 0;

    private SlideManager slideManager;
    private Scroller scroller;

    /**判断侧滑菜单出现方向.*/
    private boolean isRL = true;
    /**判断当前是否处于自动侧滑的状态.*/
    private boolean isDirect = false;
    /**判断此次点击是否有item手动滑出.*/
    private boolean isHC = false;
    /**是否是长按事件.*/
    private long firstTime = 0;
    private long lastTime = 0;
    private boolean isLongClick = false;
    private Timer timer;

    private List<SlideLinearLayout> slideLinearLayoutList;
    private SlideLinearLayout slideLinear;
    private int rightWidth = 0;
    private int leftWidth = 0;
    private int position;
    private Context context;
    private List<LinearLayout> rightViews;
    private List<LinearLayout> leftViews;

    private SlideHolder holder;
    public SlideLinearLayout(Context context, SlideManager slideManager, List<SlideLinearLayout> slideLinearLayoutList) {
        super(context);
        this.context = context;
        this.slideManager = slideManager;
        scroller = new Scroller(context);
        this.slideLinearLayoutList = slideLinearLayoutList;
        rightViews = new ArrayList<>();
        leftViews = new ArrayList<>();
        slideLinear = this;
        init();
    }

    /**
     * 初始化.
     */
    private void init(){
        this.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    /**
     * 设置右边item宽度.
     * @param rightWidth
     */
    public void setRightWidth(int rightWidth){
        this.rightWidth = rightWidth;
    }

    /**
     * 设置左边item宽度.
     * @param leftWidth
     */
    public void setLeftWidth(int leftWidth){
        this.leftWidth = leftWidth;
    }

    public void addLeftViews(LinearLayout view,LayoutParams params){
        leftViews.add(view);
        addView(view,params);
    }

    public void addRightViews(LinearLayout view,LayoutParams params){
        rightViews.add(view);
        addView(view,leftViews.size(),params);
    }

    public List<LinearLayout> getRightViews() {
        return rightViews;
    }

    public List<LinearLayout> getLeftViews() {
        return leftViews;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    /**
     * 添加子View.
     * @param view
     */
    public void setView(View view){
        this.view = view;
        LayoutParams params = new LayoutParams(AdaptationUtil.getDisplay(context).x, LayoutParams.MATCH_PARENT);
        addView(view,leftViews.size(),params);
    }

    public void setOnClick(SlideHolder holder){
        this.holder = holder;
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                slideXorY = 0;
                isLongClick = false;
                firstDownX = event.getRawX();
                firstDownY = event.getRawY();
                if (slideManager.getScroller().size()>0&&!slideManager.getScrollerLinear(this)&&slideManager.getModelNum()==2) {
                    isHC = true;
                    slideManager.removeScroller();
                    return false;
                }
                firstTime = System.currentTimeMillis();
                longOnClickTime();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(firstDownX -event.getRawX())!=0&&Math.abs(firstDownY -event.getRawY())!=0){
                    if (slideXorY==0){
                        if (Math.abs(firstDownX -event.getRawX())<Math.abs(firstDownY -event.getRawY())){
                            slideXorY = 2;
                        }else {
                            slideXorY = 1;
                        }
                    }
                    if (slideXorY==2&&isHC){
                        return false;
                    }
                    if (Math.abs(firstDownX -event.getRawX())<1&&Math.abs(firstDownY -event.getRawY())<1){
                        isSlide = false;
                    }else {
                        isSlide = true;
                        if (timer!=null){
                            timer.cancel();
                            timer = null;
                        }
                    }
                    if (!isHC&&!isDirect&&slideManager.getModelNum()==2){
                        if (isRL){
                            moveL(event);
                        }else {
                            moveR(event);
                        }
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (holder!=null&&!isHC&&!isSlide&&!isLongClick){
                    holder.OnClick(this,position,isDirect,slideManager.getScroller().size()>0?true:false);
                }
            case MotionEvent.ACTION_CANCEL:
                if (timer!=null){
                    timer.cancel();
                    timer = null;
                }
                isSlide = false;
                isHC = false;
                if (!isHC&&!isDirect&&slideManager.getModelNum()==2){
                    if (isRL){
                        if (Math.abs(getScrollX())< leftWidth /2){
                            scroller.startScroll(getScrollX(),0,-getScrollX(),0);
                            invalidate();
                        }else if (Math.abs(getScrollX())>= leftWidth /2){
                            scroller.startScroll(getScrollX(),0, leftWidth -getScrollX(),0);
                            invalidate();
                        }
                    }else {
                        if (Math.abs(getScrollX())> rightWidth /2+leftWidth){
                            scroller.startScroll(getScrollX(),0, rightWidth -getScrollX()+leftWidth,0);
                            invalidate();
                        }else if (Math.abs(getScrollX())<= rightWidth /2+leftWidth){
                            scroller.startScroll(getScrollX(),0,-(getScrollX()-leftWidth),0);
                            invalidate();
                        }
                    }
                    getParent().requestDisallowInterceptTouchEvent(false);
                    slideManager.setIsScroll(false);
                }
                break;
        }
        return false;
    }

    private void longOnClickTime(){
        if (isHC){
            return;
        }
        timer = new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                lastTime = System.currentTimeMillis();
                if (lastTime-firstTime>600&&!isSlide){
                    slideLinear.post(new Runnable() {
                        @Override
                        public void run() {
                            isLongClick = holder.OnLongClick(SlideLinearLayout.this,position,isDirect,slideManager.getScroller().size()>0?true:false);
                            if (timer!=null){
                                timer.cancel();
                                timer = null;
                            }
                        }
                    });
                }else {
                    isLongClick = false;
                }
            }
        };
        timer.schedule(task, 0,100);
    }

    /**
     * 模式为2时，手动滑动手势处理.
     * @param event
     */
    public void moveR(MotionEvent event){
        getParent().requestDisallowInterceptTouchEvent(true);
        slideManager.setIsScroll(true);
        float mYMove = event.getRawX();
        float diff = firstDownX - mYMove;
        boolean rl;
        boolean ll;
        ll = getScrollX()+diff<leftWidth;
        rl = getScrollX()+diff<= rightWidth+leftWidth;
        if (getScrollX()>=leftWidth&&rl&&!ll){
            if (getScrollX()<= rightWidth+leftWidth &&getScrollX()+diff>=0){
                scrollBy((int) diff,0);
            }else {
                scrollTo(leftWidth,0);
            }
        }else {
            if (!ll){
                scrollTo(rightWidth+leftWidth,0);
            }else {
                scrollTo(leftWidth,0);
            }
        }
        firstDownX = mYMove;
    }

    /**
     * 模式为2时，手动滑动手势处理.
     * @param event
     */
    public void moveL(MotionEvent event){
        getParent().requestDisallowInterceptTouchEvent(true);
        slideManager.setIsScroll(true);
        float mYMove = event.getRawX();
        diff = (float) (firstDownX - mYMove);
        boolean rl;
        boolean ll;
        ll = getScrollX()+diff<0;
        rl = getScrollX()+diff<= leftWidth;
        if (getScrollX()>=0&&rl&&!ll){
            if (getScrollX()<= leftWidth &&getScrollX()+diff>=0){
                scrollBy((int) diff,0);
            }else {
                scrollTo(0,0);
            }
        }else {
            if (!ll){
                scrollTo(leftWidth,0);
            }else {
                scrollTo(0,0);
            }
        }
        firstDownX = mYMove;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
        if (!isDirect){
            if (getScrollX()==leftWidth){
                slideManager.removeScroller();
            }
            if (isRL){
                if (getScrollX()==0){
                    slideManager.addScroller(this);
                }
            }else {
                if (getScrollX()==leftWidth+rightWidth){
                    slideManager.addScroller(this);
                }
            }
        }
    }
    public void scrollFY(){
        if (!isDirect){
            if (isRL){
                if (getScrollX()==0){
                    scroller.startScroll(getScrollX(),0,leftWidth,0,300);
                }
            }else {
                if (getScrollX()!=leftWidth){
                    scroller.startScroll(getScrollX(),0, -rightWidth,0,300);
                }
            }
            invalidate();
            slideManager.setIsScroll(false);
        }
    }

    /**
     * 逐渐滑动.
     */
    public void directOperation(boolean isDirect){
        if (!this.isDirect){
            this.isDirect = isDirect;
            if (isRL){
                scroller.startScroll(leftWidth,0, rightWidth,0,200);
            }else {
                scroller.startScroll(leftWidth,0,-leftWidth,0,200);
            }
            invalidate();
        }
    }

    /**
     * 直接滑动.
     */
    public void directOperationI(boolean isDirect){
        if (!this.isDirect){
            this.isDirect = isDirect;
            if (isRL){
                scrollTo(leftWidth+rightWidth,0);
            }else {
                scrollTo(0,0);
            }
            invalidate();
        }
    }

    /**
     * 逐渐复原.
     */
    public void directRestore(boolean isDirect){
        if (this.isDirect){
            this.isDirect = isDirect;
            if (isRL){
                scroller.startScroll(rightWidth+leftWidth,0,-rightWidth,0,300);
            }else {
                scroller.startScroll(0,0, leftWidth,0,300);
            }
            invalidate();
        }
    }

    public void setIsRL(boolean isRL){
        this.isRL = isRL;
    }

    public boolean getIsRL(){
        return isRL;
    }

}
