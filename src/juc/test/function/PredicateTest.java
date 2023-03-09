package juc.test.function;

import java.util.function.Predicate;

/**
 * @author noah
 * @version 1.0
 * @Description Predicate:断定形接口，有一个输入参数，返回值只能是boolean
 * Create by 2023/3/2 10:21
 */
public class PredicateTest {
    public static void main(String[] args) {
    /**
     *  @FunctionalInterface
     *         public interface Predicate<T> {
     *
     *             boolean test(T t);
     */
    //Predicate<String> predicate =s->s.isEmpty();
    Predicate<String> predicate = String::isEmpty;
    System.out.println(predicate.test("hi"));
    }
}
