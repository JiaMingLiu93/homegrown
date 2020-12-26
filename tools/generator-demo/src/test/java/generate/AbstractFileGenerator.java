package generate;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author youyu
 */
public abstract class AbstractFileGenerator implements FileGenerator{

    /**
     * file path
     */
    protected String parentPath;

    /**
     * class content
     */
    protected List<String> content = Lists.newArrayList();

    /**
     * content of old file if exists
     */
    protected List<String> oldContent;

    /**
     * part of import declaration
     */
    protected List<String> importDeclaration;


    protected File file;

    protected String facadeName;

    public AbstractFileGenerator(String parentPath, String facadeName,List<String> importDeclaration){
        this.parentPath = parentPath;
        this.facadeName = facadeName;
        this.importDeclaration = importDeclaration;
    }

    public AbstractFileGenerator() {
    }

    protected String getFilePath(String parentPath){
        return parentPath+"/"+getClassName()+".java";
    }

    protected abstract String getClassName();

    @Override
    public void generate(Method method){
        if (file == null){
            initFile(method);
        }
        if (CollectionUtils.isEmpty(content)){
            initContent(method);
        }
        generateMethod(method);
    }

    protected void initFile(Method method) {
        //default init
        this.file = new File(getFilePath(parentPath));
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

    private void initContent(Method method) {
        if (!file.exists()){
            try {
                file.createNewFile();
                init(method);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                oldContent = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            oldContent.remove(oldContent.size()-1);
            content.addAll(oldContent);
        }
    }

    protected void generateMethod(Method method){
        if (isNotNewMethod(method)){
            return;
        }
        doGenerateMethod(method);
    }

    protected abstract void doGenerateMethod(Method method);

    protected boolean isNotNewMethod(Method method) {
        return content.stream().anyMatch(line -> line.contains(getMethodSignature(method)));
    }

    protected abstract String getMethodSignature(Method method);

    protected abstract void init(Method method);

    public String getReturn(Method method){
        if (method.getReturnType().toString().equals("void")){
            return "";
        }
        return "        return null;";
    }

    public String getSimpleGenericReturnTypeName(Method method){
        if (method.getReturnType().toString().equals("void")){
            return "void";
        }
        String typeName = method.getGenericReturnType().getTypeName();

        Map<String,String> map = new HashMap<>();

        Arrays.stream(typeName.split("<")).forEach(s -> {
            String replace = s.replace(">", "");
            String substring = replace.substring(replace.lastIndexOf(".") + 1);
            map.put(replace,substring);

        });

        String replace = typeName;
        for (Map.Entry<String,String> entry:map.entrySet()){
            replace = replace.replace(entry.getKey(), entry.getValue());
        }
        return replace;
    }

    public String getParametersLine(Method method){
        String parameters = Arrays.stream(method.getParameters()).map(parameter -> {
            Class<?> type = parameter.getType();
            String name = parameter.getName();
            if (name.equals("arg0")){
                name = "request";
            }
            return type.getSimpleName() + " " + name+", ";
        }).reduce("", (left, right) -> left + right);

        return parameters.substring(0,parameters.lastIndexOf(","));
    }

    public void addAuthor(List<String> lines,String author) {
        lines.add("/**");
        lines.add(" * @author "+author);
        lines.add(" */");
    }

    public String getManagerClassName(String facadeName){
        return getModelName(facadeName)+"Manager";
    }

    public String getModelName(String facadeName){
        if (facadeName.contains("Read")){
            return facadeName.substring(0,facadeName.indexOf("Read"));
        }else {
            return facadeName.substring(0,facadeName.indexOf("Write"));
        }
    }


    public String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public String lowerCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public String deleteResponse(String returnTypeName){
        Pattern compile = Pattern.compile(".*Response<(.*)>");
        Matcher matcher = compile.matcher(returnTypeName);
        if (matcher.matches()){
            return matcher.group(1);
        }
        return returnTypeName;
    }

    public String getReadServiceInterfaceName(String facadeName) {
        return getModelName(facadeName)+"ReadService";
    }

    public String getWriteServiceInterfaceName(String facadeName) {
        return getModelName(facadeName)+"WriteService";
    }

    public Boolean isWrite(Method method){
        return GenerateFacadeAndRequestResponse.biz.endsWith("Write");
//        try {
//            AbstractRequest request = (AbstractRequest) method.getParameters()[0].getType().newInstance();
//            return request.getOperationType().isWrite();
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        throw new RuntimeException("invalid request");
    }

    public String getDaoName(String facadeName){
        return getModelName(facadeName)+"Repo";
    }

    public String transformToModel(String name){
        if (!name.equals("void")){
            String model = deleteResponse(name).replace("Info", "");
//            if (!model.equals(getModelName(facadeName))){
//                throw new RuntimeException("wrong model");
//            }
            return model;
        }
        return name;
    }
}
