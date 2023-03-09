package juc.test.lock;

/**
 * @author noah
 * @version 1.0
 * @Description 可重入锁 synchronized
 * Create by 2023/3/9 9:53
 */
public class LockTest01 {
    public static void main(String[] args) {
        Phone phone = new Phone();

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

class Phone{
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName()+"SMS");
        call();//这里也有锁
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"CALL");

    }
}