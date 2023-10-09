package com.znh.plugin.router

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by znh on 10/09/2023
 * <p>
 * Description: HuiRouterCVisitor
 */
class HuiRouterCVisitor(
    cv: ClassVisitor,
    private val annotationPathMap: HashMap<String?, String?>?,
) : ClassVisitor(Opcodes.ASM9, cv) {

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor? {
        val methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        if (name == "<clinit>" && desc == "()V") {
            println("HuiRouterApi的静态代码块执行了")
            return HuiRouterMVisitor(
                Opcodes.ASM9,
                methodVisitor,
                access,
                name,
                desc,
                annotationPathMap
            )
        }
        return methodVisitor
    }
}