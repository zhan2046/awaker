package com.awaker.compiler;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * ruzhan
 */

@AutoService(Processor.class)//自动生成 javax.annotation.processing.IProcessor 文件
@SupportedSourceVersion(SourceVersion.RELEASE_7)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "com.awaker.annotation.SingleDelegate",
        "com.awaker.annotation.MultiDelegate"
})
public class AnnotationProcessor extends AbstractProcessor {

    public static final String PACKAGE = "com.poet.compiler.awaker";

    public Filer filer; //文件相关的辅助类
    public Elements elements; //元素相关的辅助类
    public Messager messager; //日志相关的辅助类
    public Types types;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        types = processingEnv.getTypeUtils();

        new SingleDelegateProcessor().process(set, roundEnvironment, this);
        new MultiDelegateProcessor().process(set, roundEnvironment, this);

        return true;
    }
}
