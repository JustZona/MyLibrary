package com.example.mylibrary.ViewUtil;

import java.util.UUID;

/**
 * Created by zy on 2017/2/14.
 * 字符串相关工具类.
 */
public class StringUtil {

    /**
     * 判断字符串是否为空;
     * @param c
     * @return
     */
    public static boolean isEmpty(String c){
        if (c==null||c.length()<=0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 字符串截取.
     * @param c
     * @param start
     * @param end
     * @return
     */
    public static String subString(String c,int start ,int end){
        return c.substring(start,end);
    }

    /**
     * 字符串截取.
     * @param c
     * @param end
     * @return
     */
    public static String subString(String c ,int end){
        return subString(c,0,end);
    }

    /**
     * 字符串根据特定字符截取成为字符数组.
     * @param c
     * @param s
     * @return
     */
    public static String[] subStringS(String c,String s){
        return c.split(s);
    }


    public static String[] chars = new String[]
            {
                    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z"
            };

    /**
     * 生成随机字符串.
     * @return
     */
    public static String getShortUuid(){
        return getShortUuid(8);
    }

    /**
     * 生成随机字符串.
     * @param length
     * @return
     */
    public static String getShortUuid(int length){
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < length; i++){
            String str      = uuid.substring(i * 4, i * 4 + 4);
            int strInteger  = Integer.parseInt(str, 16);
            stringBuffer.append(chars[strInteger % 0x3E]);
        }

        return stringBuffer.toString();
    }

}
