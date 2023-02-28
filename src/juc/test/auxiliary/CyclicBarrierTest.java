package juc.test.auxiliary;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author noah
 * @version 1.0
 * @Description CyclicBarrier 加法计数器
 * Create by 2023/2/28 15:31
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        /**
         * 集齐龙珠召唤神龙
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙成功！");
        });
        for (int i = 1; i <= 7; i++) {
            new Thread(()->{
                System.out.println("收集到"+Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            },"龙珠"+i).start();
        }
    }
}
