package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ConfigurationProcessor;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * @author youyu
 */
public abstract class AbstractProcessorContextBuilder implements ProcessorContextBuilder {
    public static final String CLASS = "class";
    protected ConfigurationProcessor config;
    protected AnnotationMapping annotationMapping;

    @Override
    public ElementProcessor.ProcessorContext build() {
        if (haveConfigured()){
            if (!processed()){
                cacheConfig();
                return doBuild();
            }
        }
        //if client have not configured type to generate code,do nothing
        return null;
    }

    private void cacheConfig() {
        config.getConfigs().put(getTypeConfigClass(), annotationMapping);
    }

    protected abstract ElementProcessor.ProcessorContext doBuild();

    /**
     * check whether type is processed
     * @return
     */
    protected boolean processed(){
        return config.getConfigs().containsKey(getTypeConfigClass());
    }

    protected abstract Class<? extends Annotation> getTypeConfigClass();

    protected abstract AnnotationMapping getAnnotationMapping(Annotation config);

    /**
     * check whether type is configured
     * @return
     */
    public boolean haveConfigured(){
        return config.getConfigElement().getAnnotation(getTypeConfigClass()) != null;
    }

    /**
     * To cache necessary type elements at the first round.
     */
    @Override
    public void init(ConfigurationProcessor config) {
        this.config = config;
        if (haveConfigured()){
            annotationMapping = getAnnotationMapping(config.getConfigElement().getAnnotation(getTypeConfigClass()));
            //cache super class TypeElement
            config.catchAndCacheTypeElement(annotationMapping.getSuperClassName());
            //cache annotation TypeElement
            config.catchAndCacheTypeElements(Arrays.asList(annotationMapping.getAnnotations()));
            //cache imports TypeElement
            config.catchAndCacheTypeElements(Arrays.asList(annotationMapping.getImports()));
        }
    }

    protected String getFilePath(){
        String path = annotationMapping.getPackageName().replace(".", "/");
        return config.getPrePath() + path;
    }
}
