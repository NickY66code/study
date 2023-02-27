package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/23 10:34
 */
@MyAnnotation02(name="hi")
public class Test01 {
    @MyAnnotation02(name="hi")
    public void test(){

    }
}

/**
 * @author noah
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation02{
    //注解的参数：参数类型+参数名();
    String name() default "";
}
