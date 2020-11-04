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
public @interface GeneratorConfig {
    // switch to use default assumpsit
    boolean useDefault() default false;

    String facadePath() default "";
    String facadeName() default "";
    String matchSuffix() default "Facade";
    String methodName() default "";
    String scope() default "read";

    //RequestTypeConfig not configuring,program will use the defaultReqClassName
    //to find existed TypeElement
    String defaultReqClassName() default "";
    String infoClassName() default "";

    //must be qualified class name ,not simple class name
    String model() default "";

    String repoPackage() default "";
    String[] repoAnnotations() default {"@Repository"};
    String[] repoImports() default {"import org.springframework.stereotype.Repository;"};
    String repoSuperClassName() default "";
}
