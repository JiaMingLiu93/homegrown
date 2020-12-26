package generate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
import java.util.stream.Collectors;

/**
 * Base on facade interface,the code will generate facadeImpl,manager,service and repo
 * @author youyu
 */
public class GenerateManagerAndServiceAndDao {
    public static String author = "youyu";

    private static String facadePath = GenerateFacadeAndRequestResponse.facadePath;
    private static String implPath = GenerateFacadeAndRequestResponse.implPath;
    private static String managerPath = GenerateFacadeAndRequestResponse.managerPath;
    private static String serviceApiPath = GenerateFacadeAndRequestResponse.serviceApiPath;
    private static String repoPath = GenerateFacadeAndRequestResponse.repoPath;
    private static String methodName= GenerateFacadeAndRequestResponse.methodName;


    //don't init
    private static String managerClassName;

    private static List<FileGenerator> generators = Lists.newArrayList();

    public static void main(String[] args) throws ClassNotFoundException {

//        File file = new File(facadePath + "/CompanySealWriteFacade.java");
//        System.out.println(file.getName());
//
//        Class<?> clazz = Class.forName("CompanySealWriteFacade");
//
//        Arrays.stream(clazz.getMethods()).forEach(method -> {
//            System.out.println(method.getReturnType());
//            Class<?> requestType = method.getParameters()[0].getType();
//            try {
//                AbstractRequest request = (AbstractRequest) requestType.newInstance();
//                System.out.println(request.getOperationType().isWrite());
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//
//            String typeName = method.getGenericReturnType().getTypeName();
//            System.out.println(typeName);
//            Map<String,String> map = new HashMap<>();
//
//            Arrays.stream(typeName.split("<")).forEach(s -> {
//                String replace = s.replace(">", "");
//                String substring = replace.substring(replace.lastIndexOf(".") + 1);
//                map.put(replace,substring);
//                System.out.println(substring);
//            });
//
//            String replace = typeName;
//            for (Map.Entry<String,String> entry:map.entrySet()){
//                replace = replace.replace(entry.getKey(), entry.getValue());
//            }
//            System.out.println(replace);
//        });


        generateCode();
    }

    public static void generateCode(){
        generateCode(initFacadeClazz(),methodName);
    }

    public static String getSimpleGenericReturnTypeName(Method method){
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

    public static void generateCode(Map<Class<?>,File> class2file,String methodName) {
        class2file.entrySet().forEach(entry->{
            File file = entry.getValue();
            Class<?> clazz = entry.getKey();

            //generate facade impl
            try {
                List<String> lines = Files.readAllLines(file.toPath());
                List<String> importDeclare = lines.stream().filter(line -> line.contains("import")).collect(Collectors.toList());

//                String implFilePath = implPath+"/"+clazz.getSimpleName()+"Impl.java";
//                File implFile = new File(implFilePath);
//                FacadeImplFileGenerator facadeImpl = new FacadeImplFileGenerator(implFile,clazz,importDeclare);

                generators.add(new FacadeImplFileGenerator1(implPath,importDeclare,clazz));

//                String managerFilePath = managerPath+"/"+getManagerClassName(clazz.getSimpleName())+".java";
//                File managerFile = new File(managerFilePath);
//                ManagerFileGenerator managerFileGenerator = new ManagerFileGenerator(managerFile, clazz, importDeclare);
                generators.add(new ManagerFileGenerator1(managerPath,clazz.getSimpleName(),importDeclare));

//                ServiceFileGenerator serviceFileGenerator = new ServiceFileGenerator(serviceApiPath, clazz, importDeclare);
                generators.add(new ServiceApiFileGenerator(serviceApiPath,clazz.getSimpleName(),importDeclare));
                generators.add(new ServiceApiImplFileGenerator(serviceApiPath+"/impl",clazz.getSimpleName(),importDeclare));

//                DaoFileGenerator daoFileGenerator = new DaoFileGenerator(repoPath, getModelName(clazz.getSimpleName()));
                generators.add(new DaoFileGenerator1(repoPath,clazz.getSimpleName(),importDeclare));

                Arrays.asList(clazz.getMethods()).stream().filter(method -> {
                    if (methodName != null){
                        return method.getName().equals(methodName);
                    }
                    return true;
                }).forEach(method -> {
                    generators.forEach(generator->generator.generate(method));
                });

                generators.forEach(FileGenerator::flush);
                generators.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static String getParametersLine(Method method){
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

    public static String getManagerClassName(String facadeName){
        return getModelName(facadeName)+"Manager";
    }

    public static String getModelName(String facadeName){
        if (facadeName.contains("Read")){
            return facadeName.substring(0,facadeName.indexOf("Read"));
        }else {
            return facadeName.substring(0,facadeName.indexOf("Write"));
        }
    }

    private static Map<Class<?>,File> initFacadeClazz() {

        Map<Class<?>,File> class2file = Maps.newHashMap();
        File facadeDir = new File(facadePath);
        Arrays.stream(facadeDir.listFiles()).forEach(file -> {
            if (file.getName().endsWith("Facade.java")){
                try {
                    List<String> lines = Files.readAllLines(file.toPath());
                    String packageLine = lines.stream().filter(line -> line.contains("package")).findFirst().get();
                    String packageDeclare = packageLine.split(" ")[1].replace(";", "");
                    String className = packageDeclare +"."+ file.getName().replace(".java", "");
                    class2file.put(Class.forName(className),file);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<Class<?>,File> tmp = Maps.newHashMap();
        class2file.forEach((key, value) -> {
            if (key == GenerateFacadeAndRequestResponse.filterClass) {
                tmp.put(key, value);
            }
        });
//        return class2file;
        return tmp;
    }

    public static String getReadServiceInterfaceName(String facadeName) {
        return getModelName(facadeName)+"ReadService";
    }

    public static String getWriteServiceInterfaceName(String facadeName) {
        return getModelName(facadeName)+"WriteService";
    }

    public static String getDaoName(String facadeName){
        return getModelName(facadeName)+"Dao";
    }

    public static Boolean isWrite(Method method){
        return false;
    }

    public static String deleteResponse(String returnTypeName){
        Pattern compile = Pattern.compile(".*Response<(.*)>");
        Matcher matcher = compile.matcher(returnTypeName);
        if (matcher.matches()){
            return matcher.group(1);
        }
        return returnTypeName;
    }
}
