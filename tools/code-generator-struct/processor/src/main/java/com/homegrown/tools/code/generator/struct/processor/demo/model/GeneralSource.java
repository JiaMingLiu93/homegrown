package com.homegrown.tools.code.generator.struct.processor.demo.model;

import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;

import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.SortedSet;

/**
 * @author youyu
 */
public class GeneralSource extends GeneratedType{
    static final String CLASS_NAME_PLACEHOLDER = "<CLASS_NAME>";
    static final String PACKAGE_NAME_PLACEHOLDER = "<PACKAGE_NAME>";

    public GeneralSource(String packageName, String name, String superClassName, String interfacePackage,
                         String interfaceName, List<GeneratedMethod> methods, SortedSet<Type> extraImportedTypes,
                         List<Annotation> annotations, String type, List<Field> fields) {
        super(packageName, name, superClassName, interfacePackage, interfaceName, methods,
                annotations, extraImportedTypes, type, fields);
    }

    public static class Builder extends GeneratedTypeBuilder<Builder> {

        private TypeElement element;

        private boolean customName;
        private String packageName;
        private boolean customPackage;
        private String name;
        private String superClassName;
        private List<Field> fields;
        private String interfaceName;
        private String interfacePackage;
        private List<Annotation> annotations;
        //extra import from config
        private SortedSet<Type> extraImportedTypes;
        private String type;


        public Builder() {
            super( Builder.class );
        }

        public Builder element(TypeElement element) {
            this.element = element;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
//            this.customPackage = !DEFAULT_IMPLEMENTATION_PACKAGE.equals( this.implPackage );
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder superClassName(String superClassName) {
            this.superClassName = superClassName;
            return this;
        }

        public Builder fields(List<Field> fields){
            this.fields = fields;
            return this;
        }

        public Builder interfaceName(String interfaceName){
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder interfacePackage(String interfacePackage){
            this.interfacePackage = interfacePackage;
            return this;
        }

        public Builder annotations(List<Annotation> annotations){
            this.annotations = annotations;
            return this;
        }

        public Builder extraImportedTypes(SortedSet<Type> extraImportedTypes){
            this.extraImportedTypes = extraImportedTypes;
            return this;
        }

        public Builder type(String type){
            this.type = type;
            return this;
        }

        public GeneralSource build() {

            return new GeneralSource(packageName,name,superClassName,interfacePackage,interfaceName,methods,
                    extraImportedTypes,annotations,type,fields);
        }

    }

    @Override
    protected String getTemplateName() {
        return super.getTemplateNameForClass(GeneratedType.class);
    }
}
