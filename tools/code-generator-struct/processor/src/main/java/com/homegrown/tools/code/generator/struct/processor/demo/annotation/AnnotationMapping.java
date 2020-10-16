package com.homegrown.tools.code.generator.struct.processor.demo.annotation;

import com.homegrown.tools.code.generator.struct.processor.demo.utils.APUtils;

import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Mapping of config annotation,such as {@link RequestTypeConfig}
 * @author youyu
 */
public class AnnotationMapping {
    private boolean useDefault;
    private String methodName;
    private String scope;
    private String model;

    private String packageName;
    private List<? extends TypeMirror> annotations;
    private List<? extends TypeMirror> imports;
    private TypeMirror superClass;
    private String className;

    public AnnotationMapping(Builder builder) {
        this.useDefault = builder.useDefault;
        this.methodName = builder.methodName;
        this.scope = builder.scope;
        this.model = builder.model;
        this.packageName = builder.packageName;;
        this.annotations = builder.annotations;;
        this.imports = builder.imports;
        this.superClass = builder.superClass;
        this.className = builder.className;
    }

    public static AnnotationMapping from(GeneratorConfig config) {
        return new Builder()
                .setMethodName(config.methodName())
                .setModel(config.model())
                .setScope(config.scope())
                .setUseDefault(config.useDefault())
                .build();
    }

    public static AnnotationMapping from(RequestTypeConfig config) {
        return new Builder()
                .setClassName(config.className())
                .setAnnotations(APUtils.getTypeMirrorFromAnnotationValue(config::annotations))
                .setImports(APUtils.getTypeMirrorFromAnnotationValue(config::imports))
                .setPackageName(config.packageName())
                .setSuperClass(APUtils.getTypeMirrorFromAnnotationValue(config::superClass).get(0))
                .build();
    }

    public static AnnotationMapping from(RepoTypeConfig config) {
        return new Builder()
                .setClassName(config.className())
                .setAnnotations(APUtils.getTypeMirrorFromAnnotationValue(config::annotations))
                .setImports(APUtils.getTypeMirrorFromAnnotationValue(config::imports))
                .setPackageName(config.packageName())
                .setSuperClass(APUtils.getTypeMirrorFromAnnotationValue(config::superClass).get(0))
                .build();
    }


    public static AnnotationMapping from(ServiceTypeConfig config) {
        return null;
    }

    public static class Builder{
        private boolean useDefault;
        private String methodName;
        private String scope;
        private String model;

        private String packageName;
        private List<? extends TypeMirror> annotations;
        private List<? extends TypeMirror> imports;
        private TypeMirror superClass;
        private String className;

        public Builder setUseDefault(boolean useDefault) {
            this.useDefault = useDefault;
            return this;
        }

        public Builder setMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder setScope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder setAnnotations(List<? extends TypeMirror> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder setImports(List<? extends TypeMirror> imports) {
            this.imports = imports;
            return this;
        }

        public Builder setSuperClass(TypeMirror superClass) {
            this.superClass = superClass;
            return this;
        }

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public AnnotationMapping build(){
            return new AnnotationMapping(this);
        }
    }

    public boolean isUseDefault() {
        return useDefault;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getScope() {
        return scope;
    }

    public String getModel() {
        return model;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<? extends TypeMirror> getAnnotations() {
        return annotations;
    }

    public List<? extends TypeMirror> getImports() {
        return imports;
    }

    public TypeMirror getSuperClass() {
        return superClass;
    }

    public String getClassName() {
        return className;
    }
}
