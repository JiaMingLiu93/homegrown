package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import com.homegrown.tools.code.generator.struct.processor.demo.model.Annotation;
import com.homegrown.tools.code.generator.struct.processor.demo.model.Field;
import com.homegrown.tools.code.generator.struct.processor.demo.model.GeneralSource;
import com.homegrown.tools.code.generator.struct.processor.demo.model.GeneratedMethod;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.TypeFactory;
import com.homegrown.tools.code.generator.struct.processor.demo.model.source.SourceMethod;
import com.homegrown.tools.code.generator.struct.processor.demo.utils.Strings;
import freemarker.template.utility.StringUtil;
import org.apache.commons.collections4.CollectionUtils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author youyu
 */
public class TypeCreatingElementProcessor implements ElementProcessor<List<SourceMethod>, GeneralSource>{
    @Override
    public GeneralSource process(ProcessorContext context, TypeElement typeElement, List<SourceMethod> sourceMethods) {
        TypeFactory typeFactory = context.getTypeFactory();

        TreeSet<Type> extraImportedTypes = getExtraImportedTypes(context, typeFactory);

        String superClassName = getSuperClassName(context);

        List<Annotation> annotations = getAnnotations(context, typeFactory);

        List<TypeElement> members = context.getMembers();
        List<Field> fields = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(members)){
            fields = members.stream().map(member -> {
                Type type = typeFactory.getType(member);
                return new Field(type, member.getSimpleName().toString());
            }).collect(Collectors.toList());
        }

        if (context.ignoreMethods()){
            return new GeneralSource.Builder()
                    .element(typeElement)
                    .name(context.getSimpleClassName())
                    .packageName(context.getPackageName())
                    .superClassName(superClassName)
                    .type(context.getType())
                    .extraImportedTypes(extraImportedTypes)
                    .annotations(annotations)
                    .fields(fields)
                    .build();
        }

        List<GeneratedMethod> generatedMethods = sourceMethods.stream().map(sourceMethod ->
                new GeneratedMethod.Builder().method(sourceMethod).build()).collect(Collectors.toList());

        return new GeneralSource.Builder()
                .element(typeElement)
                .name(context.getSimpleClassName())
                .packageName(context.getPackageName())
                .superClassName(superClassName)
                .type(context.getType())
                .extraImportedTypes(extraImportedTypes)
                .annotations(annotations)
                .fields(fields)
                .methods(generatedMethods)
                .build();
    }

    private List<Annotation> getAnnotations(ProcessorContext context, TypeFactory typeFactory) {
        List<Annotation> annotations = null;
        List<? extends TypeMirror> annotationTypeElements = context.getAnnotations();
        if (CollectionUtils.isNotEmpty(annotationTypeElements)){
            annotations = annotationTypeElements.stream().map(annotation->{
                Type type = typeFactory.getType(annotation);
                return new Annotation(type);
            }).collect(Collectors.toList());
        }
        return annotations;
    }

    private TreeSet<Type> getExtraImportedTypes(ProcessorContext context, TypeFactory typeFactory) {
        TreeSet<Type> extraImportedTypes = new TreeSet<>();
        //todo is it necessary?
        extraImportedTypes.add(context.getModelType());

        addSuperTypeForImport(context,extraImportedTypes);

        List<? extends TypeMirror> extraImports = context.getExtraImports();
        if (CollectionUtils.isNotEmpty(extraImports)) {
            extraImports.forEach(extraImport-> extraImportedTypes.add(typeFactory.getType(extraImport)));
        }
        return extraImportedTypes;
    }

    private void addSuperTypeForImport(ProcessorContext context, TreeSet<Type> extraImportedTypes){
        String superClass = context.getSuperClass();
        if (Strings.isNotEmpty(superClass)) {
            Type superClassType = context.getTypeFactory().getType(superClass);
            extraImportedTypes.add(superClassType);
        }

    }
    private String getSuperClassName(ProcessorContext context) {
        String superClassName = null;
        String superClass = context.getSuperClass();
        if (Strings.isNotEmpty(superClass)){

            Type superClassType = context.getTypeFactory().getType(superClass);
            TypeElement repoSuperClassTypeElement = superClassType.getTypeElement();

            List<? extends TypeParameterElement> typeParameters = repoSuperClassTypeElement.getTypeParameters();

            superClassName = context.getSuperClassName();

            if (typeParameters.size() == 1){
                //for mybatis dao
                superClassName +="<"+ context.getModelName()+">";
            }else if (typeParameters.size() == 2){
                //todo just for tr,maybe as a plugin
                superClassName +="<"+ context.getModelName()+",Long>";
            }

        }
        return superClassName;
    }

    @Override
    public int getPriority() {
        return 1000;
    }
}
