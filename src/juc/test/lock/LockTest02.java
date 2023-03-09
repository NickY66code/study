package juc.test.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description 可重入锁 Lock
 * Create by 2023/3/9 10:02
 */
public class LockTest02 {
    public static void main(String[] args) {
        Phone02 phone = new Phone02();

        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start();
    }
}


/**
 * synchronized 结果：ASMS ACALL BSMS BCALL
 */

class Phone02{

    Lock lock=new ReentrantLock();

    public void sms(){
        lock.lock();
        //lock.lock(); //lock锁必须配对。否则会死在里面
        try {
            System.out.println(Thread.currentThread().getName()+"SMS");
            call();//这里也有锁
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void call(){
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName()+"CALL");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
