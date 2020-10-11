/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model.common;


import com.homegrown.tools.code.generator.struct.processor.demo.writer.FreeMarkerWritable;

import java.util.Set;

/**
 * Base class of all model elements. Implements the {@link com.homegrown.tools.code.generator.struct.processor.demo.writer.Writable contract to write model elements into source code
 * files.
 *
 * @author youyu
 */
public abstract class ModelElement extends FreeMarkerWritable {

    /**
     * Returns a set containing those {@link Type}s referenced by this model element for which an import statement needs
     * to be declared.
     *
     * @return A set with type referenced by this model element. Must not be {@code null}.
     */
    public abstract Set<Type> getImportTypes();
}
