package com.example.mylibrary.Http;

import android.graphics.Bitmap;

/**
 * Created by zy on 2017/2/8.
 */

public interface GetHttpResultBitmap {
    void succes(Bitmap result);
    void fail(Exception e);
    void onProgressUpdate(Integer progress);
}
