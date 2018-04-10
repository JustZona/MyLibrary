package com.example.mylibrary.ViewUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by zy on 2017/4/8.
 * 调用系统uri相关工具类.
 */
public class IntentUri {
    private static final int CAMERA_REQUEST_CODE = 1;
    /**
     * 掉用拨号.
     * @param phone_str
     * 拨打号码.
     * @param context
     * 上下文环境.
     */
    public static void phoneUri(String phone_str,Context context){
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone_str));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开启系统拨号器
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
