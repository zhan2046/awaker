package com.awaker.compiler;


import com.awaker.annotation.Delegate;
import com.awaker.annotation.RepositoryDelegate;
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
 * Copyright ©2017 by Teambition
 */

public class RepositoryProcessor implements IProcessor {

    @Override
    public void process(Set<? extends TypeElement> set, RoundEnvironment roundEnv,
                        AnnotationProcessor abstractProcessor) {


        ////////////
        /// core annotation compiler
        ////////////


        // 查询注解是否存在
        Set<? extends Element> elementSet =
                roundEnv.getElementsAnnotatedWith(RepositoryDelegate.class);
        Set<TypeElement> typeElementSet = ElementFilter.typesIn(elementSet);
        if (typeElementSet == null || typeElementSet.isEmpty()) {
            return;
        }

        // 循环处理注解
        for (TypeElement rootTypeElement : typeElementSet) {
            if (!(rootTypeElement.getKind() == ElementKind.INTERFACE)) {
                continue;
            }

            // 添加构造器
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC);

            // 处理 RepositoryDelegate
            RepositoryDelegate annotation = rootTypeElement.getAnnotation(RepositoryDelegate.class);
            Delegate[] delegates = annotation.Delegates();

            // create TypeSpec Builder
            TypeSpec.Builder builder = ProcessUtils.createTypeSpecBuilder(rootTypeElement,
                    annotation.classNameImpl());


            ////////////
            /// other annotation compiler
            ////////////


            // 检查是否继承其它接口
            List<? extends TypeMirror> typeMirrors = rootTypeElement.getInterfaces();
            int position = 0;
            for (TypeMirror mirror : typeMirrors) {
                TypeElement superTypeElement =
                        (TypeElement) abstractProcessor.types.asElement(mirror);
                if (!(superTypeElement.getKind() == ElementKind.INTERFACE)) { // 只处理接口类型
                    continue;
                }

                if (superTypeElement.getAnnotation(SingleDelegate.class) != null) {
                    builder = ProcessUtils.processDelegate(superTypeElement, builder,
                            constructorBuilder, delegates[position]);

                    List<? extends TypeMirror> remoteTypeMirrors = superTypeElement.getInterfaces();
                    for (TypeMirror remoteTypeMirror : remoteTypeMirrors) {
                        TypeElement remoteTypeElement =
                                (TypeElement) abstractProcessor.types.asElement(remoteTypeMirror);
                        if (!(remoteTypeElement.getKind() == ElementKind.INTERFACE)) { // 只处理接口类型
                            continue;
                        }
                        if (remoteTypeElement.getAnnotation(SingleDelegate.class) != null) {
                            builder = ProcessUtils.addMethodSpec(builder, remoteTypeElement,
                                    delegates[position].delegateSimpleName());
                        }
                    }
                    position++;
                }
            }


            ////////////
            /// compiler finish
            ////////////


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
}
