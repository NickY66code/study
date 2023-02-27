package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题 （3）
 * 5：增加两个静态同步方法后，只有一个对象，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * 6：增加两个静态同步方法后，有两个对象，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * Create by 2023/2/27 17:10
 */
public class test03 {
    public static void main(String[] args) throws InterruptedException {
        //两个对象 只有一个Class模板
        Phone3 phone = new Phone3();
        Phone3 phone2 = new Phone3();

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

class Phone3{

    /**
     * synchronized 锁的对象是方法的调用者
     * 两个方法用的是同一个锁
     * static 静态方法 当类一加载就有了！锁的是Class
     * @throws InterruptedException
     */
    public static synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    public static synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }

}
