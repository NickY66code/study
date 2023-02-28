package juc.test.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author noah
 * @version 1.0
 * @Description 测试线程不安全
 * Create by 2023/2/28 10:03
 */
public class ListTest {
    /**
     * java.util.ConcurrentModificationException 并发修改异常
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1.List<String> list = new Vector<>();
         * 2.List<String> list = Collections.synchronizedList(new ArrayList<>());
         * 3.List<String> list = new CopyOnWriteArrayList(); JUC 写入时复制，COW 计算机领域的一种优化策略
         * 在多线程调用List的时候，读取时是固定的，写入的时候避免覆盖，造成数据问题
         * CopyOnWriteArrayList 相较于 Vector lion给lock锁效率大于synchronized
         */
        //List<String> list = new ArrayList<>();
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
