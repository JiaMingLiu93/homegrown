package com.demo.test.code.generator;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.List;

import static com.demo.test.code.generator.GenerateManagerAndServiceAndDao.*;

/**
 * @author youyu
 */

public class ServiceFileGenerator implements FileGenerator{

    private boolean isInit;
    private File file;
    private Class<?> facadeClazz;

    private List<String> importDeclare;

    private List<String> content = Lists.newArrayList();
    private List<String> contentImpl = Lists.newArrayList();
    private File implFile;

    private String servicePath;

    private List<String> lines = null;


    public ServiceFileGenerator(String servicePath, Class<?> facadeClazz, List<String> importDeclare) {
        this.servicePath = servicePath;
        this.facadeClazz = facadeClazz;
        this.importDeclare = importDeclare;
    }

    @Override
    public void generate(Method method) {
        if (file == null){
            if (GenerateManagerAndServiceAndDao.isWrite(method)){
                file = new File(servicePath+"/"+GenerateManagerAndServiceAndDao.getWriteServiceInterfaceName(facadeClazz.getSimpleName())+".java");
            }else {
                file = new File(servicePath+"/"+GenerateManagerAndServiceAndDao.getReadServiceInterfaceName(facadeClazz.getSimpleName())+".java");
            }
            String name = file.getName();
            implFile = new File(file.getParent()+"/impl/"+name.replace(".java","")+"Impl.java");
        }

        if (!file.exists()){
            isInit = true;
            try {
                file.createNewFile();
                implFile.createNewFile();

                content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
                contentImpl.add(GenerateFacadeAndRequestResponse.getPackageDeclare(implFile));
                content.add("");
                contentImpl.add("");
                importDeclare.remove("import io.terminus.common.model.Response;");
                content.addAll(importDeclare);
                contentImpl.addAll(importDeclare);
                contentImpl.add("import lombok.extern.slf4j.Slf4j;");
                contentImpl.add("import org.springframework.beans.factory.annotation.Autowired;");
                contentImpl.add("import org.springframework.stereotype.Component;");

                content.add("");
                contentImpl.add("");

                GenerateFacadeAndRequestResponse.addAuthor(content,GenerateManagerAndServiceAndDao.author);
                GenerateFacadeAndRequestResponse.addAuthor(contentImpl,GenerateManagerAndServiceAndDao.author);

                contentImpl.add("@Slf4j");
                contentImpl.add("@Component");

                if (GenerateManagerAndServiceAndDao.isWrite(method)){
                    content.add("public interface "+ GenerateManagerAndServiceAndDao.getWriteServiceInterfaceName(facadeClazz.getSimpleName())+" {");
                    contentImpl.add("public class "+ GenerateManagerAndServiceAndDao.getWriteServiceInterfaceName(facadeClazz.getSimpleName())+"Impl implements "+ GenerateManagerAndServiceAndDao.getWriteServiceInterfaceName(facadeClazz.getSimpleName())+" {");

                }else {
                    content.add("public interface "+ GenerateManagerAndServiceAndDao.getReadServiceInterfaceName(facadeClazz.getSimpleName())+" {");
                    contentImpl.add("public class "+ GenerateManagerAndServiceAndDao.getReadServiceInterfaceName(facadeClazz.getSimpleName())+"Impl implements "+ GenerateManagerAndServiceAndDao.getReadServiceInterfaceName(facadeClazz.getSimpleName())+" {");
                }

                contentImpl.add("    @Autowired");
                contentImpl.add("    private "+GenerateManagerAndServiceAndDao.getDaoName(facadeClazz.getSimpleName())+" "+GenerateFacadeAndRequestResponse.lowerCase(GenerateManagerAndServiceAndDao.getDaoName(facadeClazz.getSimpleName()))+";");
                contentImpl.add("");

                generateMethod(method);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                lines = Files.readAllLines(file.toPath());
                lines.remove(lines.size()-1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (isInit){
            generateMethod(method);
        }
    }

    @Override
    public void flush() {
        if (isInit){
            content.add("}");
            contentImpl.add("}");
            try {
                GenerateFacadeAndRequestResponse.writeFile(file,content);
                GenerateFacadeAndRequestResponse.writeFile(implFile,contentImpl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateMethod(Method method) {
        content.add("    public " + transformToModel(getSimpleGenericReturnTypeName(method))+ " " + method.getName() + "(" + getParametersLine(method) + ");");
        contentImpl.add("    @Override");
        contentImpl.add("    public " + transformToModel(getSimpleGenericReturnTypeName(method))+ " " + method.getName() + "(" + getParametersLine(method) + ") {");
        if (!method.getReturnType().toString().equals("void")){
            contentImpl.add("        return null;");
        }
        contentImpl.add("    }");

        content.add("");
        contentImpl.add("");
    }

    private String transformToModel(String name){
        if (!name.equals("void")){
            String model = deleteResponse(name).replace("Info", "");
            if (!model.equals(getModelName(facadeClazz.getSimpleName()))){
                throw new RuntimeException("wrong model");
            }
            return model;
        }
        return name;
    }
}
