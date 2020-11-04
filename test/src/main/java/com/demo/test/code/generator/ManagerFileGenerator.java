package com.demo.test.code.generator;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.demo.test.code.generator.GenerateManagerAndServiceAndDao.*;

/**
 * @author youyu
 */
@Data
public class ManagerFileGenerator implements FileGenerator{
    private boolean isInit;
    private boolean isNewMethod;

    private File file;
    private Class<?> facadeClazz;

    private List<String> importDeclare;


    private List<String> content = Lists.newArrayList();

    public ManagerFileGenerator(File file, Class<?> facadeClazz,List<String> importDeclare) {
        this.file = file;
        this.facadeClazz = facadeClazz;
        this.importDeclare = importDeclare;
    }

    @Override
    public void generate(Method method) {
        if (!file.exists()){
            isInit = true;
            try {
                file.createNewFile();

                content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
                content.add("");
                importDeclare.remove("import io.terminus.common.model.Response;");
                content.addAll(importDeclare);
                content.add("import lombok.extern.slf4j.Slf4j;");
                content.add("import org.springframework.beans.factory.annotation.Autowired;");
                content.add("import org.springframework.stereotype.Component;");
                content.add("");

                GenerateFacadeAndRequestResponse.addAuthor(content,GenerateManagerAndServiceAndDao.author);

                content.add("@Slf4j");
                content.add("@Component");
                content.add("public class "+ GenerateManagerAndServiceAndDao.getManagerClassName(facadeClazz.getSimpleName())+" {");

                content.add("    @Autowired");
                content.add("    private "+GenerateManagerAndServiceAndDao.getReadServiceInterfaceName(facadeClazz.getSimpleName())+" "+GenerateFacadeAndRequestResponse.lowerCase(GenerateManagerAndServiceAndDao.getReadServiceInterfaceName(facadeClazz.getSimpleName()))+";");
                content.add("");

                content.add("    @Autowired");
                content.add("    private "+GenerateManagerAndServiceAndDao.getWriteServiceInterfaceName(facadeClazz.getSimpleName())+" "+GenerateFacadeAndRequestResponse.lowerCase(GenerateManagerAndServiceAndDao.getWriteServiceInterfaceName(facadeClazz.getSimpleName()))+";");
                content.add("");

                generateMethod(method);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isInit){
            generateMethod(method);
            return;
        }

    }

    @Override
    public void flush() {
        if (isInit){
            content.add("}");
            try {
                GenerateFacadeAndRequestResponse.writeFile(file,content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateMethod(Method method) {
        content.add("    public " + deleteResponse(getSimpleGenericReturnTypeName(method))+ " " + method.getName() + "(" + getParametersLine(method) + ") {");
        if (!method.getReturnType().toString().equals("void")){
            content.add("        return null;");
        }
        content.add("    }");
        content.add("");
    }

    private void generateNewMethod(Method method,List<String> lines) {
        isNewMethod = true;
        content = lines;
        generateMethod(method);
    }
}
