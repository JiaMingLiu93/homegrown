/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model.common;

import com.homegrown.tools.code.generator.struct.processor.demo.utils.Collections;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A parameter of a mapping method.
 *
 * @author Gunnar Morling
 */
public class Parameter extends ModelElement {

    private final Element element;
    private final String name;
    private final String originalName;
    private final Type type;

    private final boolean varArgs;

    private Parameter(Element element, Type type, boolean varArgs) {
        this.element = element;
        this.name = element.getSimpleName().toString();
        this.originalName = name;
        this.type = type;

        this.varArgs = varArgs;
    }

    private Parameter(String name, Type type, boolean mappingTarget, boolean targetType, boolean mappingContext,
                      boolean varArgs) {
        this.element = null;
        this.name = name;
        this.originalName = name;
        this.type = type;

        this.varArgs = varArgs;
    }

    public Parameter(String name, Type type) {
        this( name, type, false, false, false, false );
    }

    public Element getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Type getType() {
        return type;
    }


    @Override
    public String toString() {
        return String.format( format(), type );
    }

    private String format() {
        return null;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type );
    }


    public boolean isVarArgs() {
        return varArgs;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( type != null ? type.hashCode() : 0 );
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Parameter parameter = (Parameter) o;

        if ( !Objects.equals( name, parameter.name ) ) {
            return false;
        }
        return Objects.equals( type, parameter.type );

    }

    public static Parameter forElementAndType(VariableElement element, Type parameterType, boolean isVarArgs) {
        return new Parameter(
            element,
            parameterType,
            isVarArgs
        );
    }

    public static Parameter forForgedMappingTarget(Type parameterType) {
        return new Parameter(
            "mappingTarget",
            parameterType,
            true,
            false,
            false,
            false
        );
    }

}
