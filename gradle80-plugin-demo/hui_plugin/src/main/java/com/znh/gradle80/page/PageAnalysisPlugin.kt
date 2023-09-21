package com.znh.gradle80.page

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by znh on 09/21/2023
 * <p>
 * Description: PageAnalysisPlugin
 */
class PageAnalysisPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("Hello PageAnalysisPlugin")
    }
}