package com.znh.gradle.custom.plugin;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by znh on 2020-04-21
 * <p>
 * ASM操作class的类
 */
public class PageClassVisitor extends ClassVisitor {

    //当前类的类名称
    //本例：com/znh/gradle/plugin/demo/MainActivity
    private String className;

    //className类的父类名称
    //本例：androidx/appcompat/app/AppCompatActivity
    private String superName;

    public PageClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        super.visit(version, access, className, signature, superName, interfaces);
        this.className = className;
        this.superName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor, String signature, String[] exceptions) {

        MethodVisitor methodVisitor = cv.visitMethod(access, methodName, descriptor, signature, exceptions);

        //判断是否自己要匹配的方法(这里匹配onCreate和onDestroy方法)
        if ("android/support/v7/app/AppCompatActivity".equals(superName)//support包
                || "androidx/appcompat/app/AppCompatActivity".equals(superName)) {//androidx包
            if (methodName.startsWith("onCreate")) {
                return new PageMethodVisitor(methodVisitor, className, methodName);
            }
            if (methodName.startsWith("onDestroy")) {
                return new PageMethodVisitor(methodVisitor, className, methodName);
            }
        }
        return methodVisitor;
    }
}
