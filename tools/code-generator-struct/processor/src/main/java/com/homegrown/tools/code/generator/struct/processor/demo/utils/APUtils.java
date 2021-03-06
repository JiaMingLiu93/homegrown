package com.homegrown.tools.code.generator.struct.processor.demo.utils;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * @author youyu
 */
public class APUtils {
    @FunctionalInterface
    public interface GetClassValue {
        void execute() throws MirroredTypeException, MirroredTypesException;
    }
    public static List<? extends TypeMirror> getTypeMirrorFromAnnotationValue(GetClassValue c) {
        try {
            c.execute();
        }
        catch(MirroredTypesException ex) {
            return ex.getTypeMirrors();
        }
        return null;
    }
}
