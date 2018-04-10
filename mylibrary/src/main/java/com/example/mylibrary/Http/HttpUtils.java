package com.example.mylibrary.Http;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.util.SimpleArrayMap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.mylibrary.FileAndMemory.FileUtil;
import com.example.mylibrary.ThreadRun.ThreadPoolFramework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Created by zy on 2017/2/6.
 * android网络加载.
 * 使用Glide图片加载库.
 * 若使用HttpGetPic2Draw方法，
 * 可调用Picture中的BitmapHandle对Bitmap进行处理.
 */
public class HttpUtils {

    public static int TIMEOUT_IN_MILLIONS = 5000;
    public static int TIMEOUT_READ_MILLIONS = 5000;

    /**
     * 获取网络、本地图片
     * @param context
     * activity.
     * @param url
     * 图片地址(包括网络地址、本地地址).
     * @param imageView
     * ImageView.
     */
    public static void HttpGetPic(Activity context, String url, int imageID,ImageView imageView){
            Glide.with(context).load(url).placeholder(imageID).into(imageView);
    }

    /**
     * 圆形支持库专用
     * @param context
     * @param url
     * @param imageID
     * @param imageView
     */
    public static void HttpGetPicY(final Context context, String url, int imageID, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(imageID).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 清除缓存.
     * @param context
     */
    public static void clearGlide(final Context context){
        Glide.get(context).clearMemory();
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        });
    }


    /**
     * 获取图片并转换为Bitmap.
     * @param context
     * @param url
     * @param result
     * 获取drawable接口.
     */
    public static void HttpGetPic2Draw(final Context context, final String url, final getResult result){
        Resources resources = context.getResources();
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                result.result(resource);
            }
        });
        Glide.get(context).clearMemory();
    }

    /**
     * post访问.
     * @param urls
     * url.
     * @param map
     * 参数
     * @param result
     * 返回结果.
     */
    public static void HttpPost(final String urls, final SimpleArrayMap<String,String> map, final GetHttpResult result){
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] requestStringBytes = MapHandle(map);
                    URL url = new URL(urls);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Accept", "*/*");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    conn.setRequestProperty("Accept-Charset", "utf-8");
                    conn.setUseCaches(false);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("Content-length", "" + requestStringBytes);//设置数据长度
                    conn.setReadTimeout(TIMEOUT_READ_MILLIONS);
                    conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
                    conn.connect();//发起连接
                    OutputStream outputStream = conn.getOutputStream();
                    if (outputStream!=null){
                        outputStream.write(requestStringBytes);
                        outputStream.flush();
                    }
                    float size = conn.getContentLength();
                    InputStreamReader inputStream = new InputStreamReader(conn.getInputStream(),"UTF-8");
                    String str = "";
                    StringBuffer buffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(inputStream);
                    while((str = bufferedReader.readLine())!=null){
                        buffer.append(str);
                        result.onProgressUpdate((int)(buffer.length()/size*100));
                    }
                    result.onProgressUpdate(100);
                    inputStream.close();
                    outputStream.close();
                    result.succes(buffer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    result.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 下载文件.
     * @param urls
     * @param result
     * @param name
     */
    public static void HttpFile(final String urls, final GetHttpResult result, final String name){
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(name);
                try {
                    URL url = new URL(urls);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn .setRequestProperty("Accept-Encoding", "identity");
                    conn.setConnectTimeout(1000);
                    conn.connect();
                    FileUtil.delete(name);
                    if (conn.getResponseCode() == 200) {
                        conn.getContentLength();
                        InputStream is = conn.getInputStream();
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = is.read(buffer)) != -1) {
                            result.onProgressUpdate((int) (((float)file.length()/(float) conn.getContentLength())*100));
                            fos.write(buffer, 0, len);
                        }
                        result.onProgressUpdate(100);
                        buffer = null;
                        is.close();
                        fos.close();
                        result.succes("success");
                    }else {
                        result.fail(conn.getResponseCode()+"");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.fail(e.getMessage());
                }

            }
        });
    }


    /**
     * get访问.
     * @param urls
     * url.
     * @param result
     * 返回结果.
     */
    public static void HttpGet(final String urls,final GetHttpResult result){
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urls);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("Accept-Charset", "utf-8");
                    conn.setReadTimeout(TIMEOUT_READ_MILLIONS);
                    conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
                    float size = conn.getContentLength();
                    if (conn.getResponseCode()==200){
                        InputStreamReader inputStream = new InputStreamReader(conn.getInputStream(),"UTF-8");
                        String str = "";
                        StringBuffer buffer = new StringBuffer();
                        BufferedReader bufferedReader = new BufferedReader(inputStream);
                        while((str = bufferedReader.readLine())!=null){
                            buffer.append(str);
                            result.onProgressUpdate((int)(buffer.length()/size*100));
                        }
                        result.onProgressUpdate(100);
                        inputStream.close();
                        result.succes(buffer.toString());
                    }else {
                        result.fail(conn.getRequestMethod());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.fail(e.getMessage());
                }
            }
        });
    }


    private static byte[] MapHandle(SimpleArrayMap<String,String> map){
        StringBuffer sb = new StringBuffer();
        try {
            if (map != null) {
                for (int i = 0; i < map.size(); i++) {
                    String key = map.keyAt(i);
                    String value = map.valueAt(i);
                    if (i == 0) {
                        sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
                    } else {
                        sb.append("&" + key + "=" + URLEncoder.encode(value, "utf-8"));
                    }
                }
            }
            byte[] requestStringBytes = sb.toString().getBytes("UTF-8");
            return requestStringBytes;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * https网址跳过签名.
     * 访问https(如12306)异常，需在调用http
     * 调用此方法.
     */
    public static void initSSL() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                    // TODO Auto-generated method stub

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    // TODO Auto-generated method stub
                    return null;
                }
            } }, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
                .getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        });
    }

    public interface getResult{
        public void result(Object o);
    }
}
