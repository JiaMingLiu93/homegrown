/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.utils.accessor;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Accessor} that wraps a {@link VariableElement}.
 *
 * @author Filip Hrisafov
 */
public class FieldElementAccessor extends AbstractAccessor<VariableElement> {

    public FieldElementAccessor(VariableElement element) {
        super( element );
    }

    @Override
    public TypeMirror getAccessedType() {
        return element.asType();
    }

    @Override
    public String toString() {
        return element.toString();
    }

    @Override
    public AccessorType getAccessorType() {
        return AccessorType.FIELD;
    }

}
