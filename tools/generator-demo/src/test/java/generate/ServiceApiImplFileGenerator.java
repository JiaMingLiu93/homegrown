package generate;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author youyu
 */
public class ServiceApiImplFileGenerator extends AbstractFileGenerator{

    public ServiceApiImplFileGenerator(String parentPath, String facadeName, List<String> importDeclaration) {
        super(parentPath, facadeName,importDeclaration);
    }

    @Override
    protected String getClassName() {
        return null;
    }

    @Override
    protected void doGenerateMethod(Method method) {
        content.add("    @Override");
        content.add(getMethodSignature(method));
        content.add(getReturn(method));
        content.add("    }");
        content.add("");
    }

    @Override
    protected String getMethodSignature(Method method) {
        return "    public " + transformToModel(getSimpleGenericReturnTypeName(method))+ " " + method.getName() + "(" + getParametersLine(method) + ") {";
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

        addAuthor(content,GenerateManagerAndServiceAndDao.author);

        content.add("@Slf4j");
        content.add("@Component");

        if (isWrite(method)){

            content.add("public class "+ getWriteServiceInterfaceName(facadeName)+"Impl implements "+ getWriteServiceInterfaceName(facadeName)+" {");

        }else {

            content.add("public class "+ getReadServiceInterfaceName(facadeName)+"Impl implements "+ getReadServiceInterfaceName(facadeName)+" {");
        }

        content.add("    @Autowired");
        content.add("    private "+getDaoName(facadeName)+" "+lowerCase(getDaoName(facadeName))+";");
        content.add("");
    }

    @Override
    protected void initFile(Method method) {
        if (isWrite(method)){
            file = new File(parentPath+"/"+getWriteServiceInterfaceName(facadeName)+"Impl.java");
        }else {
            file = new File(parentPath+"/"+getReadServiceInterfaceName(facadeName)+"Impl.java");
        }
    }
}
