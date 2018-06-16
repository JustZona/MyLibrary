package com.example.mylibrary.Http;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zy on 2017/8/24.
 * okHttp
 */
public class HttpOK {

    /**get异步请求无参.*/
    public static void getHttp(String url, HttpOkCallback callback){
        getHttp(url,callback,null,null);
    }

    /**get异步请求含参.*/
    public static void getHttp(String url, HttpOkCallback callback, SimpleArrayMap<String,String> map,Context context){
        int cacheSize = 10 * 1024 * 1024;
        File sdcache = context.getExternalCacheDir();
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        OkHttpClient okHttpClient=clientBuilder.build();
        Request.Builder builder = new Request.Builder()
                .get()
                .url(url+MapHandle(map));
        callback.setHeader(builder);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**get同步请求无参.*/
    public static Response getHttpSyn(String url, HttpOkCallback callback,Context context){
        return getHttpSyn(url,callback,null,context);
    }

    /**get同步请求含参.*/
    public static Response getHttpSyn(String url, HttpOkCallback callback,SimpleArrayMap<String,String> map,Context context){
        Response response = null;
        try {
            int cacheSize = 10 * 1024 * 1024;
            File sdcache = context.getExternalCacheDir();
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
            OkHttpClient okHttpClient=clientBuilder.build();
            Request.Builder builder = new Request.Builder()
                    .get()
                    .url(url+MapHandle(map));
            if (callback!=null){
                callback.setHeader(builder);
            }
            Request request = builder.build();
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    //post需要携带参数，会经常改动，所以没必要缓存
    /**post异步请求无参.*/
    public static void postHttp(String url, HttpOkCallback callback){
        postHttpMap(url,callback,null);
    }

    /**post异步请求含参.*/
    public static void postHttpMap(String url, HttpOkCallback callback,SimpleArrayMap<String,String> map){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        OkHttpClient okHttpClient=clientBuilder.build();
        FormBody.Builder builder = new FormBody.Builder();
        if (map!=null){
            for (int i = 0; i < map.size(); i++) {
                String key = map.keyAt(i);
                String value = map.valueAt(i);
                builder.add(key,value);
            }
        }
        Request.Builder build = new Request.Builder()
                .post(builder.build())
                .url(url);
        callback.setHeader(build);
        Request request = build.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**post异步请求含参(参数为字符串).*/
    public static void postHttp(String url, HttpOkCallback callback,String parameter){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        OkHttpClient okHttpClient=clientBuilder.build();
        Request.Builder build = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("MEDIA_TYPE_MARKDOWN"), parameter))
                .url(url);
        if(callback!=null){
            callback.setHeader(build);
        }
        Request request = build.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**post同步请求无参.*/
    public static Response postHttpSyn(String url,HttpOkCallback callback){
        return postHttpSynMap(url,callback,null);
    }

    /**post同步请求含参(参数直接为字符串).*/
    public static Response postHttpSyn(String url,HttpOkCallback callback,String parameter){
        Response response = null;
        try {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            OkHttpClient okHttpClient=clientBuilder.build();
            Request.Builder build = new Request.Builder()
                    .post(RequestBody.create(MediaType.parse("MEDIA_TYPE_MARKDOWN"), parameter))
                    .url(url);
            if (callback!=null){
                callback.setHeader(build);
            }
            Request request = build.build();
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**post同步请求含参.*/
    public static Response postHttpSynMap(String url,HttpOkCallback callback,SimpleArrayMap<String,String> map){
        Response response = null;
        try {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            OkHttpClient okHttpClient=clientBuilder.build();
            FormBody.Builder builder = new FormBody.Builder();
            if (map!=null){
                for (int i = 0; i < map.size(); i++) {
                    String key = map.keyAt(i);
                    String value = map.valueAt(i);
                    builder.add(key,value);
                }
            }
            Request.Builder build= new Request.Builder()
                    .post(builder.build())
                    .url(url);
            if (callback!=null){
                callback.setHeader(build);
            }
            Request request = build.build();
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public interface HttpOkCallback extends Callback{
        public void setHeader(Request.Builder builder);
    }

    /**
     * map转get参数.
     * @param map
     * @param bm
     * 编码.
     * @return
     */
    public static String MapHandle(SimpleArrayMap<String,String> map, String bm){
        StringBuffer sb = new StringBuffer();
        try {
            if (map != null) {
                for (int i = 0; i < map.size(); i++) {
                    String key = map.keyAt(i);
                    String value = map.valueAt(i);
                    if (i == 0) {
                        sb.append(key + "=" + URLEncoder.encode(value, bm));
                    } else {
                        sb.append("&" + key + "=" + URLEncoder.encode(value, bm));
                    }
                }
            }else {
                sb.append("");
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * map转get参数(utf-8编码).
     * @param map
     * @return
     */
    public static String MapHandle(SimpleArrayMap<String,String> map){
        return MapHandle(map,"utf-8");
    }

}
