package juc.test.ccvolatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author noah
 * @version 1.0
 * @Description VolatileTest2：不保证原子性
 * Create by 2023/3/3 14:01
 */
public class VolatileTest2 {
    //不保证原子性
   // private volatile static int num=0;
    private volatile static AtomicInteger num=new AtomicInteger();

    //synchronized
    public  static void add(){
        num.getAndIncrement();//+1方法 CAS
    }

    public static void main(String[] args) {
        //理论上 num结果应该为20000
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount()>2){ //main gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+" :"+num); //main :19917
    }
}
