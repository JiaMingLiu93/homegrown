package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.RepoTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

import java.lang.annotation.Annotation;

/**
 * @author youyu
 */
public class RepoProcessorContextBuilder extends AbstractProcessorContextBuilder {

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(config,annotationMapping, null,
                true,  getFilePath(), null, CLASS);

    }

    @Override
    protected Class<? extends Annotation> getTypeConfigClass() {
        return RepoTypeConfig.class;
    }

    @Override
    protected AnnotationMapping getAnnotationMapping(Annotation config) {
        return AnnotationMapping.from((RepoTypeConfig)config);
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
