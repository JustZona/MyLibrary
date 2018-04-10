package com.example.mylibrary.control.SlideDelete;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by zy on 2017/2/21.
 */

public class SlideCallBack extends ItemTouchHelper.Callback {

    private boolean isDrag = false;
    private boolean isSwipe = false;

    private ItemTouchHelperAdapter mAdapter;

    public boolean isDrag() {
        return isDrag;
    }

    public void setDrag(boolean drag) {
        isDrag = drag;
    }

    public boolean isSwipe() {
        return isSwipe;
    }

    public void setSwipe(boolean swipe) {
        isSwipe = swipe;
    }

    public SlideCallBack(ItemTouchHelperAdapter adapter){
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isDrag;
    }
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipe;
    }
}
