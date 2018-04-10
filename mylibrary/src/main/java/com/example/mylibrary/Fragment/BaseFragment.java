package com.example.mylibrary.Fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylibrary.ViewInject;
import com.example.mylibrary.ViewInjectLayout;
import com.example.mylibrary.handler.NoLeakHandler;

import java.lang.reflect.Field;


/**
 * Created by zy on 2017/7/15.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View view;
    public Application application;
    public Context context;

    public NoLeakHandler handler;

    public BaseFragment(){}

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(analysisLayout(),null);
//        analysis();
        handler = new NoLeakHandler(this){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        initView();
        return view;
    }

    /**
     * 获取一个Handler Message.
     * @return
     */
    public Message getHandlerMessage(){
        return new Message();
    }

    @Deprecated
    public void analysis(){
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(ViewInject.class)){
                ViewInject inject = field.getAnnotation(ViewInject.class);
                int id = inject.value();
                if (id>0){
                    field.setAccessible(true);
                    try {
                        field.set(this, view.findViewById(id));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public <T extends View> T $(int resId) {
        return (T) view.findViewById(resId);
    }

    private int analysisLayout(){
        Class<?> clazz = this.getClass();
        ViewInjectLayout viewInjectLayout = clazz.getAnnotation(ViewInjectLayout.class);
        if (viewInjectLayout != null) {
            return viewInjectLayout.value();
        }else {
            return  -1;
        }
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = this.getActivity().getApplication();
        context = this.getActivity();
        init();
        initWidget();
        this.initWidget();
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    public abstract void widgetClick(View v);
    public abstract void initView();
    public abstract void init();
    public abstract void initWidget();

    /**
     * 线程Handler返回.
     * @param bundle
     */
    public void bundleReturn(Bundle bundle){

    }
}
