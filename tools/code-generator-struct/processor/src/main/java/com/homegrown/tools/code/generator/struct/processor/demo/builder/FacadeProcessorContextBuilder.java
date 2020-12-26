package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Parameter;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.TypeFactory;
import com.homegrown.tools.code.generator.struct.processor.demo.model.source.SourceMethod;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.DefaultElementProcessorContext;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author youyu
 */
public class FacadeProcessorContextBuilder extends AbstractProcessorContextBuilder{
    @Override
    protected GenerateTypeEnum getType() {
        return GenerateTypeEnum.FACADE;
    }

    @Override
    protected ElementProcessor.ProcessorContext doBuild() {
        return new DefaultElementProcessorContext(this.config, annotationMapping,
                null, false, getFilePath(),
                null, AbstractProcessorContextBuilder.INTERFACE, getMethodsFunction());
    }

    private Function<TypeFactory, List<SourceMethod>> getMethodsFunction() {
        return typeFactory -> {

            List<SourceMethod> methods = new ArrayList<>();

            String methodName = config.getGeneratorConfig().getMethodName();
            AnnotationMapping requestConfig = config.getConfigs().get(GenerateTypeEnum.REQUEST);
            AnnotationMapping infoConfig = config.getConfigs().get(GenerateTypeEnum.INFO);
            TypeElement requestElement = config.getExistedTypeElements().get(requestConfig.getPackageName() + "." + requestConfig.getClassName());
            TypeElement infoElement = config.getExistedTypeElements().get(infoConfig.getPackageName() + "." + infoConfig.getClassName());

            Parameter parameter = new Parameter(requestConfig.getClassName(), typeFactory.getType(requestElement));

            ArrayList<Parameter> parameters = new ArrayList<>();
            parameters.add(parameter);

            SourceMethod sourceMethod = new SourceMethod.Builder()
                    .setName(methodName)
                    .setParameters(parameters)
                    .setReturnType(typeFactory.getType(infoElement))
                    .build();
            methods.add(sourceMethod);

            return methods;
        };
    }

    @Override
    public int getPriority() {
        return 21;
    }
}
