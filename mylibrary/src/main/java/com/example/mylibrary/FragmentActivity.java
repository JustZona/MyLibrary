package com.example.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.example.mylibrary.handler.NoLeakHandler;

import java.lang.reflect.Field;


/**
 * Created by zy on 2017/7/7.
 */

public abstract class FragmentActivity extends android.support.v4.app.FragmentActivity implements View.OnClickListener{

    public Context context = null;

    public NoLeakHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        analysisLayout();
//        analysis();
        handler = new NoLeakHandler(this){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                bundleReturn(bundle);
            }
        };
        initView();
        MyActivityManager.getInstance().addActivity(this);
        init();
        initWidget();

    }

    /**
     * 获取一个Intent.
     * @return
     */
    public Intent getNewIntent(){
        return new Intent();
    }

    /**
     * Activity跳转.
     * @return
     */
    public void activityTo(Class<?> cls,Bundle bundle){
        Intent intent = new Intent(context,cls);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void activityTo(Class<?> cls){
        activityTo(cls,null);
    }

    /**
     * 获取一个Handler Message.
     * @return
     */
    public Message getHandlerMessage(){
        return new Message();
    }

    /**
     * Activity跳转.
     * @return
     */
    public void activityToForResult(Class<?> cls,Bundle bundle,int requestCode){
        Intent intent = new Intent(context,cls);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * Activity跳转.
     * @return
     */
    public void activityToForResult(Class<?> cls,int requestCode){
        activityToForResult(cls,null,requestCode);
    }

    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
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
                        field.set(this, this.findViewById(id));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void analysisLayout(){
        Class<?> clazz = this.getClass();
        ViewInjectLayout viewInjectLayout = clazz.getAnnotation(ViewInjectLayout.class);
        if (viewInjectLayout != null) {
            setContentView(viewInjectLayout.value());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getInstance().finishActivity(this);
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
