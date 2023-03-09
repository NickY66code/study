package juc.test.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author noah
 * @version 1.0
 * @Description 测试不同计算
 * Create by 2023/3/2 15:01
 */
public class Test {
    public static void main(String[] args) {
        //test1(); //time:6301 294
        //test2(); //time:3583 167
        test3(); //time:143
    }

    public static void test1(){
        long sum=0L;
        long start =System.currentTimeMillis();
        //正常代码
        for (long i = 1L; i <= 10_0000_0000L; i++) {
            sum+=i;
        }
        long end =System.currentTimeMillis();
        System.out.println("sum: "+sum+" 时间: " +(end-start));
    }

    public static void test2(){
        long start =System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTest task = new ForkJoinTest(0L,10_0000_0000L);
        //forkJoinPool.execute(task); //执行任务 无返回值
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        Long sum=0L;
        try {
             sum=submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        long end =System.currentTimeMillis();
        System.out.println("sum: "+sum+"时间: " +(end-start));
    }

    public static void test3(){
        long start =System.currentTimeMillis();
        //Stream并行流
        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end =System.currentTimeMillis();
        System.out.println("sum: "+sum+"时间: " +(end-start));
    }
}
