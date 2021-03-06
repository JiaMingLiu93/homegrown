/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.utils.accessor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

/**
 * This represents an Accessor that can be used for writing/reading a property to/from a bean.
 *
 * @author Filip Hrisafov
 */
public interface Accessor {

    /**
     * This returns the type that this accessor gives as a return.
     *
     * e.g. The {@link ExecutableElement#getReturnType()} if this is a method accessor,
     * or {@link VariableElement#asType()} for field accessors.
     *
     * @return the type that the accessor gives as a return
     */
    TypeMirror getAccessedType();

    /**
     * @return the simple name of the accessor
     */
    String getSimpleName();

    /**
     * @return the set of modifiers that the accessor has
     */
    Set<Modifier> getModifiers();

    /**
     * @return the underlying {@link Element}, {@link VariableElement} or {@link ExecutableElement}
     */
    Element getElement();

    /**
     * The accessor type
     *
     * @return
     */
    AccessorType getAccessorType();
}
