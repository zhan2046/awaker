package com.awaker.compiler;

import com.awaker.annotation.Delegate;
import com.awaker.annotation.MultiDelegate;
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

public class MultiDelegateProcessor implements IProcessor {

    @Override
    public void process(Set<? extends TypeElement> set, RoundEnvironment roundEnv,
                        AnnotationProcessor abstractProcessor) {
        // 查询注解是否存在
        Set<? extends Element> elementSet =
                roundEnv.getElementsAnnotatedWith(MultiDelegate.class);
        Set<TypeElement> typeElementSet = ElementFilter.typesIn(elementSet);
        if (typeElementSet == null || typeElementSet.isEmpty()) {
            return;
        }

        // 循环处理注解
        for (TypeElement typeElement : typeElementSet) {
            if (!(typeElement.getKind() == ElementKind.INTERFACE)) {
                continue;
            }

            // 添加构造器
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC);

            // 获取注解
            MultiDelegate annotation = typeElement.getAnnotation(MultiDelegate.class);
            Delegate[] delegates = annotation.Delegates();

            // 创建类名相关 class builder
            TypeSpec.Builder builder = ProcessUtils.createTypeSpecBuilder(typeElement,
                    annotation.classNameImpl());

            // 处理继承其它接口
            builder = processMultiDelegate(abstractProcessor, builder, constructorBuilder,
                    typeElement, delegates);

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
     * 处理继承其它接口
     */
    private TypeSpec.Builder processMultiDelegate(AnnotationProcessor abstractProcessor,
                                                  TypeSpec.Builder builder,
                                                  MethodSpec.Builder constructorBuilder,
                                                  TypeElement typeElement,
                                                  Delegate[] delegates) {
        List<? extends TypeMirror> typeMirrors = typeElement.getInterfaces();
        for (TypeMirror mirror : typeMirrors) {
            TypeElement superTypeElement = (TypeElement) abstractProcessor.types.asElement(mirror);
            if (!(superTypeElement.getKind() == ElementKind.INTERFACE)) { // 只处理接口类型
                continue;
            }

            // 开始处理代理
            for (Delegate delegate : delegates) {
                String superSimpleName = superTypeElement.getSimpleName().toString();
                String delegateClassName = delegate.delegateClassName();

                // 当配置代理class name与继承接口名相同，声明的代理才会处理该接口方法
                if (delegateClassName.equals(superSimpleName)) {
                    builder = ProcessUtils.processDelegate(superTypeElement, builder,
                            constructorBuilder, delegate);

                    // 如果接口中还有继承接口的情况，继续使用代理处理继承接口的方法
                    builder = processSuperMultiDelegate(superTypeElement, abstractProcessor,
                            builder, delegate);
                }
            }
        }
        return builder;
    }

    /**
     * 代理处理继承接口的方法
     */
    private TypeSpec.Builder processSuperMultiDelegate(TypeElement typeElement,
                                                       AnnotationProcessor abstractProcessor,
                                                       TypeSpec.Builder builder, Delegate delegate) {
        List<? extends TypeMirror> typeMirrors = typeElement.getInterfaces();
        for (TypeMirror typeMirror : typeMirrors) {
            TypeElement superTypeElement = (TypeElement) abstractProcessor.types.asElement(typeMirror);
            if (!(superTypeElement.getKind() == ElementKind.INTERFACE)) {
                continue;
            }

            // 代理处理方法
            builder = ProcessUtils.addMethodSpec(builder, superTypeElement,
                    delegate.delegateSimpleName());

            // 递归继续检查，使用代理处理继承接口的方法
            builder = processSuperMultiDelegate(superTypeElement, abstractProcessor, builder, delegate);
        }
        return builder;
    }
}
