package com.example.mylibrary.ToastUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zy on 2017/2/7.
 * ToastUtil.
 */
public class ToastUtil {

    /**
     * Toast提示.
     * @param context
     * @param text
     * 文本.
     */
    public static void showToast(Context context,String text){
        CustomToast.makeText(context,text+"",Toast.LENGTH_SHORT);
    }

    /**
     * Toast提示.
     * @param context
     * @param id
     * 文本id.
     */
    public static void showToast(Context context,int id){
        CustomToast.makeText(context,context.getResources().getText(id)+"",Toast.LENGTH_SHORT);
    }
}
