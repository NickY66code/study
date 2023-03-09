package juc.test.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description cas:比较并交换
 * Create by 2023/3/8 15:27
 */
public class CasTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2023);
        //public final boolean compareAndSet(int expect, int update)
        // CAS 是CPU的并发原语
        atomicInteger.compareAndSet(2023,2024);
        System.out.println(atomicInteger.get());

    }
}
