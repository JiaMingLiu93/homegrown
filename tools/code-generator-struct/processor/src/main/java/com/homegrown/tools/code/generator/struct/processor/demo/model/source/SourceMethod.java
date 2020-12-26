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

/**
 * Represents a method.
 */
public class SourceMethod implements Method {

    private final Types typeUtils;

    private final String name;
    private final Type declaringTemplate;
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
        private String name;

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

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public SourceMethod build() {



            return new SourceMethod( this );
        }
    }

    private SourceMethod(Builder builder) {
        this.declaringTemplate = builder.declaringMapper;
        this.executable = builder.executable;
        this.parameters = builder.parameters;
        this.returnType = builder.returnType;
        this.exceptionTypes = builder.exceptionTypes;

        if (builder.executable != null){
            this.accessibility = Accessibility.fromModifiers( builder.executable.getModifiers() );
        }else {
            this.accessibility = Accessibility.PUBLIC;
        }


        this.typeUtils = builder.typeUtils;

        this.prototypeMethods = builder.prototypeMethods;
        this.mapperToImplement = builder.definingType;

        this.verboseLogging = builder.verboseLogging;

        this.name = builder.name;
    }



    @Override
    public boolean matches(List<Type> sourceTypes, Type targetType) {
        return false;
    }

    @Override
    public Type getDeclaringTemplate() {
        return declaringTemplate;
    }

    @Override
    public ExecutableElement getExecutable() {
        return executable;
    }

    @Override
    public String getName() {
        return name;
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
    public String toString() {
        StringBuilder sb = new StringBuilder( returnType.toString() );
        sb.append( " " );

        if ( declaringTemplate != null ) {
            sb.append(declaringTemplate).append( "." );
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
        if (hasExecutable()){
            return declaringTemplate == null && executable.getModifiers().contains( Modifier.ABSTRACT );
        }
        return false;
    }

    @Override
    public List<Type> getThrownTypes() {
        return exceptionTypes;
    }


    @Override
    public boolean isStatic() {
        if (hasExecutable()){
            return executable.getModifiers().contains( Modifier.STATIC );
        }
        return false;
    }

    private boolean hasExecutable() {
        return executable != null;
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
        if (hasExecutable()){
            return executable.getModifiers().contains( Modifier.ABSTRACT );
        }
        return false;
    }

    @Override
    public String describe() {
        return null;
    }
}
