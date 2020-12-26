package generate;

import java.lang.reflect.Method;

/**
 * @author youyu
 * @date 2020/8/14 2:56 PM
 */
public interface FileGenerator {
    void generate(Method method);
    void flush();
}
