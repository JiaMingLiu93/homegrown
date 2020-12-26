package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class FacadeImplProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.FACADEIMPL;
    }

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return null;
    }

    @Override
    public int getPriority() {
        return 71;
    }
}
