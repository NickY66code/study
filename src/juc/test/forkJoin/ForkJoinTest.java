package juc.test.forkJoin;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.Lock;

/**
 * @author noah
 * @version 1.0
 * @Description ForkJoin
 * Create by 2023/3/2 14:35
 */
public class ForkJoinTest extends RecursiveTask<Long> {
    private long start;
    private long end;

    //临界值
    private long temp=10000L;

    public ForkJoinTest(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end-start)>temp){
            long middle=(start+end)/2; //中间值
            ForkJoinTest forkJoinTest1 = new ForkJoinTest(start,middle);
            forkJoinTest1.fork(); //拆分任务，把任务压入线程队列
            ForkJoinTest forkJoinTest2 = new ForkJoinTest(middle+1,end);
            forkJoinTest2.fork();
            return forkJoinTest1.join()+forkJoinTest2.join();
        }else {
            //正常代码
            long sum=0L;
            for (long i = start; i <= end; i++) {
                sum+=i;
            }
            return sum;
        }
    }
}
