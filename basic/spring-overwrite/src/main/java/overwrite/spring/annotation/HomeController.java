package overwrite.spring.annotation;

import java.lang.annotation.*;

/**
 * @author jam
 * @description
 * @create 2018-09-16
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HomeController {
}
