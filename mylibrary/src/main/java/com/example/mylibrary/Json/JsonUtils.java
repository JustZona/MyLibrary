package com.example.mylibrary.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2017/2/8.
 */

public class JsonUtils {

    private static Gson gson;

    /**
     * 实例化gson.
     */
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        builder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        builder.setExclusionStrategies(new CustomExclusionStrategy());
        gson = builder.create();
    }

    /**
     * Object转json.
     * @param object
     * @return
     */
    public static String toJson(Object object){
        return gson.toJson(object);
    }

    /**
     * String字符串转换为jsonObject对象.
     * @param json
     * @return
     */
    public static JSONObject toJsonObject(String json){
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JsonArray转List对象.
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toArrayObject(String json,Class<T> clazz){
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjects =gson.fromJson(json,type);
        ArrayList<T> list = new ArrayList<>();
        for (JsonObject jsonObject:jsonObjects){
            list.add(gson.fromJson(jsonObject,clazz));
        }
        return list;
    }

    /**
     * JsonObject转对象.
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }
}
