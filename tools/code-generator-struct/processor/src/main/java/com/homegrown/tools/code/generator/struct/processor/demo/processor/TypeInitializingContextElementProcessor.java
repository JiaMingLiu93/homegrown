package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import javax.lang.model.element.TypeElement;

/**
 * @author youyu
 */
public class TypeInitializingContextElementProcessor implements ElementProcessor<Void, Void>{
    @Override
    public Void process(ProcessorContext context, TypeElement typeElement, Void sourceModel) {
        return null;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
