package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.ServiceTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youyu
 */
public class ServiceProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
//        String scope = serviceTypeConfig.scope();
//        String model = generatorConfig.model();
//        String simpleSourceName = model+upperCase(scope)+"Service";
//
//        String path = getPath(serviceTypeConfig.packageName());
//
//        //cache repo TypeElement if exist default
//        catchAndCacheTypeElement(getDefaultRepoClassName(generatorConfig),"repo");
//
//        //round of service api
//        DefaultElementProcessorContext defaultElementProcessorContext = new DefaultElementProcessorContext(roundContext, processingEnv, facadeTypeElement,
//                simpleSourceName, false, serviceTypeConfig.packageName(), path, "", null, "interface", generatorConfig.model(), existedTypeElements, configs, rootElements);
        return null;
    }

    @Override
    protected Class<? extends Annotation> getTypeConfigClass() {
        return ServiceTypeConfig.class;
    }

    @Override
    protected AnnotationMapping getAnnotationMapping(Annotation config) {
        return AnnotationMapping.from((ServiceTypeConfig)config);
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
