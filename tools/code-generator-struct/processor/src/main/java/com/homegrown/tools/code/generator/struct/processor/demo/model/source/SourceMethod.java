/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model.source;

import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Accessibility;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Parameter;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.utils.Strings;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a mapping method with source and target type and the mappings between the properties of source and target
 * type.
 * <p>
 * A method can either be configured by itself or by another method for the inverse mapping direction (the appropriate
 * setter on {@link MappingMethodOptions} will be called in this case).
 *
 * @author Gunnar Morling
 */
public class SourceMethod implements Method {

    private final Types typeUtils;

    private final Type declaringMapper;
    private final ExecutableElement executable;
    private final List<Parameter> parameters;

    private final Type returnType;
    private final Accessibility accessibility;
    private final List<Type> exceptionTypes;

    private final List<SourceMethod> prototypeMethods;
    private final Type mapperToImplement;

    private List<String> parameterNames;

    private List<SourceMethod> applicablePrototypeMethods;
    private List<SourceMethod> applicableReversePrototypeMethods;

    private Boolean isValueMapping;
    private Boolean isIterableMapping;
    private Boolean isMapMapping;
    private Boolean isStreamMapping;

    private final boolean verboseLogging;

    public static class Builder {

        private Type declaringMapper = null;
        private Type definingType = null;
        private ExecutableElement executable;
        private List<Parameter> parameters;
        private Type returnType = null;
        private List<Type> exceptionTypes;

        private Types typeUtils;
        private List<SourceMethod> prototypeMethods = Collections.emptyList();

        private boolean verboseLogging;

        public Builder setDeclaringMapper(Type declaringMapper) {
            this.declaringMapper = declaringMapper;
            return this;
        }

        public Builder setExecutable(ExecutableElement executable) {
            this.executable = executable;
            return this;
        }

        public Builder setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder setReturnType(Type returnType) {
            this.returnType = returnType;
            return this;
        }

        public Builder setExceptionTypes(List<Type> exceptionTypes) {
            this.exceptionTypes = exceptionTypes;
            return this;
        }


        public Builder setTypeUtils(Types typeUtils) {
            this.typeUtils = typeUtils;
            return this;
        }


        public Builder setPrototypeMethods(List<SourceMethod> prototypeMethods) {
            this.prototypeMethods = prototypeMethods;
            return this;
        }

        public Builder setDefininingType(Type definingType) {
            this.definingType = definingType;
            return this;
        }

        public Builder setVerboseLogging(boolean verboseLogging) {
            this.verboseLogging = verboseLogging;
            return this;
        }

        public SourceMethod build() {



            return new SourceMethod( this );
        }
    }

    private SourceMethod(Builder builder) {
        this.declaringMapper = builder.declaringMapper;
        this.executable = builder.executable;
        this.parameters = builder.parameters;
        this.returnType = builder.returnType;
        this.exceptionTypes = builder.exceptionTypes;
        this.accessibility = Accessibility.fromModifiers( builder.executable.getModifiers() );


        this.typeUtils = builder.typeUtils;

        this.prototypeMethods = builder.prototypeMethods;
        this.mapperToImplement = builder.definingType;

        this.verboseLogging = builder.verboseLogging;
    }



    @Override
    public boolean matches(List<Type> sourceTypes, Type targetType) {
        return false;
    }

    @Override
    public Type getDeclaringMapper() {
        return declaringMapper;
    }

    @Override
    public ExecutableElement getExecutable() {
        return executable;
    }

    @Override
    public String getName() {
        return executable.getSimpleName().toString();
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public List<Parameter> getSourceParameters() {
        return null;
    }

    @Override
    public List<Parameter> getContextParameters() {
        return null;
    }

    @Override
    public Parameter getMappingTargetParameter() {
        return null;
    }

    @Override
    public boolean isObjectFactory() {
        return false;
    }


    @Override
    public List<String> getParameterNames() {
        if ( parameterNames == null ) {
            List<String> names = new ArrayList<>( parameters.size() );

            for ( Parameter parameter : parameters ) {
                names.add( parameter.getName() );
            }

            parameterNames = Collections.unmodifiableList( names );
        }

        return parameterNames;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public Accessibility getAccessibility() {
        return accessibility;
    }


    @Override
    public Parameter getTargetTypeParameter() {
        return null;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( returnType.toString() );
        sb.append( " " );

        if ( declaringMapper != null ) {
            sb.append( declaringMapper ).append( "." );
        }

        sb.append( getName() ).append( "(" ).append( Strings.join( parameters, ", " ) ).append( ")" );

        return sb.toString();
    }



    /**
     * Whether an implementation of this method must be generated or not.
     *
     * @return true when an implementation is required
     */
    @Override
    public boolean overridesMethod() {
        return declaringMapper == null && executable.getModifiers().contains( Modifier.ABSTRACT );
    }

    @Override
    public List<Type> getThrownTypes() {
        return exceptionTypes;
    }


    @Override
    public boolean isStatic() {
        return executable.getModifiers().contains( Modifier.STATIC );
    }

    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public Type getDefiningType() {
        return mapperToImplement;
    }

    @Override
    public boolean isLifecycleCallbackMethod() {
        return false;
    }


    /**
     * @return returns true for interface methods (see jls 9.4) lacking a default or static modifier and for abstract
     * methods
     */
    public boolean isAbstract() {
        return executable.getModifiers().contains( Modifier.ABSTRACT );
    }

    @Override
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }

    @Override
    public String describe() {
        return null;
    }
}
