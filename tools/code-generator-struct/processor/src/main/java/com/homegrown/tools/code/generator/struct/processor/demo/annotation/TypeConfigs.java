package com.homegrown.tools.code.generator.struct.processor.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author youyu
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeConfigs {
    TypeConfig[] value();
}
