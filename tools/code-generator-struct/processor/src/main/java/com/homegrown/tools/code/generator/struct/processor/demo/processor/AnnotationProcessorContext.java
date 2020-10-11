package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author youyu
 */
public class AnnotationProcessorContext {
    private Elements elementUtils;
    private Types typeUtils;

    public AnnotationProcessorContext(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public void setElementUtils(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public void setTypeUtils(Types typeUtils) {
        this.typeUtils = typeUtils;
    }
}
