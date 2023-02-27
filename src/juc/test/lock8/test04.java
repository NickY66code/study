package juc.test.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 8锁问题 （4）
 * 7：增加一个静态同步方法，一个普通同步后，只有一个对象，两个线程先打印 发短信还是打电话？ 1.打电话 2.发短信
 * 8：增加一个静态同步方法，一个普通同步后，有两个对象，两个线程先打印 发短信还是打电话？ 1.发短信 2.打电话
 * Create by 2023/2/27 17:10
 */
public class test04 {
    public static void main(String[] args) throws InterruptedException {
        //两个对象 只有一个Class模板
        Phone4 phone = new Phone4();
        Phone4 phone2 = new Phone4();

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

class Phone4{

    /**
     * 静态同步方法 锁的是Class模板
     */
    public static synchronized void send() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"发短信");
    }

    /**
     * 普通同步方法 锁的是对象方法
     */
    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }

}
