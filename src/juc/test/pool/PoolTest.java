package juc.test.pool;

import java.util.concurrent.*;

/**
 * @author noah
 * @version 1.0
 * @Description PoolTest
 * Create by 2023/3/1 15:53
 */
public class PoolTest {
    public static void main(String[] args) {
        //Executors 工具类、3大方法
        //单个线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //创建一个固定的线程池大小
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //可伸缩的，遇强则强，遇弱则弱
        //ExecutorService threadPool =Executors.newCachedThreadPool();
        //自己创建线程池

        //获取cpu核数
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService threadPool =new ThreadPoolExecutor(
                2, //核心线程数
                5, //最大线程数 如何定义？1.CPU密集型 (几核就是几，保证cpu效率最高) 2.IO密集型 (判断程序中十分耗IO的线程)>2n
                3, //超时3s，释放空闲线程
                TimeUnit.SECONDS, //超时单位
                new LinkedBlockingDeque<>(3), //候客区
                Executors.defaultThreadFactory(), //默认线程工厂
                //四个拒绝策略
                //new ThreadPoolExecutor.AbortPolicy() //默认拒绝策略：银行满了(业务厅+候客区) 不处理后面的人的请求，抛出异常
                //new ThreadPoolExecutor.CallerRunsPolicy() //哪里来的去哪里 返回到main running主线程执行
                //new ThreadPoolExecutor.DiscardPolicy() //队列满了，不会抛出异常,丢掉任务
                new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争,竞争失败，则丢掉任务，也不会抛出异常

        );
        //最大承载值：max+queue
        //超出异常 java.util.concurrent.RejectedExecutionException
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" running");
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //用完线程池，程序关闭，需要关闭线程池
            threadPool.shutdown();
        }

    }
}
