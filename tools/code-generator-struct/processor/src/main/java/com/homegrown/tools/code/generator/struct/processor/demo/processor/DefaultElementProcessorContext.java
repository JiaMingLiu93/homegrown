package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.TypeFactory;
import com.homegrown.tools.code.generator.struct.processor.demo.model.source.Method;
import com.homegrown.tools.code.generator.struct.processor.demo.model.source.SourceMethod;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor.ProcessorContext;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author youyu
 */
public class DefaultElementProcessorContext implements ProcessorContext {
    private final ConfigurationProcessor config;
    private final AnnotationMapping annotationMapping;
    private final RoundContext roundContext;
    private final ProcessingEnvironment processingEnvironment;
    private final TypeElement templateTypeElement;
    private boolean ignoreMethods;
    private final String packageName;
    private final String path;
    private final TypeFactory typeFactory;
    private List<TypeElement> members;
    // interface,class
    private final String type;

    private final Map<String,TypeElement> existedTypeElements;

    private final Map<GenerateTypeEnum, AnnotationMapping> configs;

    private final Set<? extends Element> rootElements;

    private List<SourceMethod> methods;

    public DefaultElementProcessorContext(ConfigurationProcessor config, AnnotationMapping annotationMapping, TypeElement templateTypeElement,
                                          boolean ignoreMethods, String path, List<TypeElement> members, String type, Function<TypeFactory, List<SourceMethod>> methods) {
        this.config = config;
        this.annotationMapping = annotationMapping;
        this.roundContext = config.getRoundContext();
        this.processingEnvironment = config.getProcessingEnv();
        this.templateTypeElement = templateTypeElement;
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

        if (methods != null){
            this.methods = methods.apply(this.getTypeFactory());
        }
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
    public TypeElement getTemplateTypeElement() {
        return templateTypeElement;
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
        return config.getSimpleClassName(getSuperClass());
    }

    @Override
    public String getSuperClass(){
        return annotationMapping.getSuperClass();
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
        return existedTypeElements.get(annotationMapping.getSuperClass());
    }

    @Override
    public String getModelName() {
        return config.getSimpleClassName(config.getGeneratorConfig().getModel());
    }

    @Override
    public Type getModelType() {
        return typeFactory.getType(existedTypeElements.get(config.getGeneratorConfig().getModel()));
    }

    @Override
    public Map<GenerateTypeEnum, AnnotationMapping> getConfigs() {
        return configs;
    }

    @Override
    public Set<? extends Element> getRootElements() {
        return rootElements;
    }

    @Override
    public List<? extends TypeMirror> getAnnotations() {
        return annotationMapping.getAnnotations();

    }

    @Override
    public List<? extends TypeMirror> getExtraImports() {
        return annotationMapping.getImports();
    }

    @Override
    public List<Method> getMethods() {
        return new ArrayList<>(methods);
    }

    public void setIgnoreMethods(boolean ignoreMethods) {
        this.ignoreMethods = ignoreMethods;
    }
}
