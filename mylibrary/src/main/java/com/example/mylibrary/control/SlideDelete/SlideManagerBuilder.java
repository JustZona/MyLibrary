package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by zy on 2017/2/21.
 */

public class SlideManagerBuilder {

    private Context context;
    private RecyclerView recyclerView;
    private List items;
    private SlideListener slideListener;
    private SlideItemCreator slideItemCreator;
    private SlideAdapter slideAdapter;
    private boolean isRL = false;
    public SlideManagerBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public SlideManagerBuilder setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }


    public SlideManagerBuilder setItems(List items) {
        this.items = items;
        return this;
    }

    public SlideManagerBuilder setSlideListener(SlideListener slideListener) {
        this.slideListener = slideListener;
        return this;
    }

    public SlideManagerBuilder setSlideItemCreator(SlideItemCreator slideItemCreator){
        this.slideItemCreator = slideItemCreator;
        return this;
    }

    public SlideManagerBuilder setSlideAdapter(SlideAdapter slideAdapter){
        this.slideAdapter = slideAdapter;
        return this;
    }
    public SlideManagerBuilder setIsRL(boolean isRL){
        this.isRL = isRL;
        return this;
    }

    public SlideManager create(){
        if (context!=null&&recyclerView!=null&&items!=null&&slideListener!=null&&items!=null){
            return new SlideManager(context,items,isRL,slideAdapter,recyclerView,slideListener,slideItemCreator);
        }
        throw new NullPointerException("Context,items,slideAdapter,RecyclerView,List,SlideListener,layout is Null");
    }
}
