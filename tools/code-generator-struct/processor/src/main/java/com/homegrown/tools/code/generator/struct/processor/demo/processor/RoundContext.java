package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import javax.annotation.processing.Processor;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author youyu
 */
public class RoundContext {
    private final AnnotationProcessorContext annotationProcessorContext;

    public RoundContext(AnnotationProcessorContext annotationProcessorContext) {
        this.annotationProcessorContext = annotationProcessorContext;
    }

    public AnnotationProcessorContext getAnnotationProcessorContext() {
        return annotationProcessorContext;
    }
}
