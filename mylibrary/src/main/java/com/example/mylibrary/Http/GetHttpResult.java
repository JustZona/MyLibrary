package com.example.mylibrary.Http;

/**
 * Created by zy on 2017/2/6.
 */

public interface GetHttpResult {
    void succes(String result);
    void fail(String e);
    void onProgressUpdate(Integer progress);
}
