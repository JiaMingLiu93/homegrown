package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class ServiceImplProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.SERVICEIMPL;
    }

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
    public int getPriority() {
        return 51;
    }
}
