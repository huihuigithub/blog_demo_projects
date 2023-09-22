package com.znh.gradle80.page

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationParameters
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor

/**
 * Created by znh on 09/21/2023
 * <p>
 * Description: PageAnalysisPlugin
 */
class PageAnalysisPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("Hello PageAnalysisPlugin")
        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            variant.instrumentation.transformClassesWith(
                PageClassVisitorFactory::class.java,
                InstrumentationScope.ALL
            ) {}
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }

    abstract class PageClassVisitorFactory :
        AsmClassVisitorFactory<InstrumentationParameters.None> {

        override fun createClassVisitor(
            classContext: ClassContext,
            nextClassVisitor: ClassVisitor
        ): ClassVisitor {
            return PageClassVisitor(nextClassVisitor)
        }

        override fun isInstrumentable(classData: ClassData): Boolean {
            return classData.superClasses.contains("android.support.v7.app.AppCompatActivity")
                    || classData.superClasses.contains("androidx.appcompat.app.AppCompatActivity")
                    || classData.superClasses.contains("androidx.activity.ComponentActivity")
        }
    }
}