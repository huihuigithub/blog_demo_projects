package com.znh.aop.annotation

/**
 * Created by znh on 10/09/2023
 * <p>
 * Description: HuiRouterPath
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class HuiRouterPath(
    val path: String = "" //路由表中的path
)
