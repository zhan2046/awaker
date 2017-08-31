package com.awaker.compiler;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public interface IProcessor {

    void process(Set<? extends TypeElement> set, RoundEnvironment roundEnv,
                 AnnotationProcessor abstractProcessor);

}
