package generate;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author youyu
 */
public class DaoFileGenerator1 extends AbstractFileGenerator{

    public DaoFileGenerator1(String parentPath, String facadeName, List<String> importDeclaration) {
        super(parentPath, facadeName,importDeclaration);
    }

    @Override
    protected String getClassName() {
        return getDaoName(facadeName);
    }

    @Override
    protected void generateMethod(Method method) {

    }

    @Override
    protected void doGenerateMethod(Method method) {

    }

    @Override
    protected String getMethodSignature(Method method) {
        return null;
    }

    @Override
    protected void init(Method method) {
        content.add(GenerateFacadeAndRequestResponse.getPackageDeclare(file));
        content.add("");
        content.add("import io.terminus.trantor.sdk.autumn.dao.TrantorDAO;");
        content.add("import org.springframework.stereotype.Repository;");
        content.add("");
        GenerateFacadeAndRequestResponse.addAuthor(content,GenerateManagerAndServiceAndDao.author);
        content.add("@Repository");
        content.add("public class "+ getDaoName(facadeName)+" extends TrantorDAO<"+getModelName(facadeName)+",Long>"+" {");
    }
}
