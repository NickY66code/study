package juc.test.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author noah
 * @version 1.0
 * @Description Set集合不安全
 * Create by 2023/2/28 10:32
 */
public class SetTest {
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1.Set<String> set = Collections.synchronizedSet(new HashSet<>());
         * 2.Set<String> set = new CopyOnWriteArraySet();
         */
        //Set<String> set = new HashSet<>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }
}
