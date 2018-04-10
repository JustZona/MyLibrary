package com.example.mylibrary.Http;

import android.graphics.Bitmap;
import android.support.v4.util.SimpleArrayMap;

import com.example.mylibrary.ThreadRun.ThreadPoolFramework;
import com.example.mylibrary.ViewUtil.MD5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by zy on 2017/8/11.
 * http文件上传.
 */
public class HttpUpload {

    /**
     * 图片Bitmap上传
     * @param bitmap
     * @param name
     * 文件名称.
     * @param key
     * key值
     * @param map
     * 其他参数
     * @param RequestURL
     * url
     * @param getResult
     * 回调.
     */
    public static void uploadFile(final Bitmap bitmap, final String name, final String key, final SimpleArrayMap<String,String> map, final String RequestURL, final GetHttpResult getResult) {
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                String BOUNDARY = "---------------------------25694102917164"; // 边界标识 随机生成
                String PREFIX = "--";
                String LINE_END = "\r\n";
                try {
                    URL url = new URL(RequestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10 * 1000);
                    conn.setConnectTimeout(10 * 1000);
                    conn.setDoInput(true); // 允许输入流
                    conn.setDoOutput(true); // 允许输出流
                    conn.setUseCaches(false); // 不允许使用缓存
                    conn.setRequestMethod("POST"); // 请求方式
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("Accept", "*/*");
                    conn.setRequestProperty("Charset", "utf-8"); // 设置编码
                    conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+BOUNDARY);
                    if (bitmap != null) {
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < map.size(); i++) {
                            sb.append(PREFIX+BOUNDARY+LINE_END);
                            String key = map.keyAt(i);
                            String value = map.valueAt(i);
                            sb.append("Content-Disposition: form-data; name=\""+key+"\""+LINE_END+"\n");
                            sb.append(value+LINE_END);//内容
                        }
                        sb.append(PREFIX+BOUNDARY+LINE_END);
                        sb.append("Content-Disposition: form-data;name=\""+key+"\";");
                        sb.append("filename=\""+name+"\""+LINE_END);
                        sb.append("Content-Type:application/octet-stream"+LINE_END);

                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        System.out.println(baos.toByteArray().length);
                        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = isBm.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        isBm.close();
                        dos.write(LINE_END.getBytes());
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                        dos.write(end_data);
                        dos.flush();
                        int res = conn.getResponseCode();
                        if (res==200){
                            InputStream input = conn.getInputStream();
                            StringBuffer sb1 = new StringBuffer();
                            int ss;
                            while ((ss = input.read()) != -1) {
                                sb1.append((char) ss);
                            }
                            getResult.succes(sb1.toString());
                        }

                    }else {
                        getResult.fail("文件为空");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    getResult.fail(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    getResult.fail(e.getMessage());
                }
            }

            private byte[] MapHandle(SimpleArrayMap<String,String> map, String Md5){
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
                    String key = "";
                    byte[] requestStringBytes;
                    if (Md5.equals("")){
                        requestStringBytes = sb.toString().getBytes("UTF-8");
                    }else {
                        MD5 md5 = new MD5(Md5);
                        requestStringBytes = md5.encrypt(sb.toString()).getBytes("UTF-8");
                    }
                    return requestStringBytes;
                } catch (Exception e) {
                    return null;
                }

            }
        });
    }


    /**
     * 根据文件上传
     * @param file
     * 文件.
     * @param key
     * key
     * @param map
     * 其他参数.
     * @param RequestURL
     * url
     * @param getResult
     * 回调.
     */
    public static void uploadFile(final File file, final String key, final SimpleArrayMap<String,String> map, final String RequestURL, final GetHttpResult getResult) {
        ThreadPoolFramework.execute(new Runnable() {
            @Override
            public void run() {
                String BOUNDARY = "---------------------------25694102917164"; // 边界标识 随机生成
                String PREFIX = "--";
                String LINE_END = "\r\n";
                try {
                    URL url = new URL(RequestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10 * 1000);
                    conn.setConnectTimeout(10 * 1000);
                    conn.setDoInput(true); // 允许输入流
                    conn.setDoOutput(true); // 允许输出流
                    conn.setUseCaches(false); // 不允许使用缓存
                    conn.setRequestMethod("POST"); // 请求方式
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("Accept", "*/*");
                    conn.setRequestProperty("Charset", "utf-8"); // 设置编码
                    conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+BOUNDARY);
                    if (file != null) {
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < map.size(); i++) {
                            sb.append(PREFIX+BOUNDARY+LINE_END);
                            String key = map.keyAt(i);
                            String value = map.valueAt(i);
                            sb.append("Content-Disposition: form-data; name=\""+key+"\""+LINE_END+"\n");
                            sb.append(value+LINE_END);//内容
                        }
                        sb.append(PREFIX+BOUNDARY+LINE_END);
                        sb.append("Content-Disposition: form-data;name=\""+key+"\";");
                        sb.append("filename=\""+file.getName()+"\""+LINE_END);
                        sb.append("Content-Type:application/octet-stream"+LINE_END);

                        sb.append(LINE_END);
                        dos.write(sb.toString().getBytes());
                        InputStream inputStream = new FileInputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            dos.write(bytes, 0, len);
                        }
                        inputStream.close();
                        dos.write(LINE_END.getBytes());
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                        dos.write(end_data);
                        dos.flush();
                        int res = conn.getResponseCode();
                        if (res==200){
                            InputStream input = conn.getInputStream();
                            StringBuffer sb1 = new StringBuffer();
                            int ss;
                            while ((ss = input.read()) != -1) {
                                sb1.append((char) ss);
                            }
                            getResult.succes(sb1.toString());
                        }

                    }else {
                        getResult.fail("文件为空");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    getResult.fail(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    getResult.fail(e.getMessage());
                }
            }
        });
    }
}
