package com.demo.test.code.generator;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author youyu
 */
public class DaoFileGenerator implements FileGenerator{
    private String filePath;
    private File file;
    private String modelName;

    private boolean isInit;
    private List<String> content = Lists.newArrayList();

    public DaoFileGenerator(String filePath, String modelName) {
        this.filePath = filePath;
        this.file = new File(filePath+"/"+modelName+"Dao.java");
        this.modelName = modelName;
    }

    @Override
    public void generate(Method method) {
        if (!file.exists()){
            isInit = true;

            try {
                file.createNewFile();

                content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
                content.add("");
                content.add("import io.terminus.srm.contract.server.dao.BaseDao;");
                content.add("import org.springframework.stereotype.Repository;");
                content.add("");
                GenerateFacadeAndRequestResponse.addAuthor(content,GenerateManagerAndServiceAndDao.author);
                content.add("@Repository");
                content.add("public class "+ modelName+"Dao"+" extends BaseDao<"+modelName+",Long>"+" {");


            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
