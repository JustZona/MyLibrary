package com.example.mylibrary.control.SlideDelete;

import android.view.View;

/**
 * Created by zy on 2017/2/21.
 */

/**
 * 用于View绑定控件和控件事件.
 */
public interface OnBindView {
    /**
     *
     * @param holder
     * @param view
     * 该行的view.
     * @param position
     * 行数.
     */
    public void bindView(SlideHolder holder, View view, int position);
}
