/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.utils.accessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.Set;

/**
 * This is an abstract implementation of an {@link Accessor} that provides the common implementation.
 *
 * @author Filip Hrisafov
 */
abstract class AbstractAccessor<T extends Element> implements Accessor {

    protected final T element;

    AbstractAccessor(T element) {
        this.element = element;
    }

    @Override
    public String getSimpleName() {
        return element.getSimpleName().toString();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return element.getModifiers();
    }

    @Override
    public T getElement() {
        return element;
    }

}
