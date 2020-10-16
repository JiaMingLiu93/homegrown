package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.AnnotationMapping;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.Type;
import com.homegrown.tools.code.generator.struct.processor.demo.model.common.TypeFactory;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author youyu
 */
public interface ElementProcessor<P, R> {

    interface ProcessorContext {

        Filer getFiler();

        Types getTypeUtils();

        Elements getElementUtils();

        TypeFactory getTypeFactory();

        boolean ignoreMethods();

        String getSpecifiedMethod();

        /**
         * To get the simple name class that is generate.
         * @return class name
         */
        String getSimpleClassName();

        /**
         * Whether the currently processed type is erroneous which is the
         * case if at least one diagnostic with {@link Diagnostic.Kind#ERROR} is reported
         * by any of the participating processors.
         *
         * @return {@code true} if the currently processed mapper type is
         *         erroneous, {@code false} otherwise.
         */
        boolean isErroneous();

        TypeElement getFacadeTypeElement();

        String getPackageName();

        String getPath();

        String getSuperClassName();

        TypeMirror getSuperClass();

        List<TypeElement> getMembers();

        String getType();

        Map<String,TypeElement> getExistedTypeElements();

        TypeElement getSuperClassTypeElement();

        String getModelName();

        Type getModelType();

        Map<Class<?>, AnnotationMapping> getConfigs();

        /**
         * To get root elements of current round
         * @return elements
         */
        Set<? extends Element> getRootElements();

        List<? extends TypeMirror> getAnnotations();

        List<? extends TypeMirror> getExtraImports();
    }

    /**
     * Processes the given source element, representing a Java bean in
     * one form or another.
     *
     * @param context Context providing common infrastructure objects.
     * @param typeElement The original type element from which the given mapper object
     * is derived.
     * @param sourceModel The current representation of the bean mapper. Never
     * {@code null} (the very first processor receives the original
     * type element).
     *
     * @return The resulting representation of the bean mapper; may be the same
     *         as the source representation, e.g. if a given implementation just
     *         performs some sort of validity check. Implementations must never
     *         return {@code null} except for the very last processor which
     *         generates the resulting Java source file.
     */
    R process(ProcessorContext context, TypeElement typeElement, P sourceModel);

    /**
     * Returns the priority value of this processor which must be between 1
     * (highest priority) and 10000 (lowest priority). Processors are invoked in
     * order from highest to lowest priority, starting with the mapping method
     * retrieval processor (priority 1) and finishing with the code generation
     * processor (priority 10000). Processors working on the built
     * {@code Mapper} model must have a priority &gt; 1000.
     *
     * @return The priority value of this processor.
     */
    int getPriority();
}
