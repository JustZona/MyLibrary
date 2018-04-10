package com.example.mylibrary.control.SlideDelete;

/**
 * Created by zy on 2017/2/22.
 */

public interface SlideItemCreator {
    /**
     * 创建View滑动右侧的item.
     * @param slideMenuItem
     * @param position
     */
    public void onCreateRightMenu(SlideMenuItem slideMenuItem, int position);

    /**
     * 创建View滑动左侧的item.
     * @param slideMenuItem
     * @param position
     */
    public void onCreateLeftMenu(SlideMenuItem slideMenuItem, int position);
}
