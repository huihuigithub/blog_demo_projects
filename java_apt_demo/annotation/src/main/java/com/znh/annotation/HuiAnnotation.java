package com.znh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by znh on 2020/7/29
 * <p>
 * 自定义注解
 */
@Target(ElementType.TYPE)//作用在类上
@Retention(RetentionPolicy.CLASS)//编译期注解
public @interface HuiAnnotation {
}
