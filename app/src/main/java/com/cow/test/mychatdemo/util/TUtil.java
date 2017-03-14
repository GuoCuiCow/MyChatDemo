package com.cow.test.mychatdemo.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * desc: Presenter获取
 * author：rookie on 16/12/6 下午4:55
 */
public class TUtil {

    public static <T> T getT(Object o, int i) {
        try {
            Type type = o.getClass().getGenericSuperclass();
            if(type==null){
                return null;
            }
            if(type instanceof ParameterizedType){
                return ((Class<T>) ((ParameterizedType) (type)).getActualTypeArguments()[i])
                        .newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}