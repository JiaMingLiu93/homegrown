/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model.common;

/**
 * This is a wrapper class for the Implementation types that are used within MapStruct. It contains all the
 * information needed for an Iterable creation
 *
 * @author Filip Hrisafov
 */
public class ImplementationType {

    private final Type type;
    private final boolean initialCapacityConstructor;
    private final boolean loadFactorAdjustment;

    private ImplementationType(Type type, boolean initialCapacityConstructor, boolean loadFactorAdjustment) {
        this.type = type;
        this.initialCapacityConstructor = initialCapacityConstructor;
        this.loadFactorAdjustment = loadFactorAdjustment;
    }

    public static ImplementationType withDefaultConstructor(Type type) {
        return new ImplementationType( type, false, false );
    }

    public static ImplementationType withInitialCapacity(Type type) {
        return new ImplementationType( type, true, false );
    }

    public static ImplementationType withLoadFactorAdjustment(Type type) {
        return new ImplementationType( type, true, true );
    }

    /**
     * Creates new {@link ImplementationType} that has the same {@link #initialCapacityConstructor} and
     * {@link #loadFactorAdjustment}, but a different underlying {@link Type}
     *
     * @param type to be replaced
     *
     * @return a new implementation type with the given {@code type}
     */
    public ImplementationType createNew(Type type) {
        return new ImplementationType( type, initialCapacityConstructor, loadFactorAdjustment );
    }

    /**
     * @return the underlying {@link Type}
     */
    public Type getType() {
        return type;
    }

    /**
     * @return {@code true} if the underlying type has a constructor for {@code int} {@code initialCapacity}, {@code
     * false} otherwise
     */
    public boolean hasInitialCapacityConstructor() {
        return initialCapacityConstructor;
    }

    /**
     * If this method returns {@code true} then {@link #hasInitialCapacityConstructor()} also returns {@code true}
     *
     * @return {@code true} if the underlying type needs adjustment for the initial capacity constructor, {@code
     * false} otherwise
     */
    public boolean isLoadFactorAdjustment() {
        return loadFactorAdjustment;
    }
}
