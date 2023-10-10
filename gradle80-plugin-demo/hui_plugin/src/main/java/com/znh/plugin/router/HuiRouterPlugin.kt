package com.znh.plugin.router

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by znh on 10/09/2023
 * <p>
 * Description: HuiRouterPlugin
 */
class HuiRouterPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            val taskProvider = target.tasks.register(//注册HuiRouterTask任务
                "${variant.name}HuiRouterTask", HuiRouterTask::class.java
            )
            variant.artifacts.forScope(ScopedArtifacts.Scope.ALL) //扫描所有class
                .use(taskProvider)
                .toTransform(
                    type = ScopedArtifact.CLASSES,
                    inputJars = HuiRouterTask::allJars,
                    inputDirectories = HuiRouterTask::allDirectories,
                    into = HuiRouterTask::output
                )
        }
    }
}