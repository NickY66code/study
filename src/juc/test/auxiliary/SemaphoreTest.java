package juc.test.auxiliary;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description Semaphore 作用：限流 多个共享资源互斥的使用 控制线程的最大数量
 * Create by 2023/2/28 16:05
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        //permits 线程数量 停车位
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire(); //得到
                    System.out.println(Thread.currentThread().getName()+"得到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"离开车位");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    semaphore.release();//释放
                }
            },String.valueOf(i)).start();
        }
    }
}
