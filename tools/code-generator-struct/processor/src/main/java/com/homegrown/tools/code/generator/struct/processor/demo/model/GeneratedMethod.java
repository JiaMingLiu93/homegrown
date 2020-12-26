package com.homegrown.tools.code.generator.struct.processor.demo.model;

import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Accessibility;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.ModelElement;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Parameter;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.model.source.Method;
import org.apache.commons.collections4.CollectionUtils;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author youyu
 */
public class GeneratedMethod extends ModelElement {

    private final String name;
    private final List<Parameter> parameters;
    private final Type returnType;
    private final Accessibility accessibility;
    private final List<Type> thrownTypes;
    private final boolean isStatic;
    private final String resultName;
    private final boolean overridden;

    public GeneratedMethod(String name, List<Parameter> parameters, Type returnType,
                           Accessibility accessibility, List<Type> thrownTypes, boolean isStatic,
                           String resultName,boolean overridden) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.accessibility = accessibility;
        this.thrownTypes = thrownTypes;
        this.isStatic = isStatic;
        this.resultName = resultName;
        this.overridden = overridden;
    }

    public static class Builder {
        private Method method;

        public Builder method(Method method){
            this.method = method;
            return this;
        }

        public GeneratedMethod build(){
            return new GeneratedMethod(method.getName(),
                    method.getParameters(),
                    method.getReturnType(),
                    method.getAccessibility(),
                    method.getThrownTypes(),
                    method.isStatic(),
                    null,
                    method.overridesMethod());
        }
    }


    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();

        if (CollectionUtils.isNotEmpty(parameters)){
            for ( Parameter param : parameters ) {
                types.addAll( param.getType().getImportTypes() );
            }
        }

        if (getReturnType() != null){
            types.addAll( getReturnType().getImportTypes() );
        }

        if (CollectionUtils.isNotEmpty(thrownTypes)){
            for ( Type type : thrownTypes ) {
                types.addAll( type.getImportTypes() );
            }
        }

        return types;
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public List<Type> getThrownTypes() {
        return thrownTypes;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public String getResultName() {
        return resultName;
    }
}
