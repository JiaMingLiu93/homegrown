package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Parameter;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.TypeFactory;
import com.homegrown.tools.code.generator.struct.processor.demo.model.source.SourceMethod;
import com.homegrown.tools.code.generator.struct.processor.demo.utils.Executables;
import org.apache.commons.collections4.CollectionUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youyu
 */
public class TypeRetrievingElementProcessor implements ElementProcessor<Boolean, List<SourceMethod>>{

    private TypeFactory typeFactory;
    private Types typeUtils;

    @Override
    public List<SourceMethod> process(ProcessorContext context, TypeElement typeElement, Boolean isValid) {
        if (!isValid){
            return Collections.emptyList();
        }
        this.typeFactory = context.getTypeFactory();
        this.typeUtils = context.getTypeUtils();

        if (context.ignoreMethods()){
            return Collections.emptyList();
        }

        List<ExecutableElement> executableElements = Executables.getAllEnclosedExecutableElements(context.getElementUtils(), context.getFacadeTypeElement());
        return executableElements.stream().map(executableElement -> getMethod(executableElement,context.getFacadeTypeElement())).collect(Collectors.toList());
    }

    private SourceMethod getMethod(ExecutableElement method, TypeElement typeElement) {
        ExecutableType methodType = typeFactory.getMethodType( (DeclaredType) typeElement.asType(), method );
        List<Parameter> parameters = typeFactory.getParameters( methodType, method );
        Type returnType = typeFactory.getReturnType( methodType );

        List<Type> exceptionTypes = typeFactory.getThrownTypes( methodType );

        return new SourceMethod.Builder()
                .setParameters(parameters)
                .setExecutable(method)
                .setTypeUtils(typeUtils)
                .setReturnType(returnType)
                .setExceptionTypes(exceptionTypes)
                .build();
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
