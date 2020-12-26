package generate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import joor.Compile;
import joor.CompileOptions;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newBufferedReader;

/**
 * @author youyu
 */
public class GenerateFacadeAndRequestResponse {
    public static String author = "youyu";
    // model name
    public static String prefix = "SettleItem";
    // Read or Write
    public static String biz = "Read";

    public static String methodName = "findByIds";

    //if requestClassName is empty,use default value
    public static String requestClassName = "";

    //returnClassName should not be empty
    public static String returnClassName = "List<SettleItemInfo>";
    public static String infoClassName = "SettleItemInfo";

    public static Class<?> filterClass = null;
    public static String rootPath = System.getProperty("user.dir")+"/";

    public static String requestPath = rootPath + "settlement-api/src/main/java/io/terminus/settlement/center/entity/request/settle";

    public static String apiTargetPath = rootPath + "settlement-api/target/classes/io/terminus/settlement/center/entity/request/settle";
    public static String apiResponseTargetPath = rootPath + "settlement-api/target/classes/io/terminus/settlement/center/entity/reponse/settle";

    public static String responsePath = rootPath + "settlement-api/src/main/java/io/terminus/settlement/center/entity/reponse/settle";

    public static String facadePath = rootPath + "settlement-api/src/main/java/io/terminus/settlement/center/facade/settle";
    public static String implPath = rootPath + "settlement-server/src/main/java/io/terminus/settlement/center/facade/settle";
    public static String managerPath = rootPath + "settlement-server/src/main/java/io/terminus/settlement/center/manager/settle";
    public static String serviceApiPath = rootPath + "settlement-server/src/main/java/io/terminus/settlement/center/service/settle";
    public static String repoPath = rootPath + "settlement-server/src/main/java/io/terminus/settlement/center/repository/settle";

    private static File requestClassFile;
    private static File responseClassFile;


    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
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
        String importDeclarationOfRequest = generateApiRequestClass();
        //generate response if necessary
        String importDeclarationOfReturn = generateApiResponseIfNecessary();
        //generate method to facade
        doGenerateFacadeMethod(facadeFile,importDeclarationOfRequest,importDeclarationOfReturn);
        //generate facadeImpl,manager,service,repo
        generatePostFiles(facadeFile,importDeclarationOfRequest,importDeclarationOfReturn);

    }

    private static void generatePostFiles(File file, String importDeclarationOfRequest, String importDeclarationOfReturn) {

        Map<Class<?>,File> class2file = Maps.newHashMap();
        if (file.getName().endsWith("Facade.java")){
            try {
                List<String> lines = Files.readAllLines(file.toPath());

                String content = readAllLines(file.toPath());

                String packageLine = lines.stream().filter(line -> line.contains("package")).findFirst().get();
                String packageDeclare = packageLine.split(" ")[1].replace(";", "");
                String className = packageDeclare +"."+ file.getName().replace(".java", "");


                compileAndMoveToClassPath(importDeclarationOfRequest,requestClassFile,apiTargetPath);
                compileAndMoveToClassPath(importDeclarationOfReturn,responseClassFile,apiResponseTargetPath);

                Class<?> compile = Compile.doCompile(className, content, new CompileOptions(),true,null);

                class2file.put(compile,file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GenerateManagerAndServiceAndDao.generateCode(class2file,methodName);
    }

    private static void compileAndMoveToClassPath(String importDeclarationOfRequest, File classFile, String apiTargetPath) throws IOException {
        String content = readAllLines(classFile.toPath());
        String className = importDeclarationOfRequest.replace("import ", "").replace(";", "");
        File compiledFile = new File(apiTargetPath +"/"+ classFile.getName().replace(".java", ".class"));
        if (compiledFile.exists()){
            return;
        }
        compiledFile.createNewFile();

        Compile.doCompile(className, content, new CompileOptions(), false,compiledFile);
    }

    private static String generateApiResponseIfNecessary() {
        if (StringUtils.isEmpty(getInfoClassName())){
            return null;
        }
        responseClassFile = new File(responsePath + "/" + getInfoClassName() + ".java");

        if (responseClassFile.exists()){
            return getPackageDeclare(responseClassFile).replace("package","import").replace(";","")+"."+getInfoClassName()+";";
        }

        try {
            responseClassFile.createNewFile();
            return doGenerateApiResponse(responseClassFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String doGenerateApiResponse(File classFile) throws IOException {
        List<String> codes = Lists.newArrayList();

        String packageDeclare = getPackageDeclare(classFile);

        codes.add(packageDeclare);
        codes.add("");

        codes.add("import io.terminus.api.response.info.ApiInfo;");
        codes.add("import lombok.Data;");
        codes.add("import lombok.EqualsAndHashCode;");

        codes.add("");

        addAuthor(codes,author);

        addLombok(codes);

        codes.add("public class "+getInfoClassName()+" extends "+"ApiInfo {");
        codes.add("}");

        writeFile(classFile,codes);

        return packageDeclare.replace("package","import").replace(";","")+"."+getInfoClassName()+";";
    }

    private static String generateApiRequestClass() {
        File classFile = new File(requestPath + "/" + getRequestClassName()+".java");
        requestClassFile = classFile;
        if (classFile.exists()){
//            throw new RuntimeException("already exist class file:"+classFile.getName());
            return getPackageDeclare(classFile).replace("package","import").replace(";","")+"."+getRequestClassName()+";";
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
        codes.add("import io.terminus.api.consts.OperationType;");
        if (isPage()){
            codes.add("import io.terminus.api.request.AbstractPageRequest;");
        }else {
            codes.add("import io.terminus.api.request.AbstractRequest;");
        }
        codes.add("import lombok.Data;");
        codes.add("import lombok.EqualsAndHashCode;");

        addAuthor(codes,author);

        addLombok(codes);

        if (isPage()){
            codes.add("public class "+getRequestClassName()+" extends "+"AbstractPageRequest {");
        }else {
            codes.add("public class "+getRequestClassName()+" extends "+"AbstractRequest {");
        }

        codes.add("    @Override");
        codes.add("    public OperationType getOperationType() {");
        codes.add("        return "+ "null;");
        codes.add("    }");

        codes.add("}");

        writeFile(classFile,codes);

        return packageDeclare.replace("package","import").replace(";","")+"."+getRequestClassName()+";";
    }

    private static boolean isPage() {
        return methodName.contains("page");
    }

    private static void addLombok(List<String> codes) {
        codes.add("@Data");
        codes.add("@EqualsAndHashCode(callSuper = true)");
    }

    private static void doGenerateFacadeMethod(File facadeFile, String importDeclarationOfRequest, String importDeclarationOfReturn) {
        try {
            List<String> lines = Files.readAllLines(facadeFile.toPath());
            List<String> content = Lists.newArrayList();
            Integer index = null;
            Integer importInsertIndex = null;
            boolean emptyFacade = false;

            //iterate lines for insert index
            for (int i=0;i<lines.size();i++){
                String line = lines.get(i);
                if (line.contains("import")){
                    importInsertIndex = i;
                }
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

            if (importInsertIndex == null){
                importInsertIndex = 2;
                content.add(importInsertIndex,"import io.terminus.common.model.Response;");
                index+=1;
                if (isPage()){
                    content.add(importInsertIndex,"import io.terminus.common.model.Paging;");
                    index+=1;
                }
            }


            content.add(importInsertIndex,importDeclarationOfRequest);
            index+=1;
            if (!StringUtils.isEmpty(importDeclarationOfReturn) && !content.contains(importDeclarationOfReturn)){
                content.add(importInsertIndex,importDeclarationOfReturn);
                index+=1;
            }

//            if (index == null){
//                throw new RuntimeException("code error: there is no }");
//            }

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

    private static String getInfoClassName(){
        return infoClassName;
    }

    public static void writeFile(File file, List<String> content) throws IOException {
        String lastLine = content.get(content.size() - 1);
        content.remove(content.size()-1);

        Files.write(file.toPath(), content);
        Files.write(file.toPath(),lastLine.getBytes("UTF-8"), StandardOpenOption.APPEND,StandardOpenOption.SYNC);
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

            addAuthor(lines,author);

            lines.add("public interface "+facadeName+" {}");
            System.out.println(lines);

        Files.write(file.toPath(),lines, StandardCharsets.UTF_8);

        return file;

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static void addAuthor(List<String> lines,String author) {
        lines.add("/**");
        lines.add(" * @author "+author);
        lines.add(" */");
    }

    @NotNull
    public static String getPackageDeclare(File file) {
        String path = file.getParent();
        return "package "+path.replaceAll("/", ".").substring(path.indexOf("io"), path.length())+";";
    }

    private static String generateFacadeName(String prefix, String biz) {
        return prefix+biz+"Facade";
    }

    private static String generateFacadeName() {
        return prefix+biz+"Facade";
    }

    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static String lowerCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public static String readAllLines(Path path) throws IOException {

        try (BufferedReader reader = newBufferedReader(path, StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder();
            for (;;) {
                String line = reader.readLine();
                if (line == null)
                    break;
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
