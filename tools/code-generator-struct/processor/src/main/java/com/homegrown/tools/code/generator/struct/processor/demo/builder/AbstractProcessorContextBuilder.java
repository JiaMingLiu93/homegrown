package com.homegrown.tools.code.generator.struct.processor.demo.builder;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.TypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ConfigurationProcessor;
import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author youyu
 */
public abstract class AbstractProcessorContextBuilder implements ProcessorContextBuilder {
    public static final String CLASS = "class";
    public static final String INTERFACE = "interface";
    protected ConfigurationProcessor config;
    protected AnnotationMapping annotationMapping;

    @Override
    public ElementProcessor.ProcessorContext build(ConfigurationProcessor config) {
        this.config = config;
        this.annotationMapping = config.getConfigs().get(getType());

        if (haveConfigured()){
            if (!processed()){
                recordProcessing();
                return doBuild();
            }
        }
        //if client have not configured type to generate code,do nothing
        return null;
    }

    /**
     * record type processing
     */
    private void recordProcessing() {
        config.getConfigRecords().add(getType());
    }

    protected abstract GenerateTypeEnum getType();

    protected abstract ElementProcessor.ProcessorContext doBuild();

    /**
     * check whether type is processed
     * @return
     */
    protected boolean processed(){
        return config.getConfigRecords().contains(getType());
    }

    /**
     * check whether type is configured
     * @return
     */
    public boolean haveConfigured(){
        AnnotationMapping annotationMapping = config.getConfigs().get(getType());
        if (annotationMapping == null){
            return false;
        }
        return Objects.equals(annotationMapping.getType(),getType());
    }

    protected String getFilePath(){
        String path = annotationMapping.getPackageName().replace(".", "/");
        return config.getPrePath() + path;
    }

    @Override
    public void postAfter() {
        config.catchAndCacheTypeElement(annotationMapping.getQualifiedClassName());
    }
}
