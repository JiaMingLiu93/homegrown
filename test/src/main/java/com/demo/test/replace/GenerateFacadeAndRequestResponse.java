package com.demo.test.replace;

import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author youyu
 */
public class GenerateFacadeAndRequestResponse {
    public static String author = "youyu";
    // model name
    public static String prefix = "SignTask";
    // Read or Write
    public static String biz = "Read";

    public static String methodName = "getSignTaskByBillCode";

    //if requestClassName is empty,use default value
    public static String requestClassName = "";

    //returnClassName should not be empty
    public static String returnClassName = "SignTaskInfo";

    public static String requestPath = "/Users/jam/company/code/contract-center/contract-api/src/main/java/io/terminus/srm/contract/api/sign/request";
    public static String responsePath = "/Users/jam/company/code/contract-center/contract-api/src/main/java/io/terminus/srm/contract/api/sign/response";
    public static String facadePath = "/Users/jam/company/code/contract-center/contract-api/src/main/java/io/terminus/srm/contract/api/sign/facade";

    public static void main(String[] args) {


        String facadeName = generateFacadeName(prefix,biz);

        //generate api,if facade interface does not exist,generate relative facade file with methodName under the
        //specified facade path
        generateApi(facadeName);

    }

    private static void generateApi(String facadeName) {
        //generate facade if not exists
        File facadeFile = generateFacadeIfNotExists(facadeName);
        //generate request class
        generateFacadeMethod(facadeFile);
    }

    private static void generateFacadeMethod(File facadeFile) {
        //generate request class
        generateApiRequestClass();
        //generate response if necessary
        generateApiResponseIfNecessary();
        //generate method to facade
        doGenerateFacadeMethod(facadeFile);
    }

    private static void generateApiResponseIfNecessary() {
        if (StringUtils.isEmpty(getReturnClassName())){
            return;
        }

        File classFile = new File(responsePath + "/" + getReturnClassName() + ".java");
        if (classFile.exists()){
            return;
        }

        try {
            classFile.createNewFile();
            doGenerateApiResponse(classFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doGenerateApiResponse(File classFile) throws IOException {
        List<String> codes = Lists.newArrayList();

        String packageDeclare = getPackageDeclare(classFile);

        codes.add(packageDeclare);
        codes.add("");

        codes.add("import io.terminus.api.response.info.ApiExtInfo;");
        codes.add("import lombok.Data;");
        codes.add("import lombok.EqualsAndHashCode;");

        codes.add("");

        addAuthor(codes);

        addLombok(codes);

        codes.add("public class "+getReturnClassName()+" extends "+"ApiExtInfo {");
        codes.add("}");

        writeFile(classFile,codes);
    }

    private static String generateApiRequestClass() {
        File classFile = new File(requestPath + "/" + getRequestClassName()+".java");
        if (classFile.exists()){
            throw new RuntimeException("already exist class file:"+classFile.getName());
        }
        try {
            classFile.createNewFile();
            return doGenerateApiRequestClass(classFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String doGenerateApiRequestClass(File classFile) throws IOException {
        List<String> codes = Lists.newArrayList();

        String packageDeclare = getPackageDeclare(classFile);

        codes.add(packageDeclare);
        codes.add("");
        codes.add("import io.swagger.annotations.ApiModelProperty;");
        codes.add("import io.terminus.srm.contract.api.AbstractRequestWithDefaultOperationType;");
        codes.add("import lombok.Data;");
        codes.add("import lombok.EqualsAndHashCode;");

        addAuthor(codes);

        addLombok(codes);

        codes.add("public class "+getRequestClassName()+" extends "+"AbstractRequestWithDefaultOperationType {");

        codes.add("    @Override");
        codes.add("    public boolean write() {");
        codes.add("        return "+ (Objects.equals("Write",biz)?"true":"false")+";");
        codes.add("    }");

        codes.add("}");

        writeFile(classFile,codes);

        return packageDeclare.replace("package","import")+"."+getRequestClassName();
    }

    private static void addLombok(List<String> codes) {
        codes.add("@Data");
        codes.add("@EqualsAndHashCode(callSuper = true)");
    }

    private static void doGenerateFacadeMethod(File facadeFile) {
        try {
            List<String> lines = Files.readAllLines(facadeFile.toPath());
            List<String> content = Lists.newArrayList();
            Integer index = null;
            boolean emptyFacade = false;

            //iterate lines for insert index
            for (int i=0;i<lines.size();i++){
                String line = lines.get(i);
                if (line.contains("interface") && line.contains("}")){
                    //post line of the index of '}'
                    index = i+1;
                    emptyFacade = true;

                    String subLine = line.substring(0, line.indexOf('}'));
                    content.add(subLine);
                }else if (line.contains("}")){
                    //pre line of the index of '}'
                    index = i;
                    content.add(line);
                }else {
                    content.add(line);
                }
            }

            if (index == null){
                throw new RuntimeException("code error: there is no }");
            }

            if (emptyFacade){
                content.add("}");
            }

            String api;
            if (StringUtils.isEmpty(returnClassName)){
                api = "    void ";
            }else {
                api = "    Response<" + getReturnClassName() + "> ";
            }

            api += methodName + "(" + getRequestClassName() + " request);";

            content.add(index,api);

            writeFile(facadeFile, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getReturnClassName() {
        return returnClassName;
    }

    public static void writeFile(File file, List<String> content) throws IOException {
        String lastLine = content.get(content.size() - 1);
        content.remove(content.size()-1);

        Files.write(file.toPath(), content);
        Files.write(file.toPath(),lastLine.getBytes("UTF-8"), StandardOpenOption.APPEND);
    }

    @NotNull
    private static String getRequestClassName() {
        if (StringUtils.isEmpty(requestClassName)){
            return prefix + upperCase(methodName) + "Request";
        }
        return requestClassName;
    }

    private static File generateFacadeIfNotExists(String facadeName) {
        File facadeFile = new File(facadePath+"/"+facadeName+".java");

        if (!facadeFile.exists()){
            return doGenerateFacadeInterfaceFile(facadeFile,facadeName);
        }
        return facadeFile;
    }

    private static File doGenerateFacadeInterfaceFile(File file,String facadeName) {
        try {
            file.createNewFile();

            String packagePath = getPackageDeclare(file);

            ArrayList<String> lines = Lists.newArrayList(packagePath);

            lines.add("");

            addAuthor(lines);

            lines.add("public interface "+facadeName+" {}");
            System.out.println(lines);

            Files.write(file.toPath(),lines, StandardCharsets.UTF_8);

            return file;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    private static void addAuthor(List<String> lines) {
        lines.add("/**");
        lines.add(" * @author "+author);
        lines.add(" */");
    }

    @NotNull
    private static String getPackageDeclare(File file) {
        String path = file.getParent();
        return "package "+path.replaceAll("/", ".").substring(path.indexOf("io"), path.length())+";";
    }

    private static String generateFacadeName(String prefix, String biz) {
        return prefix+biz+"Facade";
    }

    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
