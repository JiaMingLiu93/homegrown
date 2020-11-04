package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youyu
 */
public class ServiceImplProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        //todo
//        String scope = serviceTypeConfig.scope();
//        String model = generatorConfig.model();
//        String simpleSourceName = model+upperCase(scope)+"Service";
//        String path = getPath(serviceTypeConfig.packageName());
//
//        simpleSourceName+="Impl";
//        path+="/impl";
//        List<TypeElement> members = new ArrayList<>();
//
//        members.add(existedTypeElements.get("repo"));
//
//        DefaultElementProcessorContext defaultElementProcessorContext = new DefaultElementProcessorContext(roundContext, processingEnv, facadeTypeElement,
//                simpleSourceName, false, serviceTypeConfig.packageName(), path, "", members, "interface", generatorConfig.model(), existedTypeElements, configs, null);
        return null;
    }

    @Override
    protected Class<? extends Annotation> getTypeConfigClass() {
        return null;
    }

    @Override
    protected AnnotationMapping getAnnotationMapping(Annotation config) {
        return null;
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
