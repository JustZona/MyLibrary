package com.example.mylibrary;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.mylibrary.handler.NoLeakHandler;


public class BaseService extends Service {

    private MainBinder mainBinder = new MainBinder();

    public NoLeakHandler handler;

    public BaseService() {
        handler = new NoLeakHandler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                bundleReturn(bundle);
            }
        };
    }

    /**
     * 获取一个Handler Message.
     * @return
     */
    public Message getHandlerMessage(){
        return new Message();
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
        Intent intent = new Intent(getApplicationContext(),cls);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void activityTo(Class<?> cls){
        activityTo(cls,null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  mainBinder;
    }

    public class MainBinder extends Binder{

        public BaseService getService(){
            return BaseService.this;
        }

    }

    /**
     * 线程Handler返回.
     * @param bundle
     */
    public void bundleReturn(Bundle bundle){

    }
}
