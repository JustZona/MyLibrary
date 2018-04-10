package com.example.mylibrary.AlertDialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by zy on 2017/3/29.
 */

public class Custom_Dialog extends Dialog {

    private AlertDialogWait.dismissListener listener;
    public Custom_Dialog(Context context) {
        super(context);
    }

    public Custom_Dialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Custom_Dialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void dismiss() {
        if (listener!=null){
            listener.dismiss();
        }
        super.dismiss();
    }

    public void setDismissListener(AlertDialogWait.dismissListener listener) {
        this.listener = listener;
    }
}
