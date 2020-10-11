package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.RequestTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

import java.lang.annotation.Annotation;

/**
 * @author youyu
 */
public class RequestProcessorContextBuilder extends AbstractProcessorContextBuilder {

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(config, annotationMapping,
                null, true, getFilePath(),
                null, "class");
    }

    @Override
    protected Class<? extends Annotation> getTypeConfigClass() {
        return RequestTypeConfig.class;
    }

    @Override
    protected AnnotationMapping getAnnotationMapping(Annotation config) {
        return AnnotationMapping.from((RequestTypeConfig)config);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
