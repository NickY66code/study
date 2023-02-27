package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题
 * 1：标准情况下，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * 2：send()延迟4s情况下，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * Create by 2023/2/27 17:10
 */
public class test01 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(()->{
            try {
                phone.send();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"A").start();

        //休息1s
        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            phone.call();
        },"B").start();
    }
}

class Phone{

    /**
     * synchronized 锁的对象是方法的调用者
     * 两个方法用的是同一个锁
     * @throws InterruptedException
     */
    public synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }
}