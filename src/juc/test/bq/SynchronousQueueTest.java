package juc.test.bq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description SynchronousQueue不存储元素，即放即用
 * Create by 2023/3/1 14:49
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        //同步队列
        BlockingQueue<String> synchronousQueue = new SynchronousQueue();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" put 1");
                synchronousQueue.put("1");
                System.out.println(Thread.currentThread().getName()+" put 2");
                synchronousQueue.put("2");
                System.out.println(Thread.currentThread().getName()+" put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"T1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take "+synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take "+synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take "+synchronousQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"T2").start();
    }
}
