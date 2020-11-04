package com.homegrown.tools.code.generator.struct.processor.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author youyu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface InfoTypeConfig {
    String packageName() default "";
    String[] annotations() default {};
    String[] imports() default {};
    String superClassName() default "";
}
