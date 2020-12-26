package generate;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * @author youyu
 */
public class GenerateInfoFromModel{

    public static String infoPath = GenerateFacadeAndRequestResponse.rootPath+"settlement-api/src/main/java/io/terminus/settlement/center/entity/reponse/settle/SettleItemJournalInfo.java";
    public static Class<?> modelClazz = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        transform();
    }

    private static void transform() throws IOException {

        File infoFile = new File(infoPath);
        if (!infoFile.exists()){
            throw new RuntimeException("file not exists.");
        }
        List<String> infoLines = Files.readAllLines(infoFile.toPath());

        Integer insertIndex = null;
        for (int i=0;i<infoLines.size();i++){
            String line = infoLines.get(i);
            if (line.contains("}")){
                insertIndex = i;
            }
        }
        if (insertIndex == null){
            throw new RuntimeException("code error.");
        }

        List<String> insertLines = Lists.newArrayList();

        insertLines.add("    @ApiModelProperty(\"id\")");
        insertLines.add("    private Long id;");
        insertLines.add("");

        Arrays.stream(modelClazz.getDeclaredFields()).forEach(field -> {

        });

        infoLines.addAll(insertIndex,insertLines);

        GenerateFacadeAndRequestResponse.writeFile(infoFile,infoLines);
    }
}
