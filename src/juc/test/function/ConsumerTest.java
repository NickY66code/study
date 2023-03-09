package juc.test.function;

import java.util.function.Consumer;

/**
 * @author noah
 * @version 1.0
 * @Description Consumer 消费者接口
 * Create by 2023/3/2 10:36
 */
public class ConsumerTest {
    public static void main(String[] args) {
        /**
         * @FunctionalInterface
         * public interface Consumer<T> {
         *void accept (T t);
         */
        //Consumer<String> consumer=(str)-> System.out.println(str);
        Consumer<String> consumer=System.out::println;
        consumer.accept("asd");
    }
}
