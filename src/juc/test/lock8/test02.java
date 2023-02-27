 package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题 （2）
 * 3：增加了普通方法hello()后，两个线程先打印 发短信还是hello？ 1.hello 2.发短信
 * 4：两个对象，两个同步方法，两个线程先打印 发短信还是打电话？ 1.打电话 2.发短信
 * Create by 2023/2/27 17:10
 */
public class test02 {
    public static void main(String[] args) throws InterruptedException {
        //两个不同的对象，因此有两个不同的锁
        Phone2 phone = new Phone2();
        Phone2 phone2 = new Phone2();

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
            phone2.call();
        },"B").start();
    }
}

class Phone2{

    /**
     * synchronized 锁的对象是方法的调用者
     * 两个方法用的是同一个锁
     * @throws InterruptedException
     */
    public synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }

    /**
     * 无锁，并不是同步方法，不受锁的影响
     */
    public void hello(){
        System.out.println(Thread.currentThread().getName()+"hello");
    }
}
