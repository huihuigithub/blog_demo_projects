package com.znh.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.znh.annotation.HuiAnnotation;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by znh on 2020/7/29
 * <p>
 * 自定义注解处理器
 */
@AutoService(Processor.class)
public class HuiProcessor extends AbstractProcessor {

    private Elements mElementUtils;
    private Messager mMessager;

    /**
     * 做一些初始化操作
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    /**
     * 支持的注解有哪些
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(HuiAnnotation.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * 支持的jdk版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    /**
     * 注解核心处理方法
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //没有需要处理的注解
        if (set.isEmpty()) return false;

        //获取项目中使用了HuiAnnotation注解的元素集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(HuiAnnotation.class);

        //遍历elements
        for (Element element : elements) {
            //获取元素的包名
            String packageName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
            //获取元素的类名
            String className = element.getSimpleName().toString();
            //自定义需要生成的类文件名称
            String customClassName = className + "Helper";

            //打印日志
            mMessager.printMessage(Diagnostic.Kind.NOTE, "packageName:" + packageName);
            mMessager.printMessage(Diagnostic.Kind.NOTE, "className:" + className);
            mMessager.printMessage(Diagnostic.Kind.NOTE, "customClassName:" + customClassName);

            //创建一个方法参数
            ParameterSpec msgParam = ParameterSpec
                    .builder(String.class, "msg") //定义参数类型和参数名称
                    .build();

            //生成方法
            MethodSpec print = MethodSpec.methodBuilder("print")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(String.class)
                    .addStatement("$T.out.println($S)", System.class, "Hello," + customClassName)//定义打印语句
                    .addParameter(msgParam)
                    .addStatement("return $N", msgParam)
                    .build();

            //生成类
            TypeSpec customClassType = TypeSpec.classBuilder(customClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(print)
                    .build();

            //生成java文件
            JavaFile javaFile = JavaFile.builder(packageName, customClassType).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
