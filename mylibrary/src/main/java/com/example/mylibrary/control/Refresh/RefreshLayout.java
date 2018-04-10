package com.example.mylibrary.control.Refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mylibrary.control.Refresh.ptrlib.PtrDefaultHandler;
import com.example.mylibrary.control.Refresh.ptrlib.PtrFrameLayout;
import com.example.mylibrary.control.Refresh.ptrlib.PtrHandler;
import com.example.mylibrary.control.Refresh.ptrlib.PtrUIHandler;
import com.example.mylibrary.control.Refresh.ptrlib.indicator.PtrIndicator;


/**
 * Created by zy on 2017/2/13.
 */

public class RefreshLayout extends PtrFrameLayout {

    private Context context;
    private PtrUIHandler uiHandler;
    private int progress = 0;
    private float progressHeight;
    private Header head;
    private onRefreshListener refreshListener;
    private int lastX;
    private int lastY;
    private Boolean isRefresh;
    private Boolean isClose = false;
    private int slideXorY = 0;
    public RefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        initHeader();
        initUIHandler();
        setPullToRefresh(true);
        setRatioOfHeaderHeightToRefresh(1.01f);
        setDurationToClose(300);
        setDurationToCloseHeader(500);
        setKeepHeaderWhenRefresh(true);
        progressHeight = getRatioOfHeaderToHeightRefresh();
    }

    private void initHeader(){
        head = new Header(context);
        setHeaderView(head);
    }

    /**
     * 初始化刷新的事件监听.
     */
    private void initUIHandler(){
        uiHandler = new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {
                if (refreshListener!=null){
                    refreshListener.onUIRefreshBegin();
                }
            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                if (refreshListener!=null){
                    refreshListener.onRefreshChange(isUnderTouch,status,ptrIndicator.getCurrentPercent()/progressHeight);
                }
                if (head!=null){
                    switch (status){
                        case 1:
                            if (refreshListener!=null){
                                refreshListener.onUIRefreshComplete();
                            }
                            head.setModel(Header.Model.NORMAL);
                            break;
                        case 2:
                            System.out.println(status);
                            if (ptrIndicator.getCurrentPercent()>=progressHeight){
                                head.setModel(Header.Model.PROMPT);
                            }else {
                                head.setModel(Header.Model.NORMAL);
                            }
                            break;
                        case 3:
                            head.setModel(Header.Model.ING);
                            break;
                        case 4:
                            head.setModel(Header.Model.COMPLETE);
                            break;
                    }
                }
            }
        };
        addPtrUIHandler(uiHandler);
    }

    /**
     * 是否开启下拉.
     * @param isRefresh
     */
    public void setPullToRefresh(boolean isRefresh){
        this.isRefresh = isRefresh;
        setRefresh(isRefresh);
    }

    private void setRefresh(boolean Is){
        if (Is){
            setPtrHandler(new PtrHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                }
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

            });
        }else {
            setPtrHandler(new PtrHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                }
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return false;
                }

            });
        }
    }

    /**
     * 事件的拦截.
     * 当子控件包含有侧滑删除时避免冲突.
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                slideXorY = 0;
                this.lastX = (int) ev.getX();
                this.lastY = (int) ev.getY();
                isClose = false;
                if (isRefresh){
                    setRefresh(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                float reX = ev.getX() - this.lastX;
//                float reY = ev.getY() - this.lastY;
//                if (slideXorY==0){
//                    if (Math.abs(reX)+0.1 > Math.abs(reY)){
//                        slideXorY = 2;
//                    }else {
//                        slideXorY = 1;
//                    }
//                }
//                if (slideXorY==2){
//                    setRefresh(false);
//                    isClose = true;
//                    return false;
//                }
                break;
            case MotionEvent.ACTION_UP:
                if (isRefresh){
                    setRefresh(true);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setProgress(int progress){
        head.setProgress(progress);
    }

    /**
     * 自定义PrtHandler.
     * 通过PtrHandler，可以检查确定是否可以下来刷新以及在合适的时间刷新数据。
     * @param handler
     * setPtrHandler(handler);
     */

    /**
     * 重写PrtUIHandler,
     * addPtrUIHandler(handler);
     */

    /**
     * 设置head.
     * setHeaderView(view);
     */
    public void setHead(View view){
        removeAllViews();
        head = null;
        setHeaderView(view);
    }

    public void setRefreshListener(onRefreshListener refreshListener){
        this.refreshListener = refreshListener;
    }

}
