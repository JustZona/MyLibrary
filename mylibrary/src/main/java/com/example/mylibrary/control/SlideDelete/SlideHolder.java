package com.example.mylibrary.control.SlideDelete;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zy on 2017/2/21.
 * holder.
 * 用户需要自行创建，继承SlideHolder.
 */

public abstract class SlideHolder extends RecyclerView.ViewHolder{

    private View view;

    public SlideHolder(SlideLinearLayout itemView) {
        super(itemView);
        this.view = itemView;
    }

    public View getView(){
        return  view;
    }

    /**
     * 在holder创建之后，返回manager.
     * @param slideManager
     */
    public abstract void returnManager(SlideManager slideManager);
    /**
     * 当前行点击事件.
     * @param v
     * @param position
     * 当前行数.
     * @param isDirect
     * 按钮滑出的菜单栏是否处于滑出状态.
     * @param isSDDirect
     * 手动滑出的菜单栏是否处于滑出状态.
     */
    public abstract void OnClick(SlideLinearLayout v, int position, boolean isDirect,boolean isSDDirect);

    /**
     * 当前行长按事件.
     * @param v
     * @param position   当前行数.
     * @param isDirect   按钮滑出的菜单栏是否处于滑出状态.
     * @param isSDDirect
     * @return
     * 如果为true：OnClick则接受不到点击事件.
     * 如果为false：OnClick则可以继续接受到点击事件.
     * 注：当drag设置为true时，无论返回为true还是false，在长按之后点击均没办法接收到事件.
     * 另外，建议当模式为Swip的时候，禁止长按，因为在swip模式删除时，可能会造成长按事件误触.
     */
    public abstract boolean OnLongClick(SlideLinearLayout v, int position, boolean isDirect,boolean isSDDirect);
}
