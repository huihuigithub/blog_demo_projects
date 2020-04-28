package com.znh.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 测试Plugin
 */
class CustomPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        println("CustomPlugin执行了...")
    }
}