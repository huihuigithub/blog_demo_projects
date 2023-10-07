package com.znh.plugin.page

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by znh on 09/21/2023
 * <p>
 * Description: ASM操作Class
 */
class PageClassVisitor(classVisitor: ClassVisitor?) : ClassVisitor(Opcodes.ASM9, classVisitor) {

    private var className: String? = null

    override fun visit(
        version: Int,
        access: Int,
        className: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, className, signature, superName, interfaces)
        this.className = className
    }

    override fun visitMethod(
        access: Int,
        methodName: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = cv.visitMethod(access, methodName, descriptor, signature, exceptions)
        if (access == 2 && methodName == "<init>" && descriptor == "()V") {
            return object : MethodVisitor(Opcodes.ASM9, methodVisitor) {
            }
        }
        if (methodName?.startsWith("onCreate") == true) {
            return PageMethodVisitor(methodVisitor, className, methodName)
        }
        if (methodName?.startsWith("onDestroy") == true) {
            return PageMethodVisitor(methodVisitor, className, methodName)
        }
        return methodVisitor
    }
}