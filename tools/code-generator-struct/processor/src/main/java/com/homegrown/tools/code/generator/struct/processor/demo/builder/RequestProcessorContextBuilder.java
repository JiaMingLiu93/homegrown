package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class RequestProcessorContextBuilder extends AbstractProcessorContextBuilder {

    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.REQUEST;
    }

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(this.config, annotationMapping,
                null, true, getFilePath(),
                null, "class", null);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
