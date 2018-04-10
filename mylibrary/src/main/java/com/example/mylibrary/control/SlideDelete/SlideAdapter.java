package com.example.mylibrary.control.SlideDelete;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2017/2/21.
 * Adapter.
 * 用户需要自行创建Adapter，继承SlideAdapter.
 */

public abstract class SlideAdapter<VH extends SlideHolder> extends RecyclerView.Adapter<SlideHolder> implements ItemTouchHelperAdapter {

    private List<?> mItems;
    private Context context;
    private SlideManager slideManager;
    private List<SlideLinearLayout> slideLinearLayoutList;

    public SlideAdapter(Context context){
        this.context = context;
        slideLinearLayoutList = new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        SlideLinearLayout slideLinearLayout = new SlideLinearLayout(context,slideManager,slideLinearLayoutList);
        slideLinearLayout.setIsRL(slideManager.getIsRL());
        if (slideManager.getSlideItemCreator()!=null){
            slideManager.getSlideItemCreator().onCreateRightMenu(slideManager.getSlideRightMenuItem(),0);
            slideManager.getSlideItemCreator().onCreateLeftMenu(slideManager.getSlideLeftMenuItem(),0);
            if (slideManager.getSlideLeftMenuItem().slideItemBuilders.size()>0){
                slideLinearLayout.setLeftWidth(slideManager.getSlideLeftMenuItem().setLeftLinearLayout(slideLinearLayout));
            }
            if (slideManager.getSlideRightMenuItem().slideItemBuilders.size()>0){
                slideLinearLayout.setRightWidth(slideManager.getSlideRightMenuItem().setRightLinearLayout(slideLinearLayout));
            }
        }
        if (slideManager.isDirect()){
            slideLinearLayout.directOperationI(true);
        }
        slideLinearLayoutList.add(slideLinearLayout);
        VH holder = null;
        if (slideLinearLayout.getChildCount()!=0){
            LinearLayout left = slideLinearLayout.getLeftViews().size()>0?slideLinearLayout.getLeftViews().get(0):null;
            LinearLayout right = slideLinearLayout.getRightViews().size()>0?slideLinearLayout.getRightViews().get(0):null;
            holder = onCreateView(parent,viewType,slideLinearLayout,left,right);
        }else {
            holder = onCreateView(parent,viewType,slideLinearLayout);
        }

        return holder;
    }

    /**
     * @param parent
     * @param viewType
     * @param slideLinearLayout
     * @param view
     * 添加的左右侧滑菜单,
     * 取值顺序为从左向右:view[0]为左菜单,view[1]为右菜单
     * 可以通过该值进行findViewById等操作，同时可以为里面值进行单独的事件设置.
     * @return
     */
    public abstract VH  onCreateView(ViewGroup parent, int viewType, SlideLinearLayout slideLinearLayout, LinearLayout... view);

    public abstract void  onBindView(VH holder, int position);

    @Override
    public void onBindViewHolder(SlideHolder holder, int position) {
        slideManager.bindView(holder,holder.getView(),position);
        onBindView((VH) holder,position);
    }

    @Override
    public int getItemCount() {
        this.mItems =  getMItemsSize();
        return mItems.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        slideManager.moveDrag(fromPosition,toPosition);
    }

    public void setSlideManager(SlideManager slideManager){
        this.slideManager = slideManager;
    }

    @Override
    public void onItemDismiss(int position) {
        slideManager.removeItem(position);
    }

    public abstract List<?> getMItemsSize();

    /**
     * 通过代码方式直接将测滑栏弹出．
     */
    public void directOperation(boolean isDirect){
        for (int i = 0; i < slideLinearLayoutList.size(); i++) {
            slideLinearLayoutList.get(i).directOperation(isDirect);
        }
    }

    /**
     * 通过代码方式直接将测滑栏复原．
     */
    public void directRestore(boolean isDirect){
        for (int i = 0; i < slideLinearLayoutList.size(); i++) {
            slideLinearLayoutList.get(i).directRestore(isDirect);
        }
    }

}
