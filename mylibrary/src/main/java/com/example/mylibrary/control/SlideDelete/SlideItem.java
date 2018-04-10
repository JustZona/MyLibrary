package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by zy on 2017/2/22.
 */

public class SlideItem extends TextView {

    protected SlideItem(Context context) {
        super(context);
        init();
    }
    private void init(){
        this.setGravity(Gravity.CENTER);
    }
}
