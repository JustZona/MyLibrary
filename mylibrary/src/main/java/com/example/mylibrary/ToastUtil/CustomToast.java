package com.example.mylibrary.ToastUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.R;


/**
 * Created by zy on 2017/3/29.
 * Toast工具类.
 */
public class CustomToast {

    /**
     * 显示通用Toast
     * @param context
     * @param tvString
     */
    public static void makeText(Context context, String tvString,int duration){
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast,null);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(tvString);
        makeCustomToast(context,duration,layout);
    }

    /**
     * 自定义Toast
     * @param context
     */
    public static void makeCustomToast(Context context,int duration,View layout){
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }
}
