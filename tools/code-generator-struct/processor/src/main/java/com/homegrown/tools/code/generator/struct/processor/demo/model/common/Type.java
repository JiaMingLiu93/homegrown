/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.model.common;


import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents (a reference to) the type of a bean property, parameter etc. Types are managed per generated source file.
 * Each type corresponds to a {@link TypeMirror}, i.e. there are different instances for e.g. {@code Set<String>} and
 * {@code Set<Integer>}.
 * <p>
 * Allows for a unified handling of declared and primitive types and usage within templates. Instances are obtained
 * through {@link TypeFactory}.
 *
 * @author youyu
 */
public class Type extends ModelElement implements Comparable<Type> {

    private final Types typeUtils;
    private final Elements elementUtils;

    private final TypeMirror typeMirror;
    private final TypeElement typeElement;
    private final List<Type> typeParameters;
    private final TypeFactory typeFactory;
    private final ImplementationType implementationType;

    private final Type componentType;

    private final String packageName;
    private final String name;
    private final String qualifiedName;

    private final boolean isInterface;
    private final boolean isEnumType;
    private final boolean isIterableType;
    private final boolean isCollectionType;
    private final boolean isMapType;
    private final boolean isVoid;
    private final boolean isStream;
    private final boolean isLiteral;

    private final boolean loggingVerbose;

    private final List<String> enumConstants;

    private final Map<String, String> toBeImportedTypes;
    private final Map<String, String> notToBeImportedTypes;
    private Boolean isToBeImported;


    private List<ExecutableElement> allMethods = null;
    private List<VariableElement> allFields = null;
    private List<Element> recordComponents = null;


    private Type boundingBase = null;

    private Boolean hasAccessibleConstructor;
    public Type(Types typeUtils, Elements elementUtils, TypeFactory typeFactory,
                TypeMirror typeMirror, TypeElement typeElement,
                List<Type> typeParameters, ImplementationType implementationType, Type componentType,
                String packageName, String name, String qualifiedName,
                boolean isInterface, boolean isEnumType, boolean isIterableType,
                boolean isCollectionType, boolean isMapType, boolean isStreamType,
                Map<String, String> toBeImportedTypes,
                Map<String, String> notToBeImportedTypes,
                Boolean isToBeImported,
                boolean isLiteral, boolean loggingVerbose) {

        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.typeFactory = typeFactory;

        this.typeMirror = typeMirror;
        this.typeElement = typeElement;
        this.typeParameters = typeParameters;
        this.componentType = componentType;
        this.implementationType = implementationType;

        this.packageName = packageName;
        this.name = name;
        this.qualifiedName = qualifiedName;

        this.isInterface = isInterface;
        this.isEnumType = isEnumType;
        this.isIterableType = isIterableType;
        this.isCollectionType = isCollectionType;
        this.isMapType = isMapType;
        this.isStream = isStreamType;
        this.isVoid = typeMirror.getKind() == TypeKind.VOID;
        this.isLiteral = isLiteral;

        if ( isEnumType ) {
            enumConstants = new ArrayList<>();

            for ( Element element : typeElement.getEnclosedElements() ) {
                // #162: The check for visibility shouldn't be required, but the Eclipse compiler implementation
                // exposes non-enum members otherwise
                if ( element.getKind() == ElementKind.ENUM_CONSTANT &&
                        element.getModifiers().contains( Modifier.PUBLIC ) ) {
                    enumConstants.add( element.getSimpleName().toString() );
                }
            }
        }
        else {
            enumConstants = Collections.emptyList();
        }

        this.isToBeImported = isToBeImported;
        this.toBeImportedTypes = toBeImportedTypes;
        this.notToBeImportedTypes = notToBeImportedTypes;

        this.loggingVerbose = loggingVerbose;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> result = new HashSet<>();

        if ( getTypeMirror().getKind() == TypeKind.DECLARED ) {
            result.add( this );
        }

        if ( componentType != null ) {
            result.addAll( componentType.getImportTypes() );
        }

        for ( Type parameter : typeParameters ) {
            result.addAll( parameter.getImportTypes() );
        }

        if ( ( isWildCardExtendsBound() || isWildCardSuperBound() ) && getTypeBound() != null ) {
            result.addAll( getTypeBound().getImportTypes() );
        }

        return result;
    }

    public boolean isWildCardExtendsBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            result = wildcardType.getExtendsBound() != null;
        }
        return result;
    }

    public boolean isWildCardSuperBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            result = wildcardType.getSuperBound() != null;
        }
        return result;
    }
//CHECKSTYLE:OFF

    /**
     * Whether this type is to be imported by means of an import statement in the currently generated source file
     * (it can be referenced in the generated source using its simple name) or not (referenced using the FQN).
     *
     * @return {@code true} if the type is imported, {@code false} otherwise.
     */
    public boolean isToBeImported() {
        if ( isToBeImported == null ) {
            String trimmedName = trimSimpleClassName( name );
            if ( notToBeImportedTypes.containsKey( trimmedName ) ) {
                isToBeImported = false;
                return isToBeImported;
            }
            String trimmedQualifiedName = trimSimpleClassName( qualifiedName );
            String importedType = toBeImportedTypes.get( trimmedName );

            isToBeImported = false;
            if ( importedType != null ) {
                if ( importedType.equals( trimmedQualifiedName ) ) {
                    isToBeImported = true;
                }
            } else {
                toBeImportedTypes.put( trimmedName, trimmedQualifiedName );
                isToBeImported = true;
            }
        }
        return isToBeImported;
    }

    /**
     * It strips all the {@code []} from the {@code className}.
     *
     * E.g.
     * <pre>
     *     trimSimpleClassName("String[][][]") -> "String"
     *     trimSimpleClassName("String[]") -> "String"
     * </pre>
     *
     * @param className that needs to be trimmed
     *
     * @return the trimmed {@code className}, or {@code null} if the {@code className} was {@code null}
     */
    private String trimSimpleClassName(String className) {
        if ( className == null ) {
            return null;
        }
        String trimmedClassName = className;
        while ( trimmedClassName.endsWith( "[]" ) ) {
            trimmedClassName = trimmedClassName.substring( 0, trimmedClassName.length() - 2 );
        }
        return trimmedClassName;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public boolean isArrayType() {
        return componentType != null;
    }

    public String getPackageName() {
        return packageName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public Type getComponentType() {
        return componentType;
    }

    public String getImportName() {
        return isArrayType() ? trimSimpleClassName( qualifiedName ) : qualifiedName;
    }


    public boolean isPrimitive() {
        return typeMirror.getKind().isPrimitive();
    }

    public boolean isVoid() {
        return isVoid;
    }

    /**
     * Establishes the type bound:
     * <ol>
     * <li>{@code <? extends Number>}, returns Number</li>
     * <li>{@code <? super Number>}, returns Number</li>
     * <li>{@code <?>}, returns Object</li>
     * <li>{@code <T extends Number>, returns Number}</li>
     * </ol>
     * @return the bound for this parameter
     */
    public Type getTypeBound() {
        if ( boundingBase != null ) {
            return boundingBase;
        }

        boundingBase = typeFactory.getType( typeFactory.getTypeBound( getTypeMirror() ) );

        return boundingBase;
    }

    public String getName() {
        return name;
    }

    public String getFullyQualifiedName() {
        return qualifiedName;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isEnumType() {
        return isEnumType;
    }

    /**
     * Whether this type is a sub-type of {@link Iterable} or an array type.
     *
     * @return {@code true} if this type is a sub-type of {@link Iterable} or an array type, {@code false} otherwise.
     */
    public boolean isIterableType() {
        return isIterableType || isArrayType();
    }

    public boolean isCollectionType() {
        return isCollectionType;
    }

    public boolean isMapType() {
        return isMapType;
    }

    public boolean isStreamType() {
        return isStream;
    }

    public boolean isLiteral() {
        return isLiteral;
    }


    @Override
    public int hashCode() {
        // javadoc typemirror: "Types should be compared using the utility methods in Types. There is no guarantee
        // that any particular type will always be represented by the same object." This is true when the objects
        // are in another jar than the mapper. So the qualfiedName is a better candidate.
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        Type other = (Type) obj;

        return typeUtils.isSameType( typeMirror, other.typeMirror );
    }

    @Override
    public int compareTo(Type o) {
        return getFullyQualifiedName().compareTo( o.getFullyQualifiedName() );
    }

    @Override
    public String toString() {
        return typeMirror.toString();
    }
}
