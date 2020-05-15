package com.znh.hook.host;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by znh on 2020/5/5
 * <p>
 * 处理反射相关的工具类
 */
public class ReflectUtils {

    /**
     * 通过反射获取到某个字段对应的值(对象值)
     *
     * @param obj       该字段所属的对象
     * @param clazz     该字段所属的类
     * @param fieldName 字段名称
     * @return 该字段的值
     */
    public static Object getFieldValue(Object obj, Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 通过反射为某个字段赋值
     *
     * @param obj        该字段所属的对象
     * @param clazz      该字段所属的类
     * @param fieldName  字段名称
     * @param fieldValue 要为该字段赋的值
     */
    public static void setFieldValue(Object obj, Class<?> clazz, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, fieldValue);
    }

    /**
     * 通过反射获取方法的返回值
     *
     * @param obj        该方法所属的对象
     * @param clazz      该方法所属的类
     * @param methodName 方法名称
     * @param args       方法的参数
     * @return 方法的返回值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getMethodValue(Object obj, Class<?> clazz, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getMethod(methodName);
        method.setAccessible(true);
        return method.invoke(obj, args);
    }
}
