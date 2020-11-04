package com.demo.test.code.generator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author youyu
 */
public class FacadeImplFileGenerator1 extends AbstractFileGenerator{
    private Class<?> facadeClazz;


    public FacadeImplFileGenerator1(String parentPath, List<String> importDeclaration, Class<?> facadeClazz) {
        super(parentPath, facadeClazz.getSimpleName(),importDeclaration);
        this.facadeClazz = facadeClazz;
    }

    @Override
    protected String getClassName() {
        return facadeName+"Impl";
    }

    @Override
    protected void doGenerateMethod(Method method) {
        content.add("");
        content.add("    @Override");
        content.add(getMethodSignature(method));
        content.add(getReturn(method));
        content.add("    }");
        content.add("");
    }

    @Override
    protected String getMethodSignature(Method method) {
        return "    public " + getSimpleGenericReturnTypeName(method) + " " + method.getName() + "(" + getParametersLine(method) + ") {";
    }

    @Override
    protected void init(Method method) {
        content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
        content.add("");
        content.addAll(importDeclaration);
        content.add("import "+facadeClazz.getName()+";");
        content.add("import org.springframework.beans.factory.annotation.Autowired;");
        content.add("");
        addAuthor(content,GenerateManagerAndServiceAndDao.author);

        content.add("@Service");
        content.add("public class "+ facadeClazz.getSimpleName()+"Impl implements "+ facadeClazz.getSimpleName()+" {");

        content.add("    @Autowired");
        content.add("    private "+getManagerClassName(facadeName)+" "+lowerCase(getManagerClassName(facadeName))+";");
        content.add("");
    }
}
