package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

/**
 * @author youyu
 */
public class RepoProcessorContextBuilder extends AbstractProcessorContextBuilder {

    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.REPO;
    }

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(this.config,annotationMapping, null,
                true,  getFilePath(), null, CLASS, null);

    }

    @Override
    public int getPriority() {
        return 31;
    }
}
