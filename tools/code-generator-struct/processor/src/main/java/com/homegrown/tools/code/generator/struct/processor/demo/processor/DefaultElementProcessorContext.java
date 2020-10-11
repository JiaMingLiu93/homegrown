package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.TypeFactory;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor.ProcessorContext;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author youyu
 */
public class DefaultElementProcessorContext implements ProcessorContext {
    private final ConfigurationProcessor config;
    private final AnnotationMapping annotationMapping;
    private final RoundContext roundContext;
    private final ProcessingEnvironment processingEnvironment;
    private final TypeElement facadeTypeElement;
    private boolean ignoreMethods;
    private final String packageName;
    private final String path;
    private final TypeFactory typeFactory;
    private List<TypeElement> members;
    // interface,class
    private final String type;

    private final Map<String,TypeElement> existedTypeElements;

    private final Map<Class<?>, AnnotationMapping> configs;

    private final Set<? extends Element> rootElements;

    public DefaultElementProcessorContext(ConfigurationProcessor config, AnnotationMapping annotationMapping, TypeElement facadeTypeElement,
                                          boolean ignoreMethods, String path, List<TypeElement> members, String type) {
        this.config = config;
        this.annotationMapping = annotationMapping;
        this.roundContext = config.getRoundContext();
        this.processingEnvironment = config.getProcessingEnv();
        this.facadeTypeElement = facadeTypeElement;
        this.ignoreMethods = ignoreMethods;
        this.packageName = annotationMapping.getPackageName();
        this.path = path;
        this.members = members;
        this.type = type;
        this.existedTypeElements = config.getExistedTypeElements();
        this.configs = config.getConfigs();
        this.rootElements = config.getRootElements();

        this.typeFactory = new TypeFactory(
                processingEnvironment.getElementUtils(),
                processingEnvironment.getTypeUtils(),
                roundContext,
                new HashMap<>(),
                false
        );
    }

    @Override
    public Filer getFiler() {
        return processingEnvironment.getFiler();
    }

    @Override
    public Types getTypeUtils() {
        return processingEnvironment.getTypeUtils();
    }

    @Override
    public Elements getElementUtils() {
        return processingEnvironment.getElementUtils();
    }

    @Override
    public TypeFactory getTypeFactory() {
        return typeFactory;
    }

    @Override
    public boolean ignoreMethods() {
        return ignoreMethods;
    }

    @Override
    public String getSpecifiedMethod() {
        return null;
    }

    @Override
    public String getSimpleClassName() {
        return config.getSimpleClassName(annotationMapping.getClassName());
    }

    @Override
    public boolean isErroneous() {
        return false;
    }

    @Override
    public TypeElement getFacadeTypeElement() {
        return facadeTypeElement;
    }

    @Override
    public String getPackageName() {
        return annotationMapping.getPackageName();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getSuperClassName() {
        return config.getSimpleClassName(annotationMapping.getSuperClassName());
    }

    @Override
    public List<TypeElement> getMembers() {
        return members;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Map<String, TypeElement> getExistedTypeElements() {
        return existedTypeElements;
    }

    @Override
    public TypeElement getSuperClassTypeElement() {
        return existedTypeElements.get(annotationMapping.getSuperClassName());
    }

    @Override
    public String getModelName() {
        return config.getSimpleClassName(config.getAnnotationMappingOfGeneratorConfig().getModel());
    }

    @Override
    public Type getModelType() {
        return typeFactory.getType(existedTypeElements.get(config.getAnnotationMappingOfGeneratorConfig().getModel()));
    }

    @Override
    public Map<Class<?>, AnnotationMapping> getConfigs() {
        return configs;
    }

    @Override
    public Set<? extends Element> getRootElements() {
        return rootElements;
    }

    @Override
    public List<TypeElement> getAnnotations() {
        return Arrays.stream(annotationMapping.getAnnotations()).map(existedTypeElements::get).collect(Collectors.toList());

    }

    @Override
    public List<TypeElement> getExtraImports() {
        return Arrays.stream(annotationMapping.getImports()).map(existedTypeElements::get).collect(Collectors.toList());
    }

    public void setIgnoreMethods(boolean ignoreMethods) {
        this.ignoreMethods = ignoreMethods;
    }
}
