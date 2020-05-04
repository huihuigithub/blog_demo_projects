package com.znh.hot.fix;

import java.lang.reflect.Field;

/**
 * Created by znh on 2020/5/4
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
}
