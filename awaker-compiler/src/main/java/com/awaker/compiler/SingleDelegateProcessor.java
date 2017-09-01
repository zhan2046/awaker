package com.awaker.compiler;

import com.awaker.annotation.Delegate;
import com.awaker.annotation.SingleDelegate;
import com.awaker.utils.ProcessUtils;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

/**
 * ruzhan
 */

public class SingleDelegateProcessor implements IProcessor {

    @Override
    public void process(Set<? extends TypeElement> set, RoundEnvironment roundEnv,
                        AnnotationProcessor abstractProcessor) {
        // 查询注解是否存在
        Set<? extends Element> elementSet =
                roundEnv.getElementsAnnotatedWith(SingleDelegate.class);
        Set<TypeElement> typeElementSet = ElementFilter.typesIn(elementSet);
        if (typeElementSet == null || typeElementSet.isEmpty()) {
            return;
        }

        // 循环处理注解
        for (TypeElement typeElement : typeElementSet) {
            if (!(typeElement.getKind() == ElementKind.INTERFACE)) { // 只处理接口类型
                continue;
            }

            // 处理 SingleDelegate，只处理 annotation.classNameImpl() 不为空的注解
            SingleDelegate annotation = typeElement.getAnnotation(SingleDelegate.class);
            if ("".equals(annotation.classNameImpl())) {
                continue;
            }
            Delegate delegate = annotation.delegate();

            // 添加构造器
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC);

            // 创建类名相关 class builder
            TypeSpec.Builder builder =
                    ProcessUtils.createTypeSpecBuilder(typeElement, annotation.classNameImpl());

            // 处理 delegate
            builder = ProcessUtils.processDelegate(typeElement, builder,
                    constructorBuilder, delegate);

            // 检查是否继承其它接口
            builder = processSuperSingleDelegate(abstractProcessor, builder, constructorBuilder, typeElement);

            // 完成构造器
            builder.addMethod(constructorBuilder.build());

            // 创建 JavaFile
            JavaFile javaFile = JavaFile.builder(AnnotationProcessor.PACKAGE, builder.build()).build();
            try {
                javaFile.writeTo(abstractProcessor.filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查是否继承其它接口
     */
    private TypeSpec.Builder processSuperSingleDelegate(AnnotationProcessor abstractProcessor,
                                                        TypeSpec.Builder builder,
                                                        MethodSpec.Builder constructorBuilder,
                                                        TypeElement typeElement) {
        // 遍历当前 typeElement 继承的接口
        List<? extends TypeMirror> typeMirrors = typeElement.getInterfaces();
        for (TypeMirror mirror : typeMirrors) {
            TypeElement superTypeElement = (TypeElement) abstractProcessor.types.asElement(mirror);
            if (!(superTypeElement.getKind() == ElementKind.INTERFACE)) { // 只处理接口类型
                continue;
            }
            SingleDelegate superAnnotation = superTypeElement.getAnnotation(SingleDelegate.class);

            if (superAnnotation != null) {  // 处理继承接口 SingleDelegate
                builder = ProcessUtils.processDelegate(superTypeElement, builder,
                        constructorBuilder, superAnnotation.delegate());

                //递归继续检查是否继承其它接口
                builder = processSuperSingleDelegate(abstractProcessor, builder, constructorBuilder,
                        superTypeElement);
            }
        }
        return builder;
    }
}
