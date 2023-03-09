package juc.test.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 死锁
 * Create by 2023/3/9 11:04
 */
public class DeadLockTest01 {
    public static void main(String[] args) {

        String lockA="a";
        String lockB="b";

        new Thread(new MyThread(lockA,lockB),"T1").start();
        new Thread(new MyThread(lockB,lockA),"T2").start();
    }
}

class MyThread implements Runnable{
    private String lock01;
    private String lock02;

    public MyThread(String lock01, String lock02) {
        this.lock01 = lock01;
        this.lock02 = lock02;
    }

    @Override
    public void run() {
        synchronized (lock01){
            System.out.println(Thread.currentThread().getName()+" lock" +lock01+"=>get "+lock02);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock02){
                System.out.println(Thread.currentThread().getName()+" lock" +lock02+"=>get "+lock01);
            }
        }
    }
}