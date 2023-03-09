package juc.test.function;

import java.util.function.Supplier;

/**
 * @author noah
 * @version 1.0
 * @Description Supplier 供给型接口
 * Create by 2023/3/2 10:55
 */
public class SupplierTest {
    public static void main(String[] args) {
        /**
         * @FunctionalInterface
         * public interface Supplier<T> {
         *T get ();
         */
        Supplier<String> supplier=()->"hello world";
        System.out.println(supplier.get());
    }
}
