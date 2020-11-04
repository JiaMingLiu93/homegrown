/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model;

import com.homegrown.tools.code.generator.struct.processor.demo.model.common.ModelElement;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents a Java 5 annotation.
 *
 * @author Gunnar Morling
 */
public class Annotation extends ModelElement {

    private final Type type;

    /**
     * List of annotation attributes. Quite simplistic, but it's sufficient for now.
     */
    private List<String> properties;

    public Annotation(Type type) {
        this( type, Collections.emptyList() );
    }

    public Annotation(Type type, List<String> properties) {
        this.type = type;
        this.properties = properties;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.singleton( type );
    }

    public List<String> getProperties() {
        return properties;
    }
}
