package com.example.mylibrary.control.Refresh;

/**
 * Created by zy on 2017/2/18.
 * 下拉刷新事件监听.
 */
public interface onRefreshListener {
    /**
     * @param isUnderTouch
     * 子控件是否处于被下拉状态.
     * @param status
     * 子控件下拉正处于的状态.
     * 1:正常状态.
     * 2:处于被下拉状态.
     * 3:刷新状态.
     * 4:刷新完成状态.
     * @param progress
     * 子控件被下来的高度.
     */
    void onRefreshChange(boolean isUnderTouch, byte status, float progress);

    /**
     * 开始下拉.
     */
    void onUIRefreshBegin();

    /**
     * 刷新完成.
     */
    void onUIRefreshComplete();
}
