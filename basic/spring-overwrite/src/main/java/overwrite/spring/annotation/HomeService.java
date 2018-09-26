package overwrite.spring.annotation;

import java.lang.annotation.*;

/**
 * @author jam
 * @description
 * @create 2018-09-26
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HomeService {
}
