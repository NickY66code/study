package juc.test.bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 测试ArrayBlockingQueue队列
 * Create by 2023/3/1 13:47
 */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //test1();
        //test2();
        //test3();
        test4();
    }

    /**
     * 抛出异常
     */
    public static void test1(){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        System.out.println(arrayBlockingQueue.add("a"));
        System.out.println(arrayBlockingQueue.add("b"));
        System.out.println(arrayBlockingQueue.add("c"));

        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        //System.out.println(arrayBlockingQueue.add("d"));

        System.out.println(arrayBlockingQueue.element());

        //FIFO
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());

        //Exception in thread "main" java.util.NoSuchElementException
        //System.out.println(arrayBlockingQueue.remove());


    }

    /**
     * 不抛出异常
     */
    public static void test2(){
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        System.out.println(arrayBlockingQueue.offer("a"));
        System.out.println(arrayBlockingQueue.offer("b"));
        System.out.println(arrayBlockingQueue.offer("c"));

        System.out.println(arrayBlockingQueue.offer("d")); //false 不抛出异常

        System.out.println(arrayBlockingQueue.peek());

        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());//null 不抛出异常

    }

    /**
     * 等待 一直阻塞
     */
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        //一直阻塞
        arrayBlockingQueue.put("a");
        arrayBlockingQueue.put("b");
        arrayBlockingQueue.put("c");
        arrayBlockingQueue.put("d"); //队列没有位置，一直阻塞

        System.out.println(arrayBlockingQueue.take());
        System.out.println(arrayBlockingQueue.take());
        System.out.println(arrayBlockingQueue.take());
    }


    /**
     * 等待 阻塞超时
     */
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        System.out.println(arrayBlockingQueue.offer("a"));
        System.out.println(arrayBlockingQueue.offer("b"));
        System.out.println(arrayBlockingQueue.offer("c"));
        System.out.println(arrayBlockingQueue.offer("d",2, TimeUnit.SECONDS));

        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll(2,TimeUnit.SECONDS));
    }
}
