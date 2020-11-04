package com.demo.test.code.generator;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author youyu
 */
public class ServiceApiFileGenerator extends AbstractFileGenerator{
    public ServiceApiFileGenerator(String parentPath, String facadeName, List<String> importDeclaration) {
        super(parentPath, facadeName,importDeclaration);
    }

    @Override
    protected String getClassName() {
        return null;
    }

    @Override
    protected void doGenerateMethod(Method method) {
        content.add(getMethodSignature(method));
        content.add("");
    }

    @Override
    protected String getMethodSignature(Method method) {
        return "    " + transformToModel(getSimpleGenericReturnTypeName(method))+ " " + method.getName() + "(" + getParametersLine(method) + ");";
    }

    @Override
    protected void init(Method method) {
        content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
        content.add("");
        importDeclaration.remove("import io.terminus.common.model.Response;");
        content.addAll(importDeclaration);

        content.add("");

        addAuthor(content,GenerateManagerAndServiceAndDao.author);

        if (isWrite(method)){
            content.add("public interface "+ getWriteServiceInterfaceName(facadeName)+" {");

        }else {
            content.add("public interface "+ getReadServiceInterfaceName(facadeName)+" {");
        }
    }

    @Override
    protected void initFile(Method method) {
        if (isWrite(method)){
            file = new File(parentPath+"/"+getWriteServiceInterfaceName(facadeName)+".java");
        }else {
            file = new File(parentPath+"/"+getReadServiceInterfaceName(facadeName)+".java");
        }
    }
}
