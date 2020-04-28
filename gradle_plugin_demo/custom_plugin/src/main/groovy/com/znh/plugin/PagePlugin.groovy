package com.znh.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by znh on 2020-04-21
 * <p>
 * 用于页面统计的Plugin
 */
class PagePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        //注册自定义的PageTransform
        def androidConfig = project.extensions.getByType(AppExtension)
        PageTransform transform = new PageTransform()
        androidConfig.registerTransform(transform)
    }
}