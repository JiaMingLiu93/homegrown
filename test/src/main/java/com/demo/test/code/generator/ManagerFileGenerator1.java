package com.demo.test.code.generator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author youyu
 */
public class ManagerFileGenerator1 extends AbstractFileGenerator{

    public ManagerFileGenerator1(String parentPath, String facadeName, List<String> importDeclaration) {
        super(parentPath, facadeName, importDeclaration);
    }

    @Override
    protected String getClassName() {
        return getManagerClassName(facadeName);
    }


    @Override
    protected void doGenerateMethod(Method method) {
        content.add(getMethodSignature(method));
        content.add(getReturn(method));
        content.add("    }");
        content.add("");
    }

    @Override
    protected String getMethodSignature(Method method) {
        return "    public " + deleteResponse(getSimpleGenericReturnTypeName(method)) + " " + method.getName() + "(" + getParametersLine(method) + ") {";
    }

    @Override
    protected void init(Method method) {
        content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
        content.add("");
        importDeclaration.remove("import io.terminus.common.model.Response;");
        content.addAll(importDeclaration);
        content.add("import lombok.extern.slf4j.Slf4j;");
        content.add("import org.springframework.beans.factory.annotation.Autowired;");
        content.add("import org.springframework.stereotype.Component;");
        content.add("");

        GenerateFacadeAndRequestResponse.addAuthor(content,GenerateManagerAndServiceAndDao.author);

        content.add("@Slf4j");
        content.add("@Component");
        content.add("public class "+ getClassName()+" {");

        content.add("    @Autowired");
        content.add("    private "+getReadServiceInterfaceName(facadeName)+" "+lowerCase(getReadServiceInterfaceName(facadeName))+";");
        content.add("");

        content.add("    @Autowired");
        content.add("    private "+getWriteServiceInterfaceName(facadeName)+" "+lowerCase(getWriteServiceInterfaceName(facadeName))+";");
        content.add("");
    }
}
