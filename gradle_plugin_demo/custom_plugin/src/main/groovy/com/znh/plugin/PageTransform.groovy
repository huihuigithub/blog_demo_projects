package com.znh.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.znh.gradle.custom.plugin.PageClassVisitor
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

/**
 * Created by znh on 2020-04-21
 * <p>
 * 处理页面统计的Transform
 */
class PageTransform extends Transform {

    @Override
    String getName() {
        return "PageTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        Collection<TransformInput> inputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider

        inputs.each {
            TransformInput transformInput ->

                //Gradle3.6.0之后需要单独复制jar包
                transformInput.jarInputs.each { JarInput jarInput ->
                    File jarFile = jarInput.file
                    def destJar = outputProvider.getContentLocation(jarInput.name,
                            jarInput.contentTypes,
                            jarInput.scopes, Format.JAR)
                    FileUtils.copyFile(jarFile, destJar)
                }

                //操作class文件
                transformInput.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        File dir = directoryInput.file
                        if (dir) {
                            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->

                                //读取和解析class文件
                                ClassReader classReader = new ClassReader(file.bytes)
                                //对class文件的写入
                                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                                //访问class文件的内容
                                ClassVisitor classVisitor = new PageClassVisitor(classWriter)
                                //调用classVisitor的各个方法
                                classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)

                                //将修改的字节码以byte数组返回
                                byte[] bytes = classWriter.toByteArray()
                                //通过文件流写入方式覆盖原先的内容，完成class文件的修改
                                FileOutputStream outputStream = new FileOutputStream(file.path)
                                outputStream.write(bytes)
                                outputStream.close()
                            }
                        }

                        //处理完输入文件后，把输出传递给下一个transform
                        def destDir = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                        FileUtils.copyDirectory(directoryInput.file, destDir)
                }
        }
    }
}