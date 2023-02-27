package juc.test.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/2/27 16:21
 */
public class PCJuc {
    private int num = 0;
    private final Lock lock=new ReentrantLock();
    /**
     * 相当于房间
     */
    private final Condition condition=lock.newCondition();

    public void incr() throws InterruptedException {
        lock.lock();
        try {
            while (num != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + num++);
            //唤醒其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + num--);
            //唤醒其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
