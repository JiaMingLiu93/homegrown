package com.homegrown.tools.generator.demo3;

import com.homegrown.tools.code.generator.struct.processor.demo.processor.ConfigurationProcessor;
import com.homegrown.tools.generator.demo3.joor.Compile;
import com.homegrown.tools.generator.demo3.joor.CompileOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.Files.newBufferedReader;

/**
 * @author youyu
 */
public class TestMain {
    public static String rootPath = System.getProperty("user.dir")+"/";
    public static String modelPath = rootPath + "/tools/generator-demo1/generator-demo3/src/main/java/com/homegrown/tools/generator/demo3";
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));

//        File modelFile = new File(modelPath+"/TestModel.java");
//        String content = readAllLines(modelFile.toPath());
        String content = "import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;\n" +
                "import com.homegrown.tools.code.generator.struct.processor.demo.annotation.TypeConfig;\n" +
                "\n" +
                "import com.homegrown.tools.generator.demo3.TestRootModel;\n"+
                "import com.homegrown.tools.generator.demo3.TestAnnotation;\n"+
                "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * @author youyu\n" +
                " */\n" +
                "@TypeConfig(type = GenerateTypeEnum.REQUEST,\n" +
                "        packageName = \"com.homegrown.tools.code.generator.demo.request\",\n" +
                "        className = \"TestRequest\",\n" +
                "        superClass = \"com.homegrown.tools.code.generator.demo.TestSuperRequest\",\n" +
                "        annotations = {TestAnnotation.class},\n" +
                "        imports = {List.class})\n" +
                "public class TestModel1 extends TestRootModel<Long> {\n" +
                "\n" +
                "}";
        CompileOptions compileOptions = new CompileOptions();

        compileOptions = compileOptions.processors(new ConfigurationProcessor());

        Class<?> compile = Compile.compile("com.homegrown.tools.generator.demo3.TestModel1", content, compileOptions);
        System.out.println(compile.getCanonicalName());
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
