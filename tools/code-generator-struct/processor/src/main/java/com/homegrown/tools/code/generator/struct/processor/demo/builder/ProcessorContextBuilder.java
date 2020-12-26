package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.processor.ConfigurationProcessor;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor.ProcessorContext;

/**
 * @author youyu
 */
public interface ProcessorContextBuilder {
    ProcessorContext build(ConfigurationProcessor config);
    int getPriority();
    void postAfter();
}
