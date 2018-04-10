package com.example.mylibrary.log;

import android.text.TextUtils;
import android.util.Log;

import com.example.mylibrary.FileAndMemory.FileUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;

import timber.log.Timber;

/**
 * Created by zy on 2018/1/13.
 */
public class MyLog{

    /**
     * 初始化Logger,返回设置类.
     * @return
     */
    public static void initLogger(String tag){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .methodCount(4)
                .showThreadInfo(true)
                .tag(tag)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    /**
     * 初始化Timber,自定义tree.
     */
    public static void initTimber(Timber.Tree tree){
        initTimber(tree);
    }

    /**
     * 初始化Timber.
     */
    public static void initTimber(){
        Timber.plant(new Timber.DebugTree());
    }

    /**
     * 保存Log日志到文件.
     * @param cacheDiaPath
     * @param message
     */
    public static void saveFile(String cacheDiaPath, String message){
        if (TextUtils.isEmpty(cacheDiaPath)) {
            return;
        }
        try {
            FileUtil.writeTxtFile(message,new File(cacheDiaPath),true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("saveLog","保存日志失败");
        }
    }

}
