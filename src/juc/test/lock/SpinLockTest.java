package juc.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author noah
 * @version 1.0
 * @Description 自旋锁
 * Create by 2023/3/9 10:21
 */
public class SpinLockTest {
    /**
     * int 0
     * Thread null
     */
    AtomicReference<Thread> atomicReference=new AtomicReference<>();

    /**
     * 加锁
     */
    public void lock(){
        Thread thread=Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==> myLock");

        //自旋锁
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }

    /**
     * 解锁
     */
    public void unlock(){
        Thread thread=Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==> myUnlock");
        atomicReference.compareAndSet(thread,null);
    }

    public static void main(String[] args) throws InterruptedException {
        //自旋锁
        SpinLockTest spinLockTest = new SpinLockTest();

        new Thread(()->{
            spinLockTest.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                spinLockTest.unlock();
            }
        },"T1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            spinLockTest.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                spinLockTest.unlock();
            }
        },"T2").start();

    }
}
