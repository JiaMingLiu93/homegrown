package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class ManagerProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.MANAGER;
    }

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return null;
    }

    @Override
    public int getPriority() {
        return 61;
    }
}
