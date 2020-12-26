package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class InfoProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.INFO;
    }

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(this.config, annotationMapping,
                null, true, getFilePath(),
                null, AbstractProcessorContextBuilder.CLASS, null);
    }

    @Override
    public int getPriority() {
        return 11;
    }
}
