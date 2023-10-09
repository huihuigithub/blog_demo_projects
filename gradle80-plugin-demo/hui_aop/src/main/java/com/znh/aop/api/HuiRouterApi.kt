package com.znh.aop.api

import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by znh on 10/09/2023
 * <p>
 * Description: HuiRouterApi
 */
object HuiRouterApi {

    //存储跳转路由表
    private val routerPathMap = mutableMapOf<String, String>()

    /**
     * 注册跳转路由
     */
    private fun addRouterPath(key: String?, path: String?) {
        if (key != null && path != null) {
            routerPathMap[key] = path
        }
    }

    /**
     * 页面跳转
     */
    fun routerPath(context: Context, routerTargetPath: String) {
        routerPathMap[routerTargetPath]?.takeIf {
            it.isNotEmpty()
        }?.run {
            context.startActivity(Intent(context, Class.forName(this)))
        }
    }

    /**
     * 打印routerPathMap
     */
    fun printRouterPathMap() {
        routerPathMap.forEach { entry ->
            Log.e("HuiRouterApi", "routerPathMap entry -> $entry")
        }
    }
}