package juc.test.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author noah
 * @version 1.0
 * @Description Map集合不安全
 * Create by 2023/2/28 10:45
 */
public class MapTest {
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1.Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
         * 2.Map<String, String> map = new ConcurrentHashMap<>();
         * 主要利用了CAS 加 volatile 或者 synchronized 的方式来保证线程安全
         * 通过对头结点加锁来保证线程安全的
         * 通过缩小了锁的粒度，查询性能也更高
         */
        //Map<String, String> map = new HashMap<>();
        //Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
