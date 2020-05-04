package com.znh.hot.fix;

import java.lang.reflect.Array;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by znh on 2020/5/4
 * <p>
 * 处理dex相关的工具类
 */
public class DexUtils {

    /**
     * 获取BaseDexClassLoader中的pathList字段的值
     *
     * @param baseDexClassLoader pathList字段所属的BaseDexClassLoader对象
     * @return pathList字段的值
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getPathList(BaseDexClassLoader baseDexClassLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return ReflectUtils.getFieldValue(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    /**
     * 获取DexPathList中的dexElements字段的值
     *
     * @param pathList dexElements字段所属的DexPathList对象
     * @return dexElements字段的值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getDexElements(Object pathList) throws NoSuchFieldException, IllegalAccessException {
        return ReflectUtils.getFieldValue(pathList, pathList.getClass(), "dexElements");
    }

    /**
     * 设置DexPathList中的dexElements字段的值
     *
     * @param pathList         dexElements字段所属的DexPathList对象
     * @param dexElementsValue dexElements字段的值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setDexElements(Object pathList, Object dexElementsValue) throws NoSuchFieldException, IllegalAccessException {
        ReflectUtils.setFieldValue(pathList, pathList.getClass(), "dexElements", dexElementsValue);
    }

    /**
     * 合并两个数组
     *
     * @param array1 第一个数组
     * @param array2 第二个数组
     * @return 合并后的新数组
     */
    public static Object combineArray(Object array1, Object array2) {
        //获取array1数组的长度
        int array1Length = Array.getLength(array1);

        //获取array2数组的长度
        int array2Length = Array.getLength(array2);

        //获取两者的长度和
        int newArrayLength = array1Length + array2Length;

        //创建新的数组
        Object newArray = Array.newInstance(array1.getClass().getComponentType(), newArrayLength);
        for (int i = 0; i < newArrayLength; i++) {
            if (i < array1Length) {
                Array.set(newArray, i, Array.get(array1, i));
            } else {
                Array.set(newArray, i, Array.get(array2, i - array1Length));
            }
        }
        return newArray;
    }
}
