package com.example.mylibrary.control.SlideDelete;

/**
 * Created by zy on 2017/2/21.
 */

/**
 * 添加管理各种事件.
 */
public class SlideListener {

    /**
     * 长按修改item位置的监听.
     * 获取起始位置和修改的位置.
     * 若要时候长按修改位置，必须设置该监听，并且调用
     * Manager.dataMoveHandle方法.
     * 也可以自行写数据的置换.
     * 将Manager的Drag模式设置为true可使用.
     */
    private SlideItemDrag slideItemDrag;

    /**
     * item侧滑直接删除模式.
     */
    private OnSlideItemMoveDelete onSlideItemMoveDelete;


    public SlideListener setSlideItemDrag(SlideItemDrag slideItemDrag){
        this.slideItemDrag = slideItemDrag;
        return this;
    }

    public SlideItemDrag getSlideItemDrag(){
        return slideItemDrag;
    }

    public OnSlideItemMoveDelete getOnSlideItemMoveDelete() {
        return onSlideItemMoveDelete;
    }

    public SlideListener setOnSlideItemMoveDelete(OnSlideItemMoveDelete onSlideItemMoveDelete) {
        this.onSlideItemMoveDelete = onSlideItemMoveDelete;
        return this;
    }

}
