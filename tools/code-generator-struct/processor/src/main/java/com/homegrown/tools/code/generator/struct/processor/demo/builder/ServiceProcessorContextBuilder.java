package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class ServiceProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(this.config,annotationMapping, config.getFacadeTypeElement(),
                false,  getFilePath(), null, INTERFACE, null);

    }

    @Override
    protected GenerateTypeEnum getType(){
        return GenerateTypeEnum.SERVICE;
    }


    @Override
    public int getPriority() {
        return 41;
    }
}
