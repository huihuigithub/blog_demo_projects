package com.znh.plugin.router

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Created by znh on 10/09/2023
 * <p>
 * Description: HuiRouterMVisitor
 */
class HuiRouterMVisitor(
    api: Int,
    methodVisitor: MethodVisitor?,
    access: Int,
    name: String?,
    descriptor: String?,
    private val annotationPathMap: HashMap<String?, String?>?,
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        annotationPathMap?.forEach { entry ->
            mv.visitFieldInsn(
                GETSTATIC,
                "com/znh/aop/api/HuiRouterApi",
                "INSTANCE",
                "Lcom/znh/aop/api/HuiRouterApi;"
            )
            mv.visitLdcInsn(entry.key)
            mv.visitLdcInsn(entry.value)
            mv.visitMethodInsn(
                INVOKEVIRTUAL,
                "com/znh/aop/api/HuiRouterApi",
                "addRouterPath",
                "(Ljava/lang/String;Ljava/lang/String;)V",
                false
            )
            println("HuiRouterApi -> addRouterPath插入：$entry")
        }
    }
}