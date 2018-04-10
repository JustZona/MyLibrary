package com.example.mylibrary.control.SlideDelete;

/**
 * Created by zy on 2017/2/22.
 */

/**
 * Drag模式事件监听.
 * 用于调整一行的位置.
 * 包括用户手动调整和代码调整(比如顶置某一行).
 */
public interface SlideItemDrag<T> {
    public void ItemDragListen(int fromPosition, int toPosition);
}
