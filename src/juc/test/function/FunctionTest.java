package juc.test.function;

import java.util.function.Function;

/**
 * @author noah
 * @version 1.0
 * @Description FunctionTest 函数式接口
 * Create by 2023/3/2 10:14
 */
public class FunctionTest {
    /**
     * @FunctionalInterface
     * public interface Function<T, R> {
     * R apply(T t);
     */
    public static void main(String[] args) {
        Function<String,String> function= s-> s;
        System.out.println(function.apply("asd"));
    }
}
