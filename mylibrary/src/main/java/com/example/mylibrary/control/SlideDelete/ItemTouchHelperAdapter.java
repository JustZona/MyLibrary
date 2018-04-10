package com.example.mylibrary.control.SlideDelete;

/**
 * Created by zy on 2017/2/21.
 */

public interface ItemTouchHelperAdapter {
    void onBindViewHolder(SlideHolder holder, int position);

    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
