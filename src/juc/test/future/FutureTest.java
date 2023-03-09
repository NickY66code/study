package juc.test.future;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author noah
 * @version 1.0
 * @Description 异步调用
 * Create by 2023/3/3 9:59
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        //发起一个请求
//        CompletableFuture<Void> completableFuture= CompletableFuture.runAsync(()->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(Thread.currentThread().getName()+"=>runAsync Void");
//        });
//        System.out.println("end");
//        completableFuture.get();

        CompletableFuture<Integer> completableFuture= CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"=>supplyAsync Integer");
            //int i=10/0;
            return 1024;
        });

        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t: " + t); //正常的返回结果
            System.out.println("u: " + u); //错误返回信息
        }).exceptionally((e) -> {
            e.getMessage();
            return 500; //可以获取错误的返回结果
        }).get());
    }
}
