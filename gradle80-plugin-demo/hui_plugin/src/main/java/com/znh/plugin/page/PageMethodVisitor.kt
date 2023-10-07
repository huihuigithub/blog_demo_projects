package com.znh.plugin.page

import groovyjarjarasm.asm.Opcodes
import org.objectweb.asm.MethodVisitor

/**
 * Created by znh on 09/21/2023
 * <p>
 * ASM操作Method
 */
class PageMethodVisitor internal constructor(
    methodVisitor: MethodVisitor?,
    private val className: String?,  //当前的类名
    private val methodName: String? //当前的方法名
) : MethodVisitor(Opcodes.ASM9, methodVisitor) {

    override fun visitCode() {
        super.visitCode()
        //插入页面打开关闭的打点上报代码，这里用log打印代替了
        mv.visitLdcInsn("Page_TAG") //可以用来过滤log日志的tag
        mv.visitLdcInsn("$className--->$methodName") //插入要打印的内容
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/util/Log",
            "i",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        mv.visitInsn(Opcodes.POP)
    }
}