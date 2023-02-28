package juc.test.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author noah
 * @version 1.0
 * @Description 测试callable<>
 * Create by 2023/2/28 15:05
 */
public class callableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        FutureTask<String> futureTask = new FutureTask<>(myThread);
        new Thread(futureTask).start();
        new Thread(futureTask).start();//结果会被缓存，效率高
        if (!futureTask.isDone()){
            System.out.println(futureTask.get()); //可能会产生阻塞
        }
    }
}

class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("calling");
        return "hello world";
    }
}