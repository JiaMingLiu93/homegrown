package com.homegrown.coding.compiler.lang.script.normal.scope;

import com.homegrown.coding.compiler.lang.script.normal.Type;

import java.util.List;

/**
 * @author youyu
 */
public interface FunctionType extends Type {
    Type getReturnType();

    List<Type> getParamTypes();

    boolean matchParameterTypes(List<Type> paramTypes);
}
