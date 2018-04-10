package com.example.mylibrary.Sql;

import com.example.mylibrary.Json.MyExclus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zy on 2017/2/9.
 * 关于实体操作类注解查看"http://www.cnblogs.com/vanezkw/archive/2012/08/02/2619798.html"
 */
public class DataValue  {

    @MyExclus
    private List<Method> Cname;
    @MyExclus
    private List<Method> Bname;
    @MyExclus
    private Class Cclazz;
    @MyExclus
    private Class Bclazz;
    @MyExclus
    private DataValue dataValue;

    public void copy(DataValue dataValue){
        try {
            this.dataValue = dataValue;
            Class clazz = Class.forName(dataValue.getClass().getName());
            Cclazz = clazz;
            Method[] methods = clazz.getMethods();
            Cname = new ArrayList<>();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.substring(0,3).equals("get")){
                    Cname.add(method);
                }
            }
            clazz = Class.forName(this.getClass().getName());
            Bclazz = clazz;
            methods = clazz.getMethods();
            Bname = new ArrayList<>();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.substring(0,3).equals("set")){
                    Bname.add(method);
                }

            }
            startCopy();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startCopy(){
        for (int i = 0;i<Bname.size();i++){
            String bname = Bname.get(i).getName();
            for (int j = 0;j<Cname.size();j++){
                String cname = Cname.get(j).getName();
                if (bname.substring(3,bname.length()).equals(cname.substring(3,cname.length()))){
                    try {
                        Bname.get(i).invoke(this,Cname.get(j).invoke(dataValue));
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

