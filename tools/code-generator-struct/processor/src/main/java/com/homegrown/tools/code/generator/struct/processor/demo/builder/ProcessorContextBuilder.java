package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.processor.ConfigurationProcessor;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor.ProcessorContext;

/**
 * @author youyu
 */
public interface ProcessorContextBuilder {
    /**
     * init before generate
     */
    void init(ConfigurationProcessor config);
    ProcessorContext build();
    int getPriority();
}
