package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author youyu
 */
public class TypeCheckElementProcessor implements ElementProcessor<Void, Boolean>{
    @Override
    public Boolean process(ProcessorContext context, TypeElement typeElement, Void sourceModel) {
        //check class exists
        AtomicReference<String> qualifiedClassName = new AtomicReference<>();

        boolean isValid = context.getRootElements().stream().
                //1. compare class name, such as 'Test' that is the name of Test.class
                        filter(element -> element.getSimpleName().contentEquals(context.getSimpleClassName()))
                //2. compare package name, such as 'com.demo' that is the package of Test.class
                .noneMatch(element -> {
                    PackageElement packageElement = context.getElementUtils().getPackageOf(element);
                    boolean match = packageElement.getQualifiedName().contentEquals(context.getPackageName());
                    if (match){
                        qualifiedClassName.set(packageElement.getQualifiedName().toString() + "." + element.getSimpleName().toString());
                    }
                    return match;
                });
        if (!isValid){
            System.out.println("existed class "+qualifiedClassName.get());
        }
        return isValid;

    }

    @Override
    public int getPriority() {
        return 1;
    }
}
