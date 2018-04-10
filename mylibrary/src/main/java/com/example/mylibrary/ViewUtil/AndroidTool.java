package com.example.mylibrary.ViewUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import com.example.mylibrary.zxing.android.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zy on 2017/7/8.
 * android 相关工具.
 */

public class AndroidTool {

    /**
     * 获取IMEI号.
     * @param context
     * @return IMEI
     */
    public static String getIMEI(Context context){
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = TelephonyMgr.getDeviceId();
        return imei;
    }

    /**
     * 获取电话号码.
     */
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String NativePhoneNumber=telephonyManager.getLine1Number();
        return NativePhoneNumber;
    }

    /**
     * 获取手机服务商编号..
     */
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = "";
        try{
            IMSI = telephonyManager.getSubscriberId();
        }catch(Exception e){
            e.printStackTrace();
        }
        return IMSI;
    }

    /**
     * 获取手机所有信息.
     * 在一定程度上可以作为手机唯一号使用.
     * @param context
     * @return
     */
    public static Map<String,String> getPhoneInfo(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        Map<String,String> sb = new HashMap<>();
        sb.put("DeviceId" ,tm.getDeviceId());
        sb.put("DeviceSoftwareVersion" ,tm.getDeviceSoftwareVersion());
        sb.put("Line1Number",tm.getLine1Number());
        sb.put("NetworkCountryIso",tm.getNetworkCountryIso());
        sb.put("NetworkOperator",tm.getNetworkOperator());
        sb.put("NetworkOperatorName",tm.getNetworkOperatorName());
        sb.put("NetworkType",tm.getNetworkType()+"");
        sb.put("PhoneType",tm.getPhoneType()+"");
        sb.put("SimCountryIso",tm.getSimCountryIso());
        sb.put("SimOperator",tm.getSimOperator());
        sb.put("SimOperatorName",tm.getSimOperatorName());
        sb.put("SimSerialNumber",tm.getSimSerialNumber());
        sb.put("SimState",tm.getSimState()+"");
        sb.put("SubscriberId(IMSI)",tm.getSubscriberId());
        sb.put("VoiceMailNumber",tm.getVoiceMailNumber());
        return  sb;
    }

    /**
     * 获取经纬度.
     * @param context
     * @param modle
     * 1:GPS获取.
     * 2:wifi获取.
     * 3:两种方式获取.
     * @return
     */
    public static Double[] getAddress(Context context,int modle){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (modle==1){
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Double[] address = new Double[2];
            address[0]=location.getLatitude();
            address[1]=location.getLongitude();
            return address;
        }else if (modle==2){
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Double[] address = new Double[2];
            address[0]=location.getLatitude();
            address[1]=location.getLongitude();
            return address;
        }else if (modle==3){
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location==null){
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            Double[] address = new Double[2];
            address[0]=location.getLatitude();
            address[1]=location.getLongitude();
            return address;
        }
        return null;
    }

    /**
     * 打开系统图片选择.
     */
    public static void openPic(Activity activity,int REQUEST_CODE,String title){
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        innerIntent.setType("image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, title);
        activity .startActivityForResult(wrapperIntent, REQUEST_CODE);
    }

    public static List<String> getPic(Activity activity,Intent data){
        List<String> path = new ArrayList<>();
        String photo_path = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        // 获取选中图片的路径
        Cursor cursor = activity.getContentResolver().query(data.getData(),
                proj, null, null, null);

        if (cursor.moveToFirst()) {

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            photo_path = cursor.getString(column_index);
            if (photo_path == null) {
                photo_path = Utils.getPath(activity,data.getData());
            }
            path.add(photo_path);
        }
        cursor.close();
        return path;
    }

    /**
     * 判断网络是否连接.
     * @param context
     * @return
     */
    public static boolean checkNetworkState(Context context) {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 检测网络是否可用.
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断service是否运行.
     * @param mContext
     * @param serviceName
     * @param max
     * 获取多少个service；
     * @return
     */
    public static boolean isServiceWork(Context mContext, String serviceName,int max) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(max);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 判断service是否运行.
     * @param mContext
     * @param serviceName
     * @return
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        return isServiceWork(mContext,serviceName,100);
    }

    /**
     * 切换手机为竖屏.
     * @param activity
     */
    public static void fullChangeScreenV(Activity activity) {
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 切换手机为横屏.
     * @param activity
     */
    public static void fullChangeScreenC(Activity activity) {
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 自由切换手机RequestedOrientation.
     * 具体数值查看ActivityInfo.
     * @param activity
     */
    public static void fullChangeScreenX(Activity activity,int sta) {
        activity.setRequestedOrientation(sta);
    }

    /**
     * 设置全屏.
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
    }

    /**
     * 清除全屏状态.
     * @param activity
     */
    public static void clearFullScreen(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 安装apk确认.
     * @param context
     * 是否需要用户确认.
     * @param path
     */
    public static void install(Context context,String path){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()+".fileprovider", new File(path));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


}
