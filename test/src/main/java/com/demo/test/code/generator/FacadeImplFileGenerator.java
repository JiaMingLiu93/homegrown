package com.demo.test.code.generator;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.List;

import static com.demo.test.code.generator.GenerateManagerAndServiceAndDao.getParametersLine;
import static com.demo.test.code.generator.GenerateManagerAndServiceAndDao.getSimpleGenericReturnTypeName;

/**
 * @author youyu
 */
public class FacadeImplFileGenerator implements FileGenerator{
    private boolean isInit;

    private File file;
    private Class<?> facadeClazz;

    private String fileName;
    private String className;
    private String modelName;
    private List<String> importDeclare;

    private List<String> content;

    private List<String> lines = null;

    public FacadeImplFileGenerator() {
    }

    public FacadeImplFileGenerator(File file, Class<?> facadeClazz, List<String> importDeclare) {
        this.file = file;
        this.fileName = file.getName();
        this.facadeClazz = facadeClazz;
        this.className = facadeClazz.getSimpleName()+"Impl";
        this.importDeclare = importDeclare;
        this.content = Lists.newArrayList();
    }

    @Override
    public void generate(Method method){

        if (!file.exists()){
            isInit = true;

            try {
                file.createNewFile();

                content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
                content.add("");
                content.addAll(importDeclare);
                content.add("import "+facadeClazz.getName()+";");
                content.add("import org.springframework.beans.factory.annotation.Autowired;");
                content.add("");
                GenerateFacadeAndRequestResponse.addAuthor(content,GenerateManagerAndServiceAndDao.author);

                content.add("public class "+ facadeClazz.getSimpleName()+"Impl implements "+ facadeClazz.getSimpleName()+" {");

                content.add("    @Autowired");
                content.add("    private "+GenerateManagerAndServiceAndDao.getManagerClassName(facadeClazz.getSimpleName())+" "+GenerateFacadeAndRequestResponse.lowerCase(GenerateManagerAndServiceAndDao.getManagerClassName(facadeClazz.getSimpleName()))+";");
                content.add("");

                generateMethod(method);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (!isInit && CollectionUtils.isEmpty(lines)){
            try {
                lines = Files.readAllLines(file.toPath());
                lines.remove(lines.size()-1);
                content.addAll(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        generateMethod(method);
    }

    @Override
    public void flush() {
        if (!CollectionUtils.isEmpty(content)){
            content.add("}");
            try {
                GenerateFacadeAndRequestResponse.writeFile(file,content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateMethod(Method method) {
        content.add("    @Override");
        content.add("    public " + getSimpleGenericReturnTypeName(method)+ " " + method.getName() + "(" + getParametersLine(method) + ") {");
        if (!method.getReturnType().toString().equals("void")){
            content.add("        return null;");
        }
        content.add("    }");
        content.add("");
    }

    public Class<?> getFacadeClazz() {
        return facadeClazz;
    }

    public void setFacadeClazz(Class<?> facadeClazz) {
        this.facadeClazz = facadeClazz;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
