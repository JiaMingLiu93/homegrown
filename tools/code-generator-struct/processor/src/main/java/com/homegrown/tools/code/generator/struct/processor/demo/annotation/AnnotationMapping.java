package com.homegrown.tools.code.generator.struct.processor.demo.annotation;

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
    private String[] annotations;
    private String[] imports;
    private String superClassName;
    private String className;

    public AnnotationMapping(Builder builder) {
        this.useDefault = builder.useDefault;
        this.methodName = builder.methodName;
        this.scope = builder.scope;
        this.model = builder.model;
        this.packageName = builder.packageName;;
        this.annotations = builder.annotations;;
        this.imports = builder.imports;
        this.superClassName = builder.superClassName;
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
                .setAnnotations(config.annotations())
                .setImports(config.imports())
                .setPackageName(config.packageName())
                .setSuperClassName(config.superClassName())
                .build();
    }

    public static AnnotationMapping from(RepoTypeConfig config) {
        return new Builder()
                .setClassName(config.className())
                .setAnnotations(config.annotations())
                .setImports(config.imports())
                .setPackageName(config.packageName())
                .setSuperClassName(config.superClassName())
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
        private String[] annotations;
        private String[] imports;
        private String superClassName;
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

        public Builder setAnnotations(String[] annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder setImports(String[] imports) {
            this.imports = imports;
            return this;
        }

        public Builder setSuperClassName(String superClassName) {
            this.superClassName = superClassName;
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

    public String[] getAnnotations() {
        return annotations;
    }

    public String[] getImports() {
        return imports;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String getClassName() {
        return className;
    }
}
