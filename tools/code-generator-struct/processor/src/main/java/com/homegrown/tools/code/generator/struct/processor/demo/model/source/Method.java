/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model.source;

import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Accessibility;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Parameter;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;

import javax.lang.model.element.ExecutableElement;
import java.util.List;

/**
 * This interface makes available common method properties and a matching method There are 2 known implementors:
 * {@link BuiltInMethod} and {@link SourceMethod}
 *
 * @author youyu
 */
public interface Method {

    /**
     * Checks whether the provided sourceType and provided targetType match with the parameter respectively return type
     * of the method. The check also should incorporate wild card and generic type variables
     *
     * @param sourceTypes the sourceTypes to match to the parameter
     * @param targetType the targetType to match to the returnType
     *
     * @return true when match
     */
    boolean matches(List<Type> sourceTypes, Type targetType );

    /**
     * Returns the mapper type declaring this method if it is not declared by the mapper interface currently processed
     * but by another mapper imported via {@code Mapper#users()}.
     *
     * @return The declaring mapper type
     */
    Type getDeclaringMapper();

    /**
     * Returns then name of the method.
     *
     * @return method name
     */
    String getName();

    /**
     * In contrast to {@link #getSourceParameters()} this method returns all parameters
     *
     * @return all parameters
     */
    List<Parameter> getParameters();

    /**
     * returns the list of 'true' source parameters excluding the parameter(s) that are designated as target, target
     * type or context parameter.
     *
     * @return list of 'true' source parameters
     */
    List<Parameter> getSourceParameters();

    /**
     * returns the list of mapping context parameters, i.e. those parameters that are annotated with
     * {@link org.mapstruct.Context}.
     *
     * @return list of context parameters
     */
    List<Parameter> getContextParameters();

    /**
     * Returns the parameter designated as mapping target (if present) {@link org.mapstruct.MappingTarget}
     *
     * @return mapping target parameter (when present) null otherwise.
     */
    Parameter getMappingTargetParameter();

    /**
     * Returns whether the meethod is designated as bean factory for
     * mapping target {@link  org.mapstruct.ObjectFactory }
     *
     * @return true if it is a target bean factory.
     */
    boolean isObjectFactory();

    /**
     * Returns the parameter designated as target type (if present) {@link org.mapstruct.TargetType }
     *
     * @return target type parameter (when present) null otherwise.
     */
    Parameter getTargetTypeParameter();


    /**
     * Returns the {@link Accessibility} of this method.
     *
     * @return the {@link Accessibility} of this method
     */
    Accessibility getAccessibility();

    /**
     * Returns the return type of the method
     *
     * @return return type
     */
    Type getReturnType();

    /**
     * Returns all exceptions thrown by this method
     *
     * @return exceptions thrown
     */
    List<Type> getThrownTypes();


    /**
     *
     * @return the names of the parameters of this mapping method
     */
    List<String> getParameterNames();

    /**
     * Whether this method overrides an abstract method.
     *
     * @return true when an abstract method is overridden.
     */
    boolean overridesMethod();

    ExecutableElement getExecutable();

    /**
     * Whether this method is static or an instance method
     *
     * @return true when static.
     */
    boolean isStatic();

    /**
     * Whether this method is Java 8 default method
     *
     * @return true when Java 8 default method
     */
    boolean isDefault();

    /**
     *
     *  @return the Type (class or interface) that defines this method.
     */
    Type getDefiningType();

    /**
     * @return {@code true}, if the method represents a mapping lifecycle callback (Before/After mapping method)
     */
    boolean isLifecycleCallbackMethod();

    /**
     * @return {@code true}, if the method is an update method, i.e. it has a parameter annotated with
     *         {@code @MappingTarget}.
     */
    boolean isUpdateMethod();

    /**
     * @return the short name for error messages when verbose, full name when not
     */
    String describe();
}
