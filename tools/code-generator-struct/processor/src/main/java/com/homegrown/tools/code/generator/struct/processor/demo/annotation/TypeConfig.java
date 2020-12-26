package com.homegrown.tools.code.generator.struct.processor.demo.annotation;

import java.lang.annotation.*;

/**
 * todo 1.template class 2. append
 * @author youyu
 */
@Repeatable(TypeConfigs.class)
public @interface TypeConfig {
    GenerateTypeEnum type();

    String packageName() default "";
    String className() default "";
    Class<?>[] annotations() default {};
    Class<?>[] imports() default {};

    String superClass() default "";
    String superInterface() default "";
    String scope() default "read";
}
