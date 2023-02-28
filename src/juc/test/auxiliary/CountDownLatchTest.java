package juc.test.auxiliary;

import java.util.concurrent.CountDownLatch;

/**
 * @author noah
 * @version 1.0
 * @Description CountDownLatch 计数器
 * Create by 2023/2/28 15:21
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" go out");
                countDownLatch.countDown(); //数量-1
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();//待所有计数清零才执行后面代码
        System.out.println("close door");
    }
}
