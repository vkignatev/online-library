package com.sber.java13spring.java13springproject.libraryproject.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MySecuredAnnotation {
    String[] value();
}
