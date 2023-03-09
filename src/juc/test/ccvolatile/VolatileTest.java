package juc.test.ccvolatile;

import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description TODO
 * Create by 2023/3/3 10:54
 */
public class VolatileTest {
    //private static int num=0;
    private volatile static int num=0;//不加volatile程序就会进入死循环
    public static void main(String[] args) throws InterruptedException { //main
        new Thread(()->{ //Thread_1
            while (num==0){ //对于主线程的更改是不知道的

            }
            System.out.println(Thread.currentThread().getName()+" end");
        }).start();

        TimeUnit.SECONDS.sleep(1);

        num=1;

        System.out.println(num);
    }
}
