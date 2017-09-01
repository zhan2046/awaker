package com.awaker.utils;

import com.awaker.annotation.Delegate;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * ruzhan
 */

public final class ProcessUtils {

    private static final String CLASS_DESC = "From poet compiler auto create...";

    private static final String SCHEDULERS = "Schedulers";
    private static final String SCHEDULERS_PACKAGE = "io.reactivex.schedulers";
    private static final String SUBSCRIBE_ON = "subscribeOn";
    private static final String THIS_N = "this.$N = $N";
    private static final String RETURN_STRING = "return";

    private ProcessUtils() {
    }

    /**
     * 创建类名相关 class builder
     */
    public static TypeSpec.Builder createTypeSpecBuilder(TypeElement typeElement,
                                                         String classNameImpl) {
        String tagPackage = typeElement.getQualifiedName().toString();
        String tagClazzName = typeElement.getSimpleName().toString();

        // package 全路径移除类名，例如:com.a.b.x -> com.a.b
        String realTagPackage = tagPackage.replace("." + tagClazzName, "");

        // 实现的接口
        ClassName tagClassName =
                ClassName.get(realTagPackage, tagClazzName);

        // 开始构建，初始化类名与相关属性
        return TypeSpec.classBuilder(classNameImpl)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(tagClassName)
                .addJavadoc(CLASS_DESC);
    }

    /**
     * 处理代理: 添加字段，构造器声明，方法代理
     */
    public static TypeSpec.Builder processDelegate(TypeElement typeElement,
                                                   TypeSpec.Builder builder,
                                                   MethodSpec.Builder constructorBuilder,
                                                   Delegate delegate) {
        String delegatePackage = delegate.delegatePackage();
        String delegateClassName = delegate.delegateClassName();
        String delegateSimpleName = delegate.delegateSimpleName();

        // 引用代理对象
        ClassName className =
                ClassName.get(delegatePackage, delegateClassName);

        // 处理 添加字段
        builder.addField(className, delegateSimpleName, Modifier.PRIVATE);

        // 处理 构造器参数，注入代理对象
        setConstructorParam(constructorBuilder,
                className, delegateSimpleName);

        boolean isSchedulers = false;
        if ("IRemoteDataSource".equals(typeElement.getSimpleName().toString())) {
            isSchedulers = true;
        }
        builder = addMethodSpec(builder, typeElement, delegateSimpleName, isSchedulers);

        return builder;
    }

    /**
     * 处理方法
     */
    public static TypeSpec.Builder addMethodSpec(TypeSpec.Builder builder,
                                                 TypeElement typeElement,
                                                 String delegateSimpleName) {
        return addMethodSpec(builder, typeElement, delegateSimpleName, false);
    }

    /**
     * 处理方法
     */
    public static TypeSpec.Builder addMethodSpec(TypeSpec.Builder builder,
                                                 TypeElement typeElement,
                                                 String delegateSimpleName,
                                                 boolean isSchedulers) {
        // 添加方法集，接口中element内部全是方法
        List<? extends Element> elements = typeElement.getEnclosedElements();
        for (Element element : elements) {
            if (!(element instanceof ExecutableElement)) {
                continue;
            }
            ExecutableElement executableElement = (ExecutableElement) element;

            // 添加方法
            String methodName = executableElement.getSimpleName().toString();
            MethodSpec.Builder methodBuilder =
                    MethodSpec.methodBuilder(methodName)
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(Override.class);

            // 设置方法返回值类型
            TypeMirror typeMirror = executableElement.getReturnType();
            TypeName typeName = TypeName.get(typeMirror);
            methodBuilder.returns(typeName);

            // 设置方法返回参数字符串片段
            String paramStr = "";
            for (VariableElement variableElement : executableElement.getParameters()) {
                String paramName = variableElement.getSimpleName().toString();
                methodBuilder.addParameter(TypeName.get(variableElement.asType()), paramName);
                paramStr += paramName + ",";
            }

            String realParamStr = "";
            // 设置方法返回参数字符串
            if (!"".equals(paramStr)) {
                realParamStr = paramStr.substring(0, paramStr.length() - 1);
            }

            String returnStr = "";
            if (!TypeName.VOID.equals(typeName)) {
                returnStr = RETURN_STRING;
            }

            if (isSchedulers) { // add Schedulers.io()
                methodBuilder.addStatement(returnStr + " $N." + methodName
                                + "(" + realParamStr + ")"
                                + "." + SUBSCRIBE_ON + "(" + "$T.io())",
                        delegateSimpleName,
                        ClassName.get(SCHEDULERS_PACKAGE, SCHEDULERS));

            } else { // normal return
                methodBuilder.addStatement(returnStr + " $N."
                                + methodName
                                + "(" + realParamStr + ")",
                        delegateSimpleName);
            }

            // 设置方法属性完毕，添加到构建对象中
            builder.addMethod(methodBuilder.build());
        }
        return builder;
    }

    /**
     * 构造器 注入代理
     */
    public static MethodSpec.Builder setConstructorParam(MethodSpec.Builder builder,
                                                         ClassName className,
                                                         String delegateSimpleName) {
        return builder.addParameter(className, delegateSimpleName)
                .addStatement(THIS_N, delegateSimpleName, delegateSimpleName);
    }
}
