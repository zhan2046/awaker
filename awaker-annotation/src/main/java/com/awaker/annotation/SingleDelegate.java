package com.awaker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ruzhan
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface SingleDelegate {

    /**
     * impl class name
     */
    String classNameImpl();

    /**
     * delegate data
     */
    Delegate delegate();
}

