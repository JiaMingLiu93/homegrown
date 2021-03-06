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
public @interface ServiceTypeConfig {
    String packageName() default "";
    Class<?>[] annotations() default {};
    Class<?>[] imports() default {};
    Class<?> superClass() default Object.class;
    String scope() default "read";
    String className() default "";
}
