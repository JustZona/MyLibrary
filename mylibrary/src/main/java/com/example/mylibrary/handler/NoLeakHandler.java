package com.example.mylibrary.handler;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by zy on 2018/2/24.
 */

public class NoLeakHandler extends Handler {

    private WeakReference<Activity> mActivity;

    private WeakReference<Fragment> mFragment;

    public NoLeakHandler(Activity activity){
        mActivity = new WeakReference<>(activity);
    }

    public NoLeakHandler(Fragment fragment){
        mFragment = new WeakReference<>(fragment);
    }

    public NoLeakHandler(){
    }
}