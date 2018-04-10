package com.example.mylibrary.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mylibrary.R;


/**
 * Created by zy on 2017/2/7.
 */

public class AlertDialogWait{

    private static Context context;
    private static Custom_Dialog dialog;
    private static Window window;
    private static WindowManager windowManager;
    private static WindowManager.LayoutParams lp;
    private static int progressBarLayout = R.drawable.progressbar;
    private static DialogInterface.OnKeyListener onKeyListener;
    private static AlertDialogWait.dismissListener listener;
    private AlertDialogWait(Context context) {
        AlertDialogWait.context = context;
        creatBuilder(context);
    }

    /**
     * 初始化dialog及其相关对象.
     * @param context
     */
    private static void creatBuilder(Context context){
        dialog = new Custom_Dialog(context,R.style.dialog);
        window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        lp = window.getAttributes();
    }

    /**
     * 自定义Dialog的OnKey事件.
     * 在调用Dialog之前调用.
     * @param onKeyListener
     */
    public static void CustomDialogOnKey(DialogInterface.OnKeyListener onKeyListener){
        AlertDialogWait.onKeyListener = onKeyListener;
    }

    /**
     * 等待框.
     * @param context
     * @param layout
     * progressbar样式.
     * @return
     */
    public static Window showWait(Context context,int layout){
        Window window = CustomDialog(context,layout,0.7,0.2,false);
        return window;
    }

    /**
     * 等待框.
     * @param context
     */
    public static Progress showWait(Context context,String title){
        Window window = showWait(context,R.layout.dialog_wait);
        ProgressBar progress = (ProgressBar) window.findViewById(R.id.loading_progress);
        Resources resources = context.getResources();
        progress.setIndeterminateDrawable(resources.getDrawable(progressBarLayout));
        TextView tvLoad = (TextView) window.findViewById(R.id.tvLoad);
        tvLoad.setText(title);
        return new Progress(tvLoad);
    }

    /**
     * 提示框.
     * @param context
     * @param text
     * 提示内容.
     * @param title
     * 提示标题.
     */
    public static void showPrompt(Context context, String text, String title){
        showPrompt(context, text, title, new onClickPrompt() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     *提示框.
     * @param context
     * @param text
     * 提示内容.
     * @param title
     * 提示标题.
     * @param prompt
     * 自定义点击事件.
     */
    public static void showPrompt(Context context, String text, String title, final onClickPrompt prompt){
        Window window = CustomDialog(context,R.layout.dialog_prompt,0.7,0.3,true);
        Button dismiss = (Button) window.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.onClick(v);
            }
        });
        TextView prompt_tv = (TextView)window.findViewById(R.id.prompt);
        TextView title_tv = (TextView) window.findViewById(R.id.title);
        prompt_tv.setText(text);
        title_tv.setText(title+"");
    }

    /**
     * 选择框.
     * @param context
     * @param text
     * 内容.
     * @param title
     * 标题.
     * @param onClick
     * 点击事件.
     */
    public static void showChoice(Context context, String text,String title, final onClick onClick){
        Window window = CustomDialog(context,R.layout.dialog_choice,0.7,0.3,true);
        Button no = (Button) window.findViewById(R.id.no);
        Button yes = (Button) window.findViewById(R.id.yes);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onDismiss(v);
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(v);
            }
        });
        TextView prompt_tv = (TextView)window.findViewById(R.id.prompt);
        TextView title_tv = (TextView) window.findViewById(R.id.title);
        prompt_tv.setText(text);
        title_tv.setText(title+"");
    }

    /**
     * 对话框.
     * @param context
     * @param title
     * 标题.
     * @param onClickue
     * 点击事件.
     */
    public static void showDialogue(Context context,String title, final onClickUe onClickue){
        Window window = CustomDialog(context,R.layout.dialog_ue,0.7,0.3,true);
        Button no = (Button) window.findViewById(R.id.no);
        Button yes = (Button) window.findViewById(R.id.yes);
        TextView t = (TextView) window.findViewById(R.id.title);
        t.setText(title);
        final EditText prompt = (EditText) window.findViewById(R.id.prompt);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickue.onDismiss(prompt,prompt.getText().toString());
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickue.onClick(prompt,prompt.getText().toString());
            }
        });
    }

    /**
     * 自定义Dialog.
     * @param context
     * @param layout
     * 自定义layout.
     * @param width
     * 宽度.
     * @param height
     * 高度.
     * @param isOutSide
     * 是否允许点击阴影部分.
     * @return
     * 返回Window对象，用于控件findViewById.
     */
    public static Window CustomDialog(Context context, int layout, double width, double height,boolean isOutSide){
        View view = LinearLayout.inflate(context, layout,null);
        return CustomDialog(context,view,width,height,isOutSide);
    }

    /**
     * 自定义Dialog.
     * @param context
     * @param layout
     * 自定义layout.
     * @param width
     * 宽度.
     * @param height
     * 高度.
     * @param isOutSide
     * 是否允许点击阴影部分.
     * @return
     * 返回Window对象，用于控件findViewById.
     */
    public static Window CustomDialog(Context context, View layout, double width, double height,boolean isOutSide){
        AlertDialogWait.context = context;
        creatBuilder(context);
        show();
        dialog.setCanceledOnTouchOutside(isOutSide);
        if (onKeyListener!=null){
            dialog.setOnKeyListener(onKeyListener);
        }else {
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return false;
                }
            });
        }
        lp.width = (int) (windowManager.getDefaultDisplay().getWidth() * width);
        lp.height = (int) (windowManager.getDefaultDisplay().getHeight() * height);
        window.setAttributes(lp);
        window.setContentView(layout);
        return window;
    }


    /**
     * 显示.
     */
    public static void show(){
        if (((Activity)context).isFinishing()) {
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog.show();
    }

    /**
     * 设置Dialog消失监听.
     */
    public static void setDismissListener(AlertDialogWait.dismissListener listener){
        AlertDialogWait.listener = listener;
        dialog.setDismissListener(listener);
    }

    /**
     * 取消.
     */
    public static void dismiss(){
        if (dialog==null&&!dialog.isShowing()){
            return;
        }
        if (((Activity)context).isFinishing()){
            return;
        }
        if (onKeyListener!=null){
            onKeyListener=null;
        }
        dialog.dismiss();
    }

    /**
     * 选择框事件.
     */
    public interface onClick {
        void onClick(View v);
        void onDismiss(View v);
    }

    /**
     * 操作框事件.
     */
    public interface onClickUe {
        void onClick(EditText v, String text);
        void onDismiss(EditText v, String text);
    }

    /**
     * 等待框进度设置.
     */
    public static class Progress{
        private TextView progress;
        private Progress(TextView progress){
            this.progress = progress;
        }
        public void setProgress(String p){
            progress.setText(p);
        }
    }

    /**
     * 提示框事件.
     */
    public interface onClickPrompt {
        void onClick(View v);
    }

    /**
     * Dialog取消监听.
     */
    public interface dismissListener{
        public void dismiss();
    }
}
